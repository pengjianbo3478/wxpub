package com.gl365.wxpub.dto.mq.consumer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl365.wxpub.dto.BaseDomain;

public class PayComfirmPOJO extends BaseDomain{

	private static final long serialVersionUID = 8850549305552742100L;

	/**
     * 订单编号
     */
    private String orderSn;
    
    /**
     * 订单标题
     */
    private String orderTitle;

    /**
     * 订单状态 订单状态 0未付款，1完成付款，2付款中，,5退货，7超时作废，8, 退款进行中,9付款失败10没有查询到支付公司退款数据
     */
    private Integer orderStatus;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 乐豆金额
     */
    private BigDecimal beanAmount;

    /**
     * 现金金额
     */
    private BigDecimal cashAmount;

    /**
     * 返利金额(返乐豆)
     */
    private BigDecimal giftAmount;

    /**
     * 付款人id
     */
    private String memberId;

    /**
     * 支付场景 0, "正常订单" 5, "群买单主单" 6, "群买单子单"
     */
    private Integer paymentConfig;

    /**
     * 支付确认支付时间
     */
    private String paymentTime;

    /**
     * 商户号
     */
    private String merchantNo;
    
    /**
     *  渠道
     */
    private String channel;

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getOrderTitle() {
		return orderTitle;
	}

	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getBeanAmount() {
		return beanAmount;
	}

	public void setBeanAmount(BigDecimal beanAmount) {
		this.beanAmount = beanAmount;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public BigDecimal getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
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

	public String getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
    
}
