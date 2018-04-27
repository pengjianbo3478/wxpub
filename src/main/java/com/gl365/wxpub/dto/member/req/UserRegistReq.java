package com.gl365.wxpub.dto.member.req;

public class UserRegistReq {
	
	private String userId;
	
	private String wxToken;
	
	private String mobilePhone;
	
	private String channel;
	
	private String active;//00活动注册，01非活动注册
	
	private String activeId;//活动Id
	
	private String MerchantNo;//活动商户号
	
	private String source; //1：买单后参加活动；2：通过分享链接参与活动

	public UserRegistReq() {
		super();
	}

	public UserRegistReq(String userId, String mobilePhone, String channel, String active, String activeId) {
		this.userId = userId;
		this.mobilePhone = mobilePhone;
		this.channel = channel;
		this.active = active;
		this.activeId = activeId;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getWxToken() {
		return wxToken;
	}

	public void setWxToken(String wxToken) {
		this.wxToken = wxToken;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getActiveId() {
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	public String getMerchantNo() {
		return MerchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		MerchantNo = merchantNo;
	}
}
