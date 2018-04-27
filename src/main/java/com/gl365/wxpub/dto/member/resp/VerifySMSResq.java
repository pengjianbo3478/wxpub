package com.gl365.wxpub.dto.member.resp;

public class VerifySMSResq{

	private String phoneNo;
	
	private String verifyCode;
	
	/**
	 * 业务类型
	 * 0：注册
	 * 1：修改手机号
	 * 2：登录
	 * 3：忘记登录密码
	 */
	private Integer businessType;
	
	private String wxToken;

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getWxToken() {
		return wxToken;
	}

	public void setWxToken(String wxToken) {
		this.wxToken = wxToken;
	}
}
