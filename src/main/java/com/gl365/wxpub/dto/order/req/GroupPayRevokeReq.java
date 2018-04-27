package com.gl365.wxpub.dto.order.req;

import com.gl365.wxpub.dto.BaseDomain;

public class GroupPayRevokeReq extends BaseDomain{

	private static final long serialVersionUID = 1571235540022986741L;

	 /**
     * 订单编号
     */
    private String orderSn;
    
    /**
     * 支付场景  5(主单)
     */
    private Integer paymentConfig;
    
    /**
     * 付款人id
     */
    private String memberId;

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Integer getPaymentConfig() {
		return paymentConfig;
	}

	public void setPaymentConfig(Integer paymentConfig) {
		this.paymentConfig = paymentConfig;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
 
}
