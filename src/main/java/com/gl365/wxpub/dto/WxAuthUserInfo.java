package com.gl365.wxpub.dto;

public class WxAuthUserInfo extends BaseDomain{

	private static final long serialVersionUID = -7683866569225047237L;

	private String openId;
	
	private String nickname;
	
	private String headimgurl;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
}
