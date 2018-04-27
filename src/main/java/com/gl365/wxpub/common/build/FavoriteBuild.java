package com.gl365.wxpub.common.build;

import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.merchant.resp.MerchantDetailCallback;
import com.gl365.wxpub.dto.merchant.resp.MerchantFavoriteCallback;
import com.gl365.wxpub.dto.merchant.resp.MerchantInfoDto;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class FavoriteBuild {

	public static ResultDto<List<MerchantFavoriteCallback>> buildFavoriteCallback(ResultDto<List<MerchantInfoDto>> source) {
		ResultDto<List<MerchantFavoriteCallback>> target = new ResultDto<>(source.getResult(), source.getDescription(), buildFavoriteCallback(source.getData()));
		return target;
	}

	public static List<MerchantFavoriteCallback> buildFavoriteCallback(List<MerchantInfoDto> source) {
		if (CollectionUtils.isEmpty(source))
			return new ArrayList<>(); // TODO 不改边响应数据结构 前端未判断null情况
		List<MerchantFavoriteCallback> target = new ArrayList<>();
		for (MerchantInfoDto temp : source) {
			target.add(buildFavoriteCallback(temp));
		}
		return target;
	}

	public static MerchantFavoriteCallback buildFavoriteCallback(MerchantInfoDto source) {
		MerchantFavoriteCallback target = new MerchantFavoriteCallback();
		MerchantDetailCallback result = MerchantBuild.buildMerchantCallback(source);
		if (null == result)
			return target;
		BeanUtils.copyProperties(result, target); // 继承关系直接copy
		return target;
	}

}
