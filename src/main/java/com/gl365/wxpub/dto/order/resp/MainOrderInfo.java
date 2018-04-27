package com.gl365.wxpub.dto.order.resp;

import com.gl365.wxpub.dto.BaseDomain;

public class MainOrderInfo extends BaseDomain{

	private static final long serialVersionUID = -4307310058506471671L;

	private String orderSn;
	
	private int orderStatus;
	
	private String groupId;

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
}
