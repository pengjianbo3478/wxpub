package com.gl365.wxpub.dto.account.req;


import com.gl365.wxpub.dto.BaseDomain;

public class ActBalanceInfoReq extends BaseDomain{

	private static final long serialVersionUID = -2970933365670838102L;

	private String userId;
	
	private String agentId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
}
