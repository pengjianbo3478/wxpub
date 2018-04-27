package com.gl365.wxpub.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.account.req.ActBalanceInfoReq;
import com.gl365.wxpub.dto.account.rlt.ActBalance;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.service.AccountService;
import com.gl365.wxpub.service.FeignCilent.AccountClient;

@Service
public class AccountServiceImpl implements AccountService{
	
	private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	@Autowired
	private AccountClient accountClient;

	@Override
	public ResultDto<?> getHCBalance(ActBalanceInfoReq req) {
		logger.info("getHCBalance begin,req:{}",req.toString());
		ResultDto<?> resp = null;
		ActBalance rlt = null;
		try {
			rlt = accountClient.queryAccountBalanceInfo(req);
		} catch (Exception e) {
			logger.error("getHCBalance ===> accountClient.queryAccountBalanceInfo exception:" , e);
		}
		if (null != rlt) {
			if (ResultCodeEnum.System.SUCCESS.getCode().equals(rlt.getResultCode())) {
				logger.info("getHCBalance ===> accountClient.queryAccountBalanceInfo success rlt:{}", rlt.toString());
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("amount", rlt.getBalance());
				resp = new ResultDto<>(ResultCodeEnum.System.SUCCESS, data);
			} else {
				logger.error("getHCBalance==>accountClient.queryAccountBalanceInfo 获取乐豆数量失败  rlt：{}", rlt.toString());
				resp = ResultDto.result(ResultCodeEnum.Payment.GET_HAPPY_COIN_COUNT_FAIL);
			}
		} else {
			resp = ResultDto.result(ResultCodeEnum.Payment.GET_HAPPY_COIN_COUNT_FAIL);
		}
		logger.info("getHCBalance end,resp:{}",resp.toString());
		return resp;
	}

}
