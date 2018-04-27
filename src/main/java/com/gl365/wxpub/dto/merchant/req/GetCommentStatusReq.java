package com.gl365.wxpub.dto.merchant.req;


import com.gl365.wxpub.dto.BaseDomain;

public class GetCommentStatusReq extends BaseDomain {

	private static final long serialVersionUID = 5023671551939470268L;

	private String merchantNo;
	
	private String userId;
	
	private String paymentNo;

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	
}
