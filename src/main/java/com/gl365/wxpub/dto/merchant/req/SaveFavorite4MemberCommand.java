package com.gl365.wxpub.dto.merchant.req;

public class SaveFavorite4MemberCommand {

	private String userId;

	private String merchantNo;

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
