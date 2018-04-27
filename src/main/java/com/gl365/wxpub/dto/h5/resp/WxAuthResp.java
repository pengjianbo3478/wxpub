package com.gl365.wxpub.dto.h5.resp;

import com.gl365.wxpub.dto.BaseDomain;

public class WxAuthResp extends BaseDomain{

	private static final long serialVersionUID = -3210703103428211057L;

	private String url;
	
	private String nickname;
	
	private String headimgurl;
	
	private String token;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
