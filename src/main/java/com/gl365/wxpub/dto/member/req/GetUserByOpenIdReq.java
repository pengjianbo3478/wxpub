package com.gl365.wxpub.dto.member.req;

import com.gl365.wxpub.dto.BaseDomain;

public class GetUserByOpenIdReq extends BaseDomain{

	private static final long serialVersionUID = -5196165948391210920L;

	private String channel = "wx";
	
	private String nickName;
	
	private String payOrganId;
	
	private String photo;
	
	private String recommendAgentType;
	
	private String recommendAgentId;
	
	private String recommendBy;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPayOrganId() {
		return payOrganId;
	}

	public void setPayOrganId(String payOrganId) {
		this.payOrganId = payOrganId;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getRecommendAgentType() {
		return recommendAgentType;
	}

	public void setRecommendAgentType(String recommendAgentType) {
		this.recommendAgentType = recommendAgentType;
	}

	public String getRecommendAgentId() {
		return recommendAgentId;
	}

	public void setRecommendAgentId(String recommendAgentId) {
		this.recommendAgentId = recommendAgentId;
	}

	public String getRecommendBy() {
		return recommendBy;
	}

	public void setRecommendBy(String recommendBy) {
		this.recommendBy = recommendBy;
	}
	
}
