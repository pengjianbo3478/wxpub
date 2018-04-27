package com.gl365.wxpub.dto.settlement.resp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl365.wxpub.dto.BaseDomain;

public class PayMain extends BaseDomain{

	private static final long serialVersionUID = 5466660300039616923L;

	private String payId;  //支付订单号、退款单号
	
	private String origPayId;  //退款时才有这个字段，原支付订单号
	
	private String merchantNo;
	
	private String merchantName;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime payTime; //支付(退款)日期
	
	private String scene; //01 线上支付、02 线下支付（POS支付）
	
	private String transType; //交易类型 Operation
	
	private String payStatus; //01：已支付-已消费;03：已部分退货-退款;04：已全额退货-退款  OperationName
	
	private BigDecimal totalAmount; //交易总金额  money
	
	private BigDecimal beanAmount; // 乐豆支付（退回）金额  happyCoin
	
	private BigDecimal giftAmount; //返豆（回收返豆）金额  returnHappyCoin
	
	private BigDecimal cashAmount; //实际支付（实际退款）realMoney
	
	private String merchantOrderNo; //订单系统的订单号
	
	/**
	 * order_type
		订单类型
		1：正常订单（如果订单标题解析出来为空、或者是POS交易，则默认为1）
		2：打赏订单
		3：达人订单
		4：网购订单
	 */
	private String orderType;
	
	private String splitFlag; //分单标志：0-主单 1-子单（分单）
	
	private BigDecimal groupMainuserPay; //群主支付总金额
	
	private BigDecimal groupPtPay;
	
	private BigDecimal groupMainuserPayBean; //群主支付乐豆金额
	
	private BigDecimal groupGiftAmount; // 群成员本单返豆金额
	
	private String groupMerchantNo; //群组消费商家
	
	private String origMerchantOrderNo; //原交易订单号
	
	private BigDecimal noBenefitAmount;
	
	public String getOrigMerchantOrderNo() {
		return origMerchantOrderNo;
	}

	public void setOrigMerchantOrderNo(String origMerchantOrderNo) {
		this.origMerchantOrderNo = origMerchantOrderNo;
	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
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

	public LocalDateTime getPayTime() {
		return payTime;
	}

	public void setPayTime(LocalDateTime payTime) {
		this.payTime = payTime;
	}

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
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

	public BigDecimal getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public String getOrigPayId() {
		return origPayId;
	}

	public void setOrigPayId(String origPayId) {
		this.origPayId = origPayId;
	}
	
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	public String getSplitFlag() {
		return splitFlag;
	}

	public void setSplitFlag(String splitFlag) {
		this.splitFlag = splitFlag;
	}

	public BigDecimal getGroupMainuserPay() {
		return groupMainuserPay;
	}

	public void setGroupMainuserPay(BigDecimal groupMainuserPay) {
		this.groupMainuserPay = groupMainuserPay;
	}

	public BigDecimal getGroupPtPay() {
		return groupPtPay;
	}

	public void setGroupPtPay(BigDecimal groupPtPay) {
		this.groupPtPay = groupPtPay;
	}

	public BigDecimal getGroupMainuserPayBean() {
		return groupMainuserPayBean;
	}

	public void setGroupMainuserPayBean(BigDecimal groupMainuserPayBean) {
		this.groupMainuserPayBean = groupMainuserPayBean;
	}

	public BigDecimal getGroupGiftAmount() {
		return groupGiftAmount;
	}

	public void setGroupGiftAmount(BigDecimal groupGiftAmount) {
		this.groupGiftAmount = groupGiftAmount;
	}

	public String getGroupMerchantNo() {
		return groupMerchantNo;
	}

	public void setGroupMerchantNo(String groupMerchantNo) {
		this.groupMerchantNo = groupMerchantNo;
	}

	public BigDecimal getNoBenefitAmount() {
		return noBenefitAmount;
	}

	public void setNoBenefitAmount(BigDecimal noBenefitAmount) {
		this.noBenefitAmount = noBenefitAmount;
	}
	
}
