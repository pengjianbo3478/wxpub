package com.gl365.wxpub.dto.merchant.resp;


import com.gl365.wxpub.common.JsonUtils;

import java.io.Serializable;

public class MerPayAccountDto implements Serializable{

	private static final long serialVersionUID = -1994859521418851623L;

	private String ldSale; //允许积分联付:0:不允许1:允许 
	
	private String merchantNo; //给乐商户号
	
	private String organMerchantNo; //支付公司商户号

	public String getLdSale() {
		return ldSale;
	}

	public void setLdSale(String ldSale) {
		this.ldSale = ldSale;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getOrganMerchantNo() {
		return organMerchantNo;
	}

	public void setOrganMerchantNo(String organMerchantNo) {
		this.organMerchantNo = organMerchantNo;
	}

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}
	
}
