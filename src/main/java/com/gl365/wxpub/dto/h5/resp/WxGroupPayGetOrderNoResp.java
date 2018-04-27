package com.gl365.wxpub.dto.h5.resp;

import com.gl365.wxpub.dto.BaseDomain;

public class WxGroupPayGetOrderNoResp extends BaseDomain{

	private static final long serialVersionUID = -5270071071605571819L;

	private String orderNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
}
