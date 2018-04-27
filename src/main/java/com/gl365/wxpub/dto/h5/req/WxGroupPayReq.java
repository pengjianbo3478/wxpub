package com.gl365.wxpub.dto.h5.req;

import java.math.BigDecimal;

import com.gl365.wxpub.dto.BaseDomain;
import com.gl365.wxpub.handler.ConfigHandler;

public class WxGroupPayReq extends BaseDomain{

	private static final long serialVersionUID = 5438699108656044304L;

	private String orderTitle;
	
	/**
     * 群主应付金额
     */
    private BigDecimal groupMainuserPay;
    
    /**
     * 支付场景
     */
    private Integer paySence;
    
    /**
     * 订单编号
     */
    private String orderNo;
    

    private String openId;
    
    private String groupId;
    
    /**
     * 商户号
     */
    private String merchantNo;
    
    private String operatorId;
    
    private String terminal="00000000";

	public String getOperatorId() {
		return operatorId;
	}


	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
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


	public Integer getPaySence() {
		return paySence;
	}


	public void setPaySence(Integer paySence) {
		this.paySence = paySence;
	}


	public String getOrderNo() {
		return orderNo;
	}


	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	public String getOpenId() {
		return openId;
	}


	public void setOpenId(String openId) {
		this.openId = openId;
	}


	public String getGroupId() {
		return groupId;
	}


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getMerchantNo() {
		return merchantNo;
	}


	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}


	public String getTerminal() {
		return terminal;
	}


	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
    
}
