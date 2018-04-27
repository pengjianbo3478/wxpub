package com.gl365.wxpub.dto.merchant.req;

public class IsFavoriteCommand {

	private String userId;

	private String merchantNo;

	public IsFavoriteCommand() {
		super();
	}

	public IsFavoriteCommand(String userId, String merchantNo) {
		super();
		this.userId = userId;
		this.merchantNo = merchantNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

}
