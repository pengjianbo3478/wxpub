package com.gl365.wxpub.dto.merchant.resp;

import java.math.BigDecimal;

import com.gl365.wxpub.dto.BaseDomain;

public class MerchantBaseInfo extends BaseDomain{

	private static final long serialVersionUID = -4917059612908866243L;

	private String merchantNo;
	
	private String merchantShortName;
	
	private BigDecimal saleRate; //返利率
	
	private String mainImage;
	
	private Integer noBenefit; //0未开通，1已开通

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

	public Integer getNoBenefit() {
		return noBenefit;
	}

	public void setNoBenefit(Integer noBenefit) {
		this.noBenefit = noBenefit;
	}
}
