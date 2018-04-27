package com.gl365.wxpub.dto.h5.req;

import java.io.Serializable;
import java.math.BigDecimal;

import com.gl365.wxpub.util.GsonUtils;

public class GetOpenIdReq implements Serializable{

	private static final long serialVersionUID = -6150106992903099875L;

	private String merchantNo;
	
	private String operatorId;
	
	private BigDecimal totalAmount;
	
	private String groupId;
	
	private String saleRate;
	
	private String redirectUrl;
	
	private String openId;
	
	private BigDecimal noBenefitAmount;
	
	private String terminal="00000000";
	
	//活动专用参数。1：活动地址，2：积分商城
	private String urlType; 
	
	private String drawId;

	public String getDrawId() {
		return drawId;
	}

	public void setDrawId(String drawId) {
		this.drawId = drawId;
	}

	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getSaleRate() {
		return saleRate;
	}

	public void setSaleRate(String saleRate) {
		this.saleRate = saleRate;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public BigDecimal getNoBenefitAmount() {
		return noBenefitAmount;
	}

	public void setNoBenefitAmount(BigDecimal noBenefitAmount) {
		this.noBenefitAmount = noBenefitAmount;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	@Override
	public String toString() {
		return GsonUtils.toJson(this);
	}
}
