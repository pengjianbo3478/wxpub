package com.gl365.wxpub.dto.h5.req;

import java.math.BigDecimal;

import com.gl365.wxpub.dto.BaseDomain;

import io.swagger.annotations.ApiModelProperty;

public class PrePayReq extends BaseDomain{

	private static final long serialVersionUID = -4725132512440559876L;

	@ApiModelProperty(value="支付人的会员号",required=true)
	private String userId;
	
	@ApiModelProperty(value="支付人的微信openId",required=true)
	private String openId;
	
	@ApiModelProperty(value="订单标题",required=true)
	private String orderTitle;
	
	@ApiModelProperty(value="支付总金额",required=true)
	private BigDecimal totalAmount;
	
	@ApiModelProperty(value="收款人Id",required=false)
	private String operatorId;
	
	@ApiModelProperty(value="打赏员工userId",notes="打赏订单需要必填",required=false)
	private String rewardUserId;
	
	@ApiModelProperty(value="商户号",required=true)
	private String merchantNo;
	
	@ApiModelProperty(value="打赏原订单号",notes="打赏订单需要必填",required=false)
	private String perOrderNo;
	
	@ApiModelProperty(value="支付场景码",required=true)
    private Integer paySence;
	
	@ApiModelProperty(value="打赏乐豆金额",notes="打赏订单需要必填",required=false)
	private BigDecimal rewardBeanAmount;
	
	@ApiModelProperty(value="支付成功回调url后缀",notes="公众号直接买单需要必填",required=false)
	private String callbackUrlSuffix;
	
	private BigDecimal noBenefitAmount;
	
	private String terminal="00000000";

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getOrderTitle() {
		return orderTitle;
	}

	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getRewardUserId() {
		return rewardUserId;
	}

	public void setRewardUserId(String rewardUserId) {
		this.rewardUserId = rewardUserId;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getPerOrderNo() {
		return perOrderNo;
	}

	public void setPerOrderNo(String perOrderNo) {
		this.perOrderNo = perOrderNo;
	}

	public Integer getPaySence() {
		return paySence;
	}

	public void setPaySence(Integer paySence) {
		this.paySence = paySence;
	}

	public BigDecimal getRewardBeanAmount() {
		return rewardBeanAmount;
	}

	public void setRewardBeanAmount(BigDecimal rewardBeanAmount) {
		this.rewardBeanAmount = rewardBeanAmount;
	}

	public String getCallbackUrlSuffix() {
		return callbackUrlSuffix;
	}

	public void setCallbackUrlSuffix(String callbackUrlSuffix) {
		this.callbackUrlSuffix = callbackUrlSuffix;
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
	
}
