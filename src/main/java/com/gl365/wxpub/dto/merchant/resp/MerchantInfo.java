package com.gl365.wxpub.dto.merchant.resp;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * < 商家基础信息Callback >
 * 
 * @author hui.li 2017年4月25日 - 上午11:36:09
 * @Since 1.0
 */

/**
 * @author Administrator
 *
 */

/**
 * @author Administrator
 *
 */
public class MerchantInfo implements Serializable {

	private static final long serialVersionUID = -330348230412830032L;

	/**
	 * 商家编号
	 */
	private String merchantNo;

	/**
	 * 商家全称
	 */
	private String name;

	/**
	 * 商家简称
	 */
	private String shortName;

	/**
	 * 返豆率
	 */
	private BigDecimal saleRate;

	/**
	 * 商家简介
	 */
	private String profile;

	/**
	 * 商家图片
	 */
	private String image;

	/**
	 * 商家地址
	 */
	private String address;

	/**
	 * 营业时间
	 */
	private String businessHours;

	/**
	 * 电话
	 */
	private String tel;

	/**
	 * 经度
	 */
	private BigDecimal longitude;

	/**
	 * 纬度
	 */
	private BigDecimal latitude;

	/**
	 * 行业Id
	 */
	private String industry;

	/**
	 * 特色服务 数据待定，可能如下：0001 WIFI 0002 停车 0003 刷卡 0004包厢
	 */
	private List<String> specialService;

	/**
	 * 人均消费
	 */
	private BigDecimal perCapita;

	/**
	 * 消费人数
	 */
	@ApiModelProperty(value = "消费人数", required = false)
	private Integer paymentCount;

	/**
	 * 评论等级
	 */
	private BigDecimal commentGrade;

	/**
	 * 行业名称
	 */
	private String categoryName;

	/**
	 * 商圈名称
	 */
	private String areaName;

	public Integer getPaymentCount() {
		return paymentCount;
	}

	public void setPaymentCount(Integer paymentCount) {
		this.paymentCount = paymentCount;
	}

	public BigDecimal getCommentGrade() {
		return commentGrade;
	}

	public void setCommentGrade(BigDecimal commentGrade) {
		this.commentGrade = commentGrade;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public BigDecimal getSaleRate() {
		return saleRate;
	}

	public void setSaleRate(BigDecimal saleRate) {
		this.saleRate = saleRate;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public List<String> getSpecialService() {
		return specialService;
	}

	public void setSpecialService(List<String> specialService) {
		this.specialService = specialService;
	}

	public BigDecimal getPerCapita() {
		return perCapita;
	}

	public void setPerCapita(BigDecimal perCapita) {
		this.perCapita = perCapita;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

}