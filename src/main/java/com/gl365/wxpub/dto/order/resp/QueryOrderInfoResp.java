package com.gl365.wxpub.dto.order.resp;

import java.math.BigDecimal;
import com.gl365.wxpub.dto.BaseDomain;

public class QueryOrderInfoResp extends BaseDomain{

	private static final long serialVersionUID = -2513208971204166427L;

	/**
     * 订单编号
     */
    private String orderSn;

    /**
     * 订单标题
     */
    private String orderTitle;

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
    
    /**
     * 主单状态
     */
    private Integer  mainOrderStatus;
    
    /**
     * 群主应付金额
     */
    private BigDecimal groupMainuserPay;
   
    /**
	 * 不可返利金额
	 */
	private BigDecimal noBenefitAmount;
	
	public BigDecimal getNoBenefitAmount() {
		return noBenefitAmount;
	}

	public void setNoBenefitAmount(BigDecimal noBenefitAmount) {
		this.noBenefitAmount = noBenefitAmount;
	}

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

	public Integer getMainOrderStatus() {
		return mainOrderStatus;
	}

	public void setMainOrderStatus(Integer mainOrderStatus) {
		this.mainOrderStatus = mainOrderStatus;
	}

	public BigDecimal getGroupMainuserPay() {
		return groupMainuserPay;
	}

	public void setGroupMainuserPay(BigDecimal groupMainuserPay) {
		this.groupMainuserPay = groupMainuserPay;
	}
    
}
