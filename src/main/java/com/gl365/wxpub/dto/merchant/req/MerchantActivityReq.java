package com.gl365.wxpub.dto.merchant.req;

import java.math.BigDecimal;

import com.gl365.wxpub.dto.BaseDomain;

public class MerchantActivityReq extends BaseDomain{

	private static final long serialVersionUID = -2723629695500924697L;

	private String merchantNo;
	
	private BigDecimal totalAmout;
	
	private String userId;
	
	private String openId;
	
	private String mobileNo;

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public BigDecimal getTotalAmout() {
		return totalAmout;
	}

	public void setTotalAmout(BigDecimal totalAmout) {
		this.totalAmout = totalAmout;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
}
