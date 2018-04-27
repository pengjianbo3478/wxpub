package com.gl365.wxpub.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.merchant.req.GetCommentStatusReq;
import com.gl365.wxpub.dto.merchant.resp.PersonalCommentStatus;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.service.CommentService;
import com.gl365.wxpub.service.FeignCilent.CommentClient;

@Service
public class CommentServiceImpl implements CommentService{
	
	private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

	@Autowired
	private CommentClient commentClient;
	
	@Override
	public ResultDto<?> getCommentStatus(GetCommentStatusReq req) {
		logger.info("getOrderCommentStatus begin,req={}", req.toString());
		ResultDto<?> resp = null;
		try {
			resp = commentClient.getCommentStatus(req);
			if (ResultCodeEnum.System.SUCCESS.getCode().equals(resp.getResult())) {
				Map<String, Object> dataMap = new HashMap<>();
				dataMap.put("status", resp.getData());
				resp = new ResultDto<>(ResultCodeEnum.System.SUCCESS, dataMap);
			}
		} catch (Exception e) {
			logger.error("getOrderCommentStatus ==> commentClient.getCommentStatus e:" , e);
			resp = new ResultDto<>(ResultCodeEnum.Comment.GET_ORDER_COMMENT_STATUS_FAIL);
		}

		logger.info("getOrderCommentStatus end, resp:{}", resp.toString());
		return resp;
	}

	@Override
	public ResultDto<?> getTipCommentStatus(String req) {
		logger.info("getTipCommentStatus begin,req={}", req);
		ResultDto<?> resp = null;
		List<PersonalCommentStatus> data = null;
		try {
			data = commentClient.getPersonalCommentStatus(req);
		} catch (Exception e) {
			logger.error("getTipCommentStatus ===> commentClient.getPersonalCommentStatus exception:" , e);
			data = new ArrayList<>();
		}
		resp = ResultDto.result(ResultCodeEnum.System.SUCCESS, data);
		logger.info("getTipCommentStatus end,resp:{}", resp);
		return resp;
	}

}
