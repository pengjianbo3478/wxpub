package com.gl365.wxpub.dto.merchant.resp;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * < 商家详情Dto >
 * 
 * @author hui.li 2017年4月25日 - 上午11:36:09
 * @Since 1.0
 */

/**
 * @author Administrator
 *
 */

/**
 * @author DELL
 *
 */
public class MerchantDetailCallback extends MerchantInfo {

	private static final long serialVersionUID = 6321114842739434401L;

	/**
	 * 商家详情展示图像
	 */
	private List<String> images;

	/**
	 * 是否收藏
	 */
	private boolean favorite;

	/**
	 * 是否消费
	 */
	private boolean isConsume;

	/**
	 * 允许在线支付
	 */
	@ApiModelProperty(value = "允许在线支付: boolean", example = "true", required = true)
	private boolean onlinePay;
	
	@ApiModelProperty(value = "0没开通免返逗；1开通免返逗")
	private Integer noBenefit;

	public boolean isOnlinePay() {
		return onlinePay;
	}

	public void setOnlinePay(boolean onlinePay) {
		this.onlinePay = onlinePay;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public boolean isConsume() {
		return isConsume;
	}

	public void setConsume(boolean isConsume) {
		this.isConsume = isConsume;
	}

	public Integer getNoBenefit() {
		return noBenefit;
	}

	public void setNoBenefit(Integer noBenefit) {
		this.noBenefit = noBenefit;
	}
}