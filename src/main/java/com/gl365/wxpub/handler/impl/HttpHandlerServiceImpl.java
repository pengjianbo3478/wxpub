package com.gl365.wxpub.handler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gl365.wxpub.handler.HttpHandlerService;
import com.gl365.wxpub.util.GsonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Service
public class HttpHandlerServiceImpl implements HttpHandlerService{
	
	private static final Logger logger = LoggerFactory.getLogger(HttpHandlerServiceImpl.class);
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public JsonObject get(String url) {
		JsonObject object = null;
		try{
			logger.info("get request from url: {}", url);
			ResponseEntity<String> resp =restTemplate.getForEntity(url, String.class);
			String body = resp.getBody();
			Gson token_gson = new Gson();
			object = token_gson.fromJson(body, JsonObject.class);
		}catch(Exception e){
			logger.error("fail to get request. [error={}]", e);
		}
		return object;
	}

	@Override
	public String post(String url, Object req) {
		String jsonStr = null;
		try{
			logger.info("post request from url: {}", url);
			ResponseEntity<String> resp =restTemplate.postForEntity(url, GsonUtils.toJson(req), String.class);
			jsonStr = resp.getBody();
		}catch(Exception e){
			logger.error("fail to post request. [error={}]", e);
		}
		return jsonStr;
	}

}
