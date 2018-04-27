package com.gl365.wxpub.dto.member.resp.users;

public class UserPhotoAndName {

	private String userId;

	private String realName;
	
	private String wxNickName;

	private String wxPhoto;
	
	private String appNickName;
	
	private String appPhoto;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getWxNickName() {
		return wxNickName;
	}

	public void setWxNickName(String wxNickName) {
		this.wxNickName = wxNickName;
	}

	public String getWxPhoto() {
		return wxPhoto;
	}

	public void setWxPhoto(String wxPhoto) {
		this.wxPhoto = wxPhoto;
	}

	public String getAppNickName() {
		return appNickName;
	}

	public void setAppNickName(String appNickName) {
		this.appNickName = appNickName;
	}

	public String getAppPhoto() {
		return appPhoto;
	}

	public void setAppPhoto(String appPhoto) {
		this.appPhoto = appPhoto;
	}
}