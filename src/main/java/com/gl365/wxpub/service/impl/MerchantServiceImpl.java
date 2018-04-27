package com.gl365.wxpub.service.impl;

import com.gl365.wxpub.common.JsonUtils;
import com.gl365.wxpub.dto.merchant.req.GetOperatorListCommand;
import com.gl365.wxpub.dto.merchant.req.MerchantOperatorDto;
import com.gl365.wxpub.dto.merchant.resp.MerPayAccountDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.merchant.resp.MerchantBaseInfo;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.service.MerchantService;
import com.gl365.wxpub.service.FeignCilent.MerchantClient;
import com.gl365.wxpub.util.GsonUtils;

@Component
public class MerchantServiceImpl implements MerchantService{

	private static final Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);
	
	@Autowired
	private MerchantClient merchantClient;
	
	@Override
	public ResultDto<MerchantBaseInfo> getMerchantBaseInfo(String merchantNo) {
		ResultDto<MerchantBaseInfo> rlt = null;
		try{
			rlt = merchantClient.queryMerBaseInfo(merchantNo);
		}catch(Exception e){
			logger.error("getMerchantBaseInfo ===> merchantClient.queryMerBaseInfo exception:",e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		if(!ResultCodeEnum.System.SUCCESS.getCode().equals(rlt.getResult())){
			return new ResultDto<>(ResultCodeEnum.System.NO_MERCHANT_INFO); 
		}
		return rlt;
	}

	@Override
	public Boolean isMerchantOnlinePay(String merchantNo, String organCode) {
		logger.info("isOnlinePay begin, merchantNo = {},organCode = {}", merchantNo, organCode);
		ResultDto<MerPayAccountDto> rlt = null;
		try {
			rlt = merchantClient.queryPayAccount(organCode, merchantNo);
		} catch (Exception e) {
			logger.error("isOnlinePay ===> merchantInfoService.queryPayAccount exception,e:" + e);
			return false;
		}
		if (null != rlt && ResultCodeEnum.System.SUCCESS.getCode().equals(rlt.getResult()) && null != rlt.getData()) {
			logger.info("isOnlinePay ===> merchantInfoService.queryPayAccount success,rlt:{}", rlt.toString());
			return true;
		} else {
			logger.error("isOnlinePay ===> merchantInfoService.queryPayAccount error,rlt:{}", JsonUtils.toJsonString(rlt));
			return false;
		}
	}

	@Override
	public ResultDto<?> getEmployees(GetOperatorListCommand req) {
		logger.info("getEmployees begin,req:{}",GsonUtils.toJson(req));
		ResultDto<?> resp = null;
		try{
			ResultDto<List<MerchantOperatorDto>> result = merchantClient.getMerchantOpertatorList(req);
			resp = buildOperatorList(result);
		}catch(Exception e){
			logger.error("getEmployees exception,e:",e);
			resp = new ResultDto<>(ResultCodeEnum.Merchant.GET_EMPLOYEES_FAIL);
		}
		logger.info("getEmployees end,req:{}",GsonUtils.toJson(resp));
		return resp;
	}
	
	private ResultDto<?> buildOperatorList(ResultDto<List<MerchantOperatorDto>> source) {
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
		return ResultDto.result(source, data);
	}
}
