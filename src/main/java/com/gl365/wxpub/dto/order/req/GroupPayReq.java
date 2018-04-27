package com.gl365.wxpub.dto.order.req;

import java.math.BigDecimal;

import com.gl365.wxpub.dto.BaseDomain;
import com.gl365.wxpub.handler.ConfigHandler;

/**
 * 群买单发起支付请求参数
 * @author dfs_519
 *2017年9月24日下午1:10:09
 */
public class GroupPayReq extends BaseDomain{

	private static final long serialVersionUID = 8205863865927700507L;

	/**
     * 订单标题
     */
    private String orderTitle;
	
	/**
     * 群主应付金额
     */
    private BigDecimal groupMainuserPay;
    
    /**
     * 支付场景
     */
    private Integer paymentConfig;
    
    /**
     * 订单编号
     */
    private String orderSn;
    

    /**
     * 支付成功跳转页
     */
    private String callbackUrl = ConfigHandler.getInstance().getGroupPayCallbackUrl();
    
    private String openId;
    
    private String groupMerchantNo;
    
    /**
     * 操作员id
     */
    private String operatorId;
    
    private String terminal;
    
    public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getGroupMerchantNo() {
		return groupMerchantNo;
	}

	public void setGroupMerchantNo(String groupMerchantNo) {
		this.groupMerchantNo = groupMerchantNo;
	}

	public String getOrderTitle() {
		return orderTitle;
	}

	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}

	public BigDecimal getGroupMainuserPay() {
		return groupMainuserPay;
	}

	public void setGroupMainuserPay(BigDecimal groupMainuserPay) {
		this.groupMainuserPay = groupMainuserPay;
	}

	public Integer getPaymentConfig() {
		return paymentConfig;
	}

	public void setPaymentConfig(Integer paymentConfig) {
		this.paymentConfig = paymentConfig;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
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
	
}
