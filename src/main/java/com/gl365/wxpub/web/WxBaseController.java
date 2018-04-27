package com.gl365.wxpub.web;

import java.time.Instant;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.wxpub.aspect.Log;
import com.gl365.wxpub.common.Constant;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.WxJsApiTicket;
import com.gl365.wxpub.dto.h5.resp.WxJsSignResp;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.handler.ConfigHandler;
import com.gl365.wxpub.service.RedisService;
import com.gl365.wxpub.util.GsonUtils;
import com.gl365.wxpub.util.Sha1Utils;

@CrossOrigin(origins = "*", maxAge = 3600) // 跨域问题，与前端调试时打开
@RestController
@RequestMapping("/wxpubApi/wxBase")
public class WxBaseController {

	private static final Logger logger = LoggerFactory.getLogger(WxBaseController.class);
	
	@Autowired
	private RedisService redisService;
	
	@GetMapping("/JsApiSign")
	@Log("JS-SDK签名")
	public Object jsApiSign(@RequestParam(value="url") String url){
		ResultDto<?> resp = null;
		String ticketStr = redisService.get(Constant.WXJSAPI_TICKET_KEY);
		if(StringUtils.isBlank(ticketStr)){
			logger.error("jsApiSign ===> redisService.getTicket fail,ticket is null");
			return ResultDto.errorResult();
		}
		WxJsApiTicket ticket = GsonUtils.fromJson2Object(ticketStr, WxJsApiTicket.class);
		logger.info("jsApiSign ===> redisService.getTicket success,ticket:{}",ticket);
		
		String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
		String timestamp = String.valueOf(Instant.now().toEpochMilli() / 1000);
		String str = "jsapi_ticket="+ticket.getTicket()+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
		String sign = Sha1Utils.SHA1(str);
		if(StringUtils.isBlank(sign)){
			logger.error("jsApiSign ===> Sha1Utils.SHA1 fail,sign is null,str:{}",str);
			return ResultDto.errorResult();
		}
		
		WxJsSignResp data = new WxJsSignResp();
		data.setAppId(ConfigHandler.getInstance().getAppId());
		data.setNoncestr(noncestr);
		data.setSign(sign);
		data.setTimestamp(timestamp);
		data.setUrl(url);
		resp = ResultDto.result(ResultCodeEnum.System.SUCCESS, data);
		return resp;
	}
}
