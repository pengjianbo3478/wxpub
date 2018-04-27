package com.gl365.wxpub.dto.order.resp;

import com.gl365.wxpub.dto.BaseDomain;

public class OrderComfirmInfo extends BaseDomain{

	private static final long serialVersionUID = 1948563846670766842L;

	private int orderStatus;

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}
