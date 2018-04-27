package com.gl365.wxpub.dto;

public class WxApiAccessToken extends BaseDomain{

	private static final long serialVersionUID = 5129968791581186565L;

	private String token;
	
	private int expiresIn;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	
}
