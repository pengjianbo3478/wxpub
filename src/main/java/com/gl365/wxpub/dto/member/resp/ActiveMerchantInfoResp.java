package com.gl365.wxpub.dto.member.resp;

import java.math.BigDecimal;

public class ActiveMerchantInfoResp{
	
	private String activityId;
	
	private String activityName;
	
	private String beginTime;
	
	private String endTime;
	
	private String jumpUrl;
	
	private String merchantId;
	
	private BigDecimal leastPrice;

	public BigDecimal getLeastPrice() {
		return leastPrice;
	}

	public void setLeastPrice(BigDecimal leastPrice) {
		this.leastPrice = leastPrice;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getJumpUrl() {
		return jumpUrl;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
}
