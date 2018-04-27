package com.gl365.wxpub.service.FeignCilent;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.member.req.SendSMSReq;
import com.gl365.wxpub.dto.member.resp.VerifySMSResq;

/**
 * < 短信验证接口消费 >
 * 
 * @author hui.li 2017年4月21日 - 上午11:19:59
 * @Since 1.0
 */
@FeignClient(name="member",url="${${env:}.url.member:}")
public interface SmsClient {

	/**
	 * 发送短信验证码接口
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/member/sms/sendSms")
	public ResultDto<String> sendSms(@RequestBody SendSMSReq req);

	/**
	 * 验证短信验证码
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/member/sms/verifySmsCode")
	public ResultDto<?> verifySmsCode(@RequestBody VerifySMSResq req);
	
}
