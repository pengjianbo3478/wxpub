package com.gl365.wxpub.dto.h5.resp;

import com.gl365.wxpub.dto.BaseDomain;

public class WxJsSignResp extends BaseDomain{

	private static final long serialVersionUID = -6331912566954095640L;

	private String noncestr;
	
	private String timestamp;
	
	private String url;
	
	private String sign;
	
	private String appId;

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
}
