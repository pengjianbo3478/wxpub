package com.gl365.wxpub.dto.merchant.resp;


import com.gl365.wxpub.common.JsonUtils;

import java.io.Serializable;

public class PersonalCommentStatus implements Serializable{

	private static final long serialVersionUID = -6765840193457585029L;

	private String paymentNo;
	
	private int status;

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}
	
}
