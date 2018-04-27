package com.gl365.wxpub.dto.h5.resp;

import java.io.Serializable;

import com.gl365.wxpub.dto.BaseDomain;
import com.gl365.wxpub.util.GsonUtils;

public class MainOrderStatue implements Serializable{

	private static final long serialVersionUID = 2550139484830611962L;

	private int payStatue;
	
	private String groupId;

	public int getPayStatue() {
		return payStatue;
	}

	public void setPayStatue(int payStatue) {
		this.payStatue = payStatue;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Override
	public String toString() {
		return GsonUtils.toJson(this);
	}
}
