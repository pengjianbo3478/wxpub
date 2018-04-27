package com.gl365.wxpub.dto.member.req;

public class SendSMSReq{
	
	private String phoneNo;
	
	/**
	 * 业务类型
	 * 0：注册
	 * 1：修改手机号
	 * 2：登录
	 * 3：忘记登录密码
	 * 7：不论场景直接发送
	 */
	private Integer businessType;
	
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
}
