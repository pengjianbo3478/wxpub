package com.gl365.wxpub.common.build;

import com.gl365.wxpub.dto.PageDto;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.merchant.resp.CommentCallback;
import com.gl365.wxpub.dto.merchant.resp.CommentDto;
import com.gl365.wxpub.dto.merchant.resp.PageResultDto;
import com.gl365.wxpub.util.RespUtil;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentBuild {

	public static ResultDto commentCallbackBuild(ResultDto<PageDto<CommentDto>>  source, BigDecimal grade) {
		List<CommentCallback> result = new ArrayList<>();
		Map<String, Object> gradeMap = new HashMap<>();
		gradeMap.put("commentGrade", grade);
		ResultDto<PageDto<CommentCallback>> target = new ResultDto<>();
		PageDto<CommentDto> pageDto = RespUtil.getData(source);
		if (!CollectionUtils.isEmpty(pageDto.getList())){
			pageDto.getList().forEach(commentDto -> {
				result.add(commentCallbackBuild(commentDto));
			});
		}
		PageDto<CommentCallback> targetPageDto = new PageDto<>(source.getData().getTotalCount(), source.getData().getCurPage(), source.getData().getPageSize(), gradeMap, result);
//		target.setResult(source.getResult());
//		target.setDescription(source.getDescription());
//		target.setData(new PageDto<>(source.getData().getTotalCount(), source.getData().getCurPage(), source.getData().getPageSize(), gradeMap, result));
//		// 构建数据集
//		if (CollectionUtils.isEmpty(source.getData().getList())) {
//			return target;
//		}
//		for (CommentDto dto : source.getData().getList()) {
//			result.add(commentCallbackBuild(dto));
//		}
		return ResultDto.success(targetPageDto);
	}

	public static CommentCallback commentCallbackBuild(CommentDto source) {
		CommentCallback target = new CommentCallback();
		target.setPaymentNo(source.getPaymentNo());// 支付订单编号
		target.setMerchantNo(source.getMerchantNo());// 商户编号
		target.setUserId(source.getUserId());// 用户Id
		target.setName(source.getRealName());// 用户显示的名字
		target.setCreateDateTime(source.getCreateTime());// 用户评论时间
		target.setGrade(source.getGrade());// 用户评分
		target.setLabels(source.getLabels());// 评论标签
		target.setUserImage(source.getUserImage());// 头像
		target.setContent(source.getContent());// 内容
		return target;
	}

}
