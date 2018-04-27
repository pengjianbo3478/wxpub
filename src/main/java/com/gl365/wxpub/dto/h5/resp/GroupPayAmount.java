package com.gl365.wxpub.dto.h5.resp;

import java.io.Serializable;
import java.math.BigDecimal;

import com.gl365.wxpub.dto.BaseDomain;
import com.gl365.wxpub.util.GsonUtils;

public class GroupPayAmount implements Serializable{

	private static final long serialVersionUID = 8239461979827763977L;

	//总金额
	private BigDecimal totalAmount;
	
	//认领金额
	private BigDecimal rlAmount;
	
	//群主userId
	private String groupOwner;
	
	//群买单商户号
	private String merchantNo;
	
	//主单号
	private String mainOrderNo;
	
	//群主姓名
	private String groupOwnerName;
	
	private String glMerchantNo;
	
	private String merchantName;
	
	private String merchantImgUrl;

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

	public String getGroupOwner() {
		return groupOwner;
	}

	public void setGroupOwner(String groupOwner) {
		this.groupOwner = groupOwner;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	
	public String getMainOrderNo() {
		return mainOrderNo;
	}

	public void setMainOrderNo(String mainOrderNo) {
		this.mainOrderNo = mainOrderNo;
	}

	public String getGroupOwnerName() {
		return groupOwnerName;
	}

	public void setGroupOwnerName(String groupOwnerName) {
		this.groupOwnerName = groupOwnerName;
	}

	public String getGlMerchantNo() {
		return glMerchantNo;
	}

	public void setGlMerchantNo(String glMerchantNo) {
		this.glMerchantNo = glMerchantNo;
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

	@Override
	public String toString() {
		return GsonUtils.toJson(this);
	}
}
