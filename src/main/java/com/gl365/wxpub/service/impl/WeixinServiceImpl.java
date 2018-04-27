package com.gl365.wxpub.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl365.wxpub.common.Constant;
import com.gl365.wxpub.dto.WxApiAccessToken;
import com.gl365.wxpub.dto.WxJsApiTicket;
import com.gl365.wxpub.dto.WxpubSendMsg;
import com.gl365.wxpub.handler.ConfigHandler;
import com.gl365.wxpub.handler.HttpHandlerService;
import com.gl365.wxpub.service.RedisService;
import com.gl365.wxpub.service.WeixinService;
import com.gl365.wxpub.util.GsonUtils;
import com.google.gson.JsonObject;

@Service
public class WeixinServiceImpl implements WeixinService{
	
	private static Logger logger = LoggerFactory.getLogger(WeixinServiceImpl.class);

	@Autowired
	private HttpHandlerService httpHandlerService;
	
	@Autowired
	private RedisService redisService;
	
	@Override
	public WxApiAccessToken getAccessToken() {
		String url = String.format(ConfigHandler.getInstance().getAccessTokenUrl(), ConfigHandler.getInstance().getAppId(),ConfigHandler.getInstance().getAppSecret());
		JsonObject object = null;
		WxApiAccessToken accessToken = new WxApiAccessToken();
		try{
			object = httpHandlerService.get(url);
			if(null == object || null == object.get("expires_in") || null == object.get("access_token")){
				logger.error("request wechat api accessToken fail. [result={}]", GsonUtils.toJson(object));
				return null;
			}
			logger.info("request wechat api accessToken success. [result={}]", GsonUtils.toJson(object));
			accessToken.setExpiresIn(Integer.valueOf(object.get("expires_in").toString().replaceAll("\"", "")));
			accessToken.setToken(object.get("access_token").toString().replaceAll("\"", ""));
		}catch (Exception ex) {
			logger.error("fail to request wechat api accessToken. error:", ex);
			return null;
		}
		return accessToken;
	}

	@Override
	public WxJsApiTicket getJsTicket(String accessToken) {
		String url = String.format(ConfigHandler.getInstance().getTicketUrl(),accessToken);
		JsonObject object = null;
		WxJsApiTicket ticket = new WxJsApiTicket();
		try{
			object = httpHandlerService.get(url);
			if(null == object || null == object.get("expires_in") || null == object.get("ticket")){
				logger.error("request wechat jsApi ticket fail. [result={}]", GsonUtils.toJson(object));
				return null;
			}
			logger.info("request wechat jsApi ticket success. [result={}]", GsonUtils.toJson(object));
			ticket.setExpiresIn(Integer.valueOf(object.get("expires_in").toString().replaceAll("\"", "")));
			ticket.setTicket(object.get("ticket").toString().replaceAll("\"", ""));
		}catch (Exception ex) {
			logger.error("fail to request wechat jsApi ticket. error:", ex);
			return null;
		}
		return ticket;
	}

	@Override
	public String sendMsg(WxpubSendMsg<?> msg) {
		logger.info("sendMsg begin,msg:{}",msg);
		String jsonStr = null;
		String url = String.format(ConfigHandler.getInstance().getSendMsgUrl(), getAccessTokenFromRedis());
		try{
			jsonStr = httpHandlerService.post(url, msg);
			logger.info("sendMsg ===> httpHandlerService.post success,rlt:{}",jsonStr);
		}catch(Exception e){
			logger.error("sendMsg ===> httpHandlerService.post exception,e:",e);
		}
		return jsonStr;
	}
	
	private String getAccessTokenFromRedis(){
		String redisToken = redisService.get(Constant.WXAPI_ACCESSTOKEN_KEY);
		if(StringUtils.isBlank(redisToken)){
			return null;
		}
		WxApiAccessToken token = GsonUtils.fromJson2Object(redisToken, WxApiAccessToken.class);
		return token.getToken();
	}

}
