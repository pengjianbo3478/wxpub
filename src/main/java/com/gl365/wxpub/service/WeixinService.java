package com.gl365.wxpub.service;

import com.gl365.wxpub.dto.WxApiAccessToken;
import com.gl365.wxpub.dto.WxJsApiTicket;
import com.gl365.wxpub.dto.WxpubSendMsg;

public interface WeixinService {

	WxApiAccessToken getAccessToken();
	
	WxJsApiTicket getJsTicket(String accessToken);
	
	String sendMsg(WxpubSendMsg<?> msg);
}
