package com.gl365.wxpub.dto.member.resp;

import java.time.LocalDate;

public class ActiveMerchantResp{


	private Boolean activeMerchant;
	
	private String activeBeginTime;
	
	private String activeEndTime;
	
	private String activeId;

	public Boolean getActiveMerchant() {
		return activeMerchant;
	}

	public void setActiveMerchant(Boolean activeMerchant) {
		this.activeMerchant = activeMerchant;
	}

	public String getActiveBeginTime() {
		return activeBeginTime;
	}

	public void setActiveBeginTime(String activeBeginTime) {
		this.activeBeginTime = activeBeginTime;
	}

	public String getActiveEndTime() {
		return activeEndTime;
	}

	public void setActiveEndTime(String activeEndTime) {
		this.activeEndTime = activeEndTime;
	}

	public String getActiveId() {
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
}
