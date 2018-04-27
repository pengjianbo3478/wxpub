package com.gl365.wxpub.common.build;


import com.gl365.wxpub.dto.merchant.resp.MerchantDetailCallback;
import com.gl365.wxpub.dto.merchant.resp.MerchantInfoDto;

public class MerchantBuild {

	public static MerchantDetailCallback buildMerchantCallback(MerchantInfoDto source) {
		if (null == source)
			return null;
		MerchantDetailCallback target = new MerchantDetailCallback();
		target.setMerchantNo(source.getMerchantNo());
		target.setName(source.getMerchantName());
		target.setShortName(source.getMerchantShortname());
		target.setSaleRate(source.getSaleRate());
		target.setProfile(source.getMerchantDesc());
		target.setAddress(source.getBusinessAddress());
		target.setBusinessHours(source.getBusinessTime());
		target.setTel(source.getOfficeTel());
		target.setLongitude(source.getLongitude());
		target.setLatitude(source.getLatitude());
		target.setPerCapita(source.getAvgPrice());
		target.setImage(source.getMainImage());
		target.setPaymentCount(source.getConsumeCount());// 消费人数
		target.setCommentGrade(source.getCommentGrade()); // 综合评分
		target.setAreaName(source.getBusAreaName()); // 商圈
		target.setIndustry(source.getCategoryId()); // 行业ID
		target.setCategoryName(source.getCategoryName()); // 行业名称
		target.setSpecialService(source.getSpecialService());// 特色服务[]
		target.setImages(source.getImages()); // 商家图片
		target.setNoBenefit(source.getNoBenefit());//0没开通免返逗；1开通免返逗
		return target;
	}
}
