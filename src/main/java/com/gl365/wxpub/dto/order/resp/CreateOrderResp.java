package com.gl365.wxpub.dto.order.resp;

import com.gl365.wxpub.dto.BaseDomain;

public class CreateOrderResp extends BaseDomain{

	private static final long serialVersionUID = 8765542578121962648L;

	private String orderSn;
    
	private String tokenId;
	
	private String payInfo;

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}
    
}
