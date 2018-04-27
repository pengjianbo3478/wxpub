package com.gl365.wxpub.dto.merchant.req;

import java.math.BigDecimal;

public class SaveCommentPersonalCommand {

	private String paymentNo;// 原付费通支付订单号

	private String merchantNo;// 商家编号

	private String operatorId;// 评论(打赏)对象用户ID

	private BigDecimal tip;// 打赏金额(消费)

	private Integer grade;// 评分
	
	private BigDecimal beanTip;// 打赏乐豆金额

	public SaveCommentPersonalCommand() {
		super();
	}

	public SaveCommentPersonalCommand(SaveCommentPersonalCommand command) {
		paymentNo = command.getPaymentNo();
		merchantNo = command.getMerchantNo();
		operatorId = command.getOperatorId();
		tip = command.getTip();
		grade = command.getGrade();
		beanTip = command.getBeanTip();
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

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public BigDecimal getTip() {
		return tip;
	}

	public void setTip(BigDecimal tip) {
		this.tip = tip;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public BigDecimal getBeanTip() {
		return beanTip;
	}

	public void setBeanTip(BigDecimal beanTip) {
		this.beanTip = beanTip;
	}

}
