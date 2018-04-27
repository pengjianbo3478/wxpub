package com.gl365.wxpub.dto.h5.req;

import java.math.BigDecimal;

import com.gl365.wxpub.dto.BaseDomain;

import io.swagger.annotations.ApiModelProperty;

public class BuildGroupReq extends BaseDomain{

	private static final long serialVersionUID = 5076907415439028292L;

	@ApiModelProperty(value = "发起人姓名(掩码)", required = true)
	private String userName;
	
	@ApiModelProperty(value = "发起人头像url", required = true)
	private String imgUrl;
	
	@ApiModelProperty(value = "总金额", required = true)
	private BigDecimal totalAmount;
	
	@ApiModelProperty(value = "认领", required = false)
	private BigDecimal rlAmount;
	
	@ApiModelProperty(value = "商户号", required = true)
	private String merchantNo;
	
	@ApiModelProperty(value = "商户名字", required = true)
	private String merchantName;
	
	@ApiModelProperty(value = "商户头像url", required = true)
	private String merchantImgUrl;
	
	private String apiVersion;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getRlAmount() {
		return rlAmount;
	}

	public void setRlAmount(BigDecimal rlAmount) {
		this.rlAmount = rlAmount;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantImgUrl() {
		return merchantImgUrl;
	}

	public void setMerchantImgUrl(String merchantImgUrl) {
		this.merchantImgUrl = merchantImgUrl;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

}
