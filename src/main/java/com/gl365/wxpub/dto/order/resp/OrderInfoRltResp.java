package com.gl365.wxpub.dto.order.resp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl365.wxpub.common.JsonUtils;

/**
 * 订单系统-订单信息Dto
 * @author dfs_519
 *2017年6月19日上午11:51:36
 */
public class OrderInfoRltResp implements Serializable{
	
	private static final long serialVersionUID = -6962265660001405393L;

	/**
     * 订单编号
     */
    private String orderSn;

    /**
     * 订单标题
     */
    private String orderTitle;

    /**
     * 订单状态
     */
    private Integer orderStatus;

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
     * 支付场景
     */
    private Integer paymentConfig;

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
     * 支付时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime paymentTime;

    /**
     * mq推送时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime pushTime;
	
	/**
     * 创建时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;


    /**
     * 付费通序列号
     */
    private String fftSequence;

    /**
     * 付费通订单号
     */
    private String fftOrderSn;

    private String merchantNo;
    
    /**
     * 群号
     */
    private String groupId;
    
    /**
     * 群买单时间
     */
	private String orderTime;

    /**
     * 主订单状态
     */
    private Integer  mainOrderStatus;
    
    /**
     * 00:地主,01：平民,02：幸运儿
     */
    private String tycoonFlag;
    
    /**
     * 用户头像
     */
  	private String imgUrl;
  	
  	/**
  	 * 用户名称
  	 */
  	private String nickName;
  	
  	/**
     * 商家头像
     */
    private String merchantMainImage;

    /**
     * 商家名称
     */
    private String merchantName;
    
    /**
     *  渠道
     */
    private String channel;

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

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
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

    public String getFftSequence() {
        return fftSequence;
    }

    public void setFftSequence(String fftSequence) {
        this.fftSequence = fftSequence;
    }

    public String getFftOrderSn() {
        return fftOrderSn;
    }

    public void setFftOrderSn(String fftOrderSn) {
        this.fftOrderSn = fftOrderSn;
    }

	public BigDecimal getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
	}

	public LocalDateTime getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(LocalDateTime paymentTime) {
		this.paymentTime = paymentTime;
	}

	public LocalDateTime getPushTime() {
		return pushTime;
	}

	public void setPushTime(LocalDateTime pushTime) {
		this.pushTime = pushTime;
	}
	
	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getMainOrderStatus() {
		return mainOrderStatus;
	}

	public void setMainOrderStatus(Integer mainOrderStatus) {
		this.mainOrderStatus = mainOrderStatus;
	}

	public String getTycoonFlag() {
		return tycoonFlag;
	}

	public void setTycoonFlag(String tycoonFlag) {
		this.tycoonFlag = tycoonFlag;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getMerchantMainImage() {
		return merchantMainImage;
	}

	public void setMerchantMainImage(String merchantMainImage) {
		this.merchantMainImage = merchantMainImage;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}
    
}