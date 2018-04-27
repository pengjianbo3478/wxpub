package com.gl365.wxpub.dto.h5.resp;

import java.math.BigDecimal;

import com.gl365.wxpub.dto.BaseDomain;

public class PayFinishResp extends BaseDomain{

	private static final long serialVersionUID = 3702202715286937402L;

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
     * 商户号
     */
    private String merchantNo;
    
    private String merchantShortName;
    
    private BigDecimal saleRate; //返利率
	
	private String mainImage;
	
	private String userId;
	
    /**
     * 群主应付金额
     */
    private BigDecimal groupMainuserPay;
    
    private BigDecimal noBenefitAmount;

	public BigDecimal getNoBenefitAmount() {
		return noBenefitAmount;
	}

	public void setNoBenefitAmount(BigDecimal noBenefitAmount) {
		this.noBenefitAmount = noBenefitAmount;
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

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getMerchantShortName() {
		return merchantShortName;
	}

	public void setMerchantShortName(String merchantShortName) {
		this.merchantShortName = merchantShortName;
	}

	public BigDecimal getSaleRate() {
		return saleRate;
	}

	public void setSaleRate(BigDecimal saleRate) {
		this.saleRate = saleRate;
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getGroupMainuserPay() {
		return groupMainuserPay;
	}

	public void setGroupMainuserPay(BigDecimal groupMainuserPay) {
		this.groupMainuserPay = groupMainuserPay;
	}
	
}
