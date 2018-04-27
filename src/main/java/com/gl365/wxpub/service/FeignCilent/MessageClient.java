package com.gl365.wxpub.service.FeignCilent;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.message.WxpubMsg;

@FeignClient(name="message",url="${${env:}.url.merchant:}")
public interface MessageClient {

	@RequestMapping(value = "/wxpubMsg/saveWxpubMsg", method = RequestMethod.POST)
	public ResultDto<?> saveWxpubMsg(@RequestBody WxpubMsg req);
	
	@RequestMapping(value = "/wxpubMsg/updateSendMsgRlt", method = RequestMethod.POST)
	public ResultDto<?> updateSendMsgRlt(@RequestBody WxpubMsg req);
}
