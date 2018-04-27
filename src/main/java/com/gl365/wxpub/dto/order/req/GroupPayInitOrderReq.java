package com.gl365.wxpub.dto.order.req;

import java.math.BigDecimal;
import com.gl365.wxpub.dto.BaseDomain;
import com.gl365.wxpub.handler.ConfigHandler;

/**
 * 群买单获取订单号请求参数
 * @author dfs_519
 *2017年9月24日下午1:10:45
 */
public class GroupPayInitOrderReq extends BaseDomain{

	private static final long serialVersionUID = 5204832589708690350L;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;


    /**
     * 付款人id
     */
    private String memberId;

    /**
     * 支付场景
     */
    private Integer paymentConfig;

    /**
     * 操作员id
     */
    private String operatorId;

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
     *  渠道
     */
    private String channel = ConfigHandler.getInstance().getWxpubChannel();
    
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

}
