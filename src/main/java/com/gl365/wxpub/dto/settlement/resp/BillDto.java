package com.gl365.wxpub.dto.settlement.resp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 账单基类
 * @author dfs_519
 * 2017年4月22日下午4:28:17
 */
public class BillDto implements Serializable{

	private static final long serialVersionUID = 6837790114563620076L;

	private String paymentNo; //支付订单号
	
	private String returnNo; //退款单号
	
	private String merchantNo; //商户编号
	
	private String merchantName; //商户名称
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime paymentDate; //支付日期
	
	private Integer scene; //支付方式 数据格式待定 0 Pos支付  1 App支付 
	
	private BigDecimal money; //支付金额
	
	private BigDecimal happyCoin; //乐豆支付金额
	
	private BigDecimal returnHappyCoin; //返豆金额
	
	private BigDecimal realMoney; //实际支付金额
	
	private String note; //摘要
	
	private boolean comment; //是否已经发表评论
	
	private String operation; //支付操作
	
	private String operationName; //支付操作名称 
	
	private String orderNo;
	
	private String sceneName;
	
	private String orderType;
	
	private String perOrderNo;
	
	private BigDecimal noBenefitAmount;
	
	public String getPerOrderNo() {
		return perOrderNo;
	}

	public void setPerOrderNo(String perOrderNo) {
		this.perOrderNo = perOrderNo;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderType() {
		return orderType;
	}
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
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

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getHappyCoin() {
		return happyCoin;
	}

	public void setHappyCoin(BigDecimal happyCoin) {
		this.happyCoin = happyCoin;
	}

	public BigDecimal getReturnHappyCoin() {
		return returnHappyCoin;
	}

	public void setReturnHappyCoin(BigDecimal returnHappyCoin) {
		this.returnHappyCoin = returnHappyCoin;
	}

	public BigDecimal getRealMoney() {
		return realMoney;
	}

	public void setRealMoney(BigDecimal realMoney) {
		this.realMoney = realMoney;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public boolean isComment() {
		return comment;
	}

	public void setComment(boolean comment) {
		this.comment = comment;
	}

	public Integer getScene() {
		return scene;
	}

	public void setScene(Integer scene) {
		this.scene = scene;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getReturnNo() {
		return returnNo;
	}

	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}

	public String getSceneName() {
		return sceneName;
	}

	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

	public BigDecimal getNoBenefitAmount() {
		return noBenefitAmount;
	}

	public void setNoBenefitAmount(BigDecimal noBenefitAmount) {
		this.noBenefitAmount = noBenefitAmount;
	}
	
}
