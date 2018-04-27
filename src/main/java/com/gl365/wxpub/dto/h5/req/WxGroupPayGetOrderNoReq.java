package com.gl365.wxpub.dto.h5.req;

import java.math.BigDecimal;

import com.gl365.wxpub.dto.BaseDomain;

public class WxGroupPayGetOrderNoReq extends BaseDomain{

	private static final long serialVersionUID = -7703448726152527282L;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;


    /**
     * 付款人id
     */
    private String userId;

    /**
     * 支付场景 5群主 6参与者
     */
    private Integer paySence;

    /**
     * 操作员id
     */
    private String operatorId;

    /**
     * 认领金额
     */
    private BigDecimal rlAmount;
    
    /**
     * 群id
     */
    private String groupId;
    
    private String merchantNo;

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getPaySence() {
		return paySence;
	}

	public void setPaySence(Integer paySence) {
		this.paySence = paySence;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public BigDecimal getRlAmount() {
		return rlAmount;
	}

	public void setRlAmount(BigDecimal rlAmount) {
		this.rlAmount = rlAmount;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
