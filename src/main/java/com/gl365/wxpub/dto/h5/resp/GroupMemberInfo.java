package com.gl365.wxpub.dto.h5.resp;

import java.io.Serializable;
import java.util.List;

import com.gl365.wxpub.enums.GroupStatus;
import com.gl365.wxpub.util.GsonUtils;

public class GroupMemberInfo implements Serializable{

	private static final long serialVersionUID = 5906745166450530154L;

	private GroupStatus status;
	
	private List<GroupUserInfo> memberInfo;

	public GroupStatus getStatus() {
		return status;
	}

	public void setStatus(GroupStatus status) {
		this.status = status;
	}

	public List<GroupUserInfo> getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(List<GroupUserInfo> memberInfo) {
		this.memberInfo = memberInfo;
	}

	@Override
	public String toString() {
		return GsonUtils.toJson(this);
	}
}
