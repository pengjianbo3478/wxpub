package com.gl365.wxpub.dto.member.req;

import java.util.List;

public class GetPhotoReq{
	
	private List<String> wxUserIds;   
	
	private List<String> appUserIds;   
	
	private String channel;

	public List<String> getWxUserIds() {
		return wxUserIds;
	}

	public void setWxUserIds(List<String> wxUserIds) {
		this.wxUserIds = wxUserIds;
	}

	public List<String> getAppUserIds() {
		return appUserIds;
	}

	public void setAppUserIds(List<String> appUserIds) {
		this.appUserIds = appUserIds;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}
