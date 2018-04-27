package com.gl365.wxpub.dto.member.resp.users;

public class UserCoinAndPhotoDto {

	/**
	 * 头像url
	 */
	private String url;

	/**
	 * 乐豆、账号开关
	 */
	private Integer value;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
