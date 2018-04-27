package com.gl365.wxpub.dto.order.req;

import java.math.BigDecimal;
import com.gl365.wxpub.dto.BaseDomain;
import com.gl365.wxpub.handler.ConfigHandler;

public class CreateOrderReq extends BaseDomain{

	private static final long serialVersionUID = -4708571837345978757L;

	private String orderTitle;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 付款人id
     */
    private String memberId;

    /**
     * 支付场景
     */
    private Integer paymentConfig;

    /**
     * 操作员id
     */
    private String operatorId;

    /**
     * 被打赏人id
     */
    private String rewardUserId;

    /**
     * 被打赏原始单号
     */
    private String origOrderSn;

    /**
     * 商户号
     */
    private String merchantNo;
    
    /**
     * 认领金额
     */
    private BigDecimal aloneAmount;
    

    /**
     * 群id
     */
    private String groupId;
    
    //----------------微信接入添加字段------------------------------
    
    /**
     *  渠道
     */
    private String channel = ConfigHandler.getInstance().getWxpubChannel();
    /**
     * 支付成功跳转页
     */
    private String callbackUrl;
    
    private String openId;
    
    private BigDecimal payLdMoney;
    
    private String orderSn;
    
    private BigDecimal noBenefitAmount;
    
    private String terminal;
    
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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Integer getPaymentConfig() {
		return paymentConfig;
	}

	public void setPaymentConfig(Integer paymentConfig) {
		this.paymentConfig = paymentConfig;
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

	public String getOrigOrderSn() {
		return origOrderSn;
	}

	public void setOrigOrderSn(String origOrderSn) {
		this.origOrderSn = origOrderSn;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public BigDecimal getAloneAmount() {
		return aloneAmount;
	}

	public void setAloneAmount(BigDecimal aloneAmount) {
		this.aloneAmount = aloneAmount;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public BigDecimal getPayLdMoney() {
		return payLdMoney;
	}

	public void setPayLdMoney(BigDecimal payLdMoney) {
		this.payLdMoney = payLdMoney;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
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
