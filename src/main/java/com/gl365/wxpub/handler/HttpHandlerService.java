package com.gl365.wxpub.handler;

import com.google.gson.JsonObject;

public interface HttpHandlerService {

	public JsonObject get(String url);
	
	public String post(String url,Object req);
	
}
