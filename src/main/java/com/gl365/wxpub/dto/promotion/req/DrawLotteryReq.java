package com.gl365.wxpub.dto.promotion.req;

import com.gl365.wxpub.dto.BaseDomain;

public class DrawLotteryReq extends BaseDomain{

	private static final long serialVersionUID = -4510665365935489160L;
	
	private String activityId;

	private String merchantNo;

	private String phone;

	private String userId;

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
