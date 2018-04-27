package com.gl365.wxpub.dto.order.req;

import com.gl365.wxpub.dto.BaseDomain;

public class CommentPersonal extends BaseDomain{

	private static final long serialVersionUID = 6422056869988434015L;

	private String paymentNo;
	
	private String userId;
	
	private Integer status;

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
