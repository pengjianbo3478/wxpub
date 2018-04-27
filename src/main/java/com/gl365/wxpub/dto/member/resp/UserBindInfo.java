package com.gl365.wxpub.dto.member.resp;

import java.math.BigDecimal;

import com.gl365.wxpub.dto.BaseDomain;

public class UserBindInfo extends BaseDomain{

	private static final long serialVersionUID = -3748849402277526899L;

	private String userId;
	
	private int bindFlag; //0:第一次注册，1：非第一次注册
	
	private BigDecimal amount; //乐豆余额
	
	private String mobilePhone;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getBindFlag() {
		return bindFlag;
	}

	public void setBindFlag(int bindFlag) {
		this.bindFlag = bindFlag;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
}
