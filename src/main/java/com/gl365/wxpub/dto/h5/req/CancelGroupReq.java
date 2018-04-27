package com.gl365.wxpub.dto.h5.req;

import com.gl365.wxpub.dto.BaseDomain;

public class CancelGroupReq extends BaseDomain{

	private static final long serialVersionUID = -3060337020674038517L;

	private String groupId;
	
	private String mainOrderNo;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getMainOrderNo() {
		return mainOrderNo;
	}

	public void setMainOrderNo(String mainOrderNo) {
		this.mainOrderNo = mainOrderNo;
	}
	
}
