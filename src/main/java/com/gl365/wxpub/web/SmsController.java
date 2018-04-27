package com.gl365.wxpub.web;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.wxpub.common.JsonUtils;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.member.req.SendSMSReq;
import com.gl365.wxpub.dto.member.resp.VerifySMSResq;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.service.RedisService;
import com.gl365.wxpub.service.FeignCilent.SmsClient;

@CrossOrigin(origins = "*", maxAge = 3600) // 跨域问题，与前端调试时打开
@RestController
@RequestMapping("/wxpubApi/sms")
public class SmsController {

	private static final Logger LOG = LoggerFactory.getLogger(SmsController.class);
	
	@Autowired
	private SmsClient smsService;
	@Autowired
	private RedisService redisService;
	
	public static final String WX_SEND_SMS_PREFIX = "WX_SEND_SMS_PREFIX";
	private static final Long liveTime = 1800L;

	@PostMapping("/sendSms")
	public Object sendSms(@RequestBody SendSMSReq req) {
		LOG.info("sendSms begin,reqParam:{}", JsonUtils.toJsonString(req));
		ResultDto<?> resp = new ResultDto<>();
		try{
			if (StringUtils.isEmpty(req.getPhoneNo()) || req.getBusinessType() == null) {
				return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
			}
			resp = smsService.sendSms(req);
			if(ResultCodeEnum.System.SUCCESS.getCode().equals(resp.getResult())){
				String key = WX_SEND_SMS_PREFIX+req.getPhoneNo()+req.getBusinessType();
				String value = UUID.randomUUID().toString();
				redisService.set(key, value, liveTime);
				Map<String,String> rltMap = new HashMap<>();
				rltMap.put("wxToken", value);
				resp = new ResultDto<>(ResultCodeEnum.System.SUCCESS,rltMap);
			}
		}catch(Exception e){
			LOG.error("sendSms ===> smsService.sendSms exception,e："+e );
			resp = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		return resp;
	}
	
	@PostMapping("/verifySms")
	public Object verifySmsCode(@RequestBody VerifySMSResq req) {
		LOG.info("SmsCode verify,reqParam:{}", JsonUtils.toJsonString(req));
		ResultDto<?> resp = null;
		try {
			if (StringUtils.isEmpty(req.getWxToken()) || StringUtils.isEmpty(req.getPhoneNo()) || req.getBusinessType() == null){
				return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
			}
			String key = WX_SEND_SMS_PREFIX+req.getPhoneNo()+req.getBusinessType();
			String wxToken = (String) redisService.get(key);
			if(!req.getWxToken().equals(wxToken)){
				return new ResultDto<>(ResultCodeEnum.System.PARAM_ERROR.getCode(), "短信超时,请重新发送", null);
			}
			resp = smsService.verifySmsCode(req);
		} catch (Exception e) {
			LOG.error("SmsCode verify ===> smsService.verify exception,e：" + e);
			resp = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		return resp;
	}

}
