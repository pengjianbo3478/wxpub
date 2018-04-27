package com.gl365.wxpub.dto.member.req;

import io.swagger.annotations.ApiModelProperty;

public class GroupPayDistributeReq {

	@ApiModelProperty("群号")
	private String groupId ;
	
	@ApiModelProperty("当前用户的userId")
	private String userId ;
	
	@ApiModelProperty("查询撤单详情时候传入5")
	private String orderStatus;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
}
