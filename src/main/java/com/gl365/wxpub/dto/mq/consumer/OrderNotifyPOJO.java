package com.gl365.wxpub.dto.mq.consumer;

import com.gl365.wxpub.dto.BaseDomain;

public class OrderNotifyPOJO extends BaseDomain{

	private static final long serialVersionUID = -5691755858391539616L;

	/**
	 *  refundIng 退款完成通知|refund 退款完成通知|confirm交易确认通知
	 */
	private String tranType;
	
	private RefundPOJO refund;
	
	private PayComfirmPOJO confirm;

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public RefundPOJO getRefund() {
		return refund;
	}

	public void setRefund(RefundPOJO refund) {
		this.refund = refund;
	}

	public PayComfirmPOJO getConfirm() {
		return confirm;
	}

	public void setConfirm(PayComfirmPOJO confirm) {
		this.confirm = confirm;
	}
	
}
