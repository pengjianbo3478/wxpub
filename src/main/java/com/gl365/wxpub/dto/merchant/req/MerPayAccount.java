package com.gl365.wxpub.dto.merchant.req;

import java.io.Serializable;

import com.gl365.wxpub.common.JsonUtils;

import io.swagger.annotations.ApiModelProperty;

/**
 * 获取乐豆商户上传
 * @author dfs_519
 *2017年5月22日下午7:52:08
 */
public class MerPayAccount implements Serializable{
	
	private static final long serialVersionUID = 6684569560485129706L;

	@ApiModelProperty(value = "支付机构：10001付费通，10003微众", example = "10001", required = true)
	private String organCode;
	
	@ApiModelProperty(value = "给乐商户号", example = "1706651000082", required = true)
	private String merchantNo; //给乐商户号
	
	@ApiModelProperty(value = "支付公司商户号", example = "805024000000203", required = true)
	private String organMerchantNo; //支付公司商户号
	
	@ApiModelProperty(value = "允许积分联付:0:不允许1:允许", example = "1", required = true)
	private String ldSale; //允许积分联付:0:不允许1:允许

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

	public String getLdSale() {
		return ldSale;
	}

	public void setLdSale(String ldSale) {
		this.ldSale = ldSale;
	}

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}

	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

}
