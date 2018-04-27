package com.gl365.wxpub.dto.order.req;

import com.gl365.wxpub.dto.BaseDomain;

public class QueryOrderInfoReq  extends BaseDomain{

	private static final long serialVersionUID = 8787422483568810354L;

	private String orderSn;

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	
}
