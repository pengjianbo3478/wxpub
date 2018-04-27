package com.gl365.wxpub.dto.merchant.build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.merchant.req.MerchantOperatorDto;

public class OperatorBuild {

	public static Object buildOperatorList(ResultDto<List<MerchantOperatorDto>> source) {
		if (CollectionUtils.isEmpty(source.getData()))
			return source;
		List<Map<String, Object>> data = new ArrayList<>();
		for (MerchantOperatorDto dto : source.getData()) {
			Map<String, Object> map = new HashMap<>();
			map.put("name", dto.getOperatorName());
			map.put("userId", dto.getUserId());
			map.put("photo", dto.getImage());
			map.put("operatorNo", dto.getOperatorNo());
			map.put("operatorId", dto.getOperatorId());
			data.add(map);
		}
		ResultDto<?> result = new ResultDto<>(data);
		result.setResult(source.getResult());
		result.setDescription(source.getDescription());
		return result;
	}

}
