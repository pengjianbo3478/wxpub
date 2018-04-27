package com.gl365.wxpub.service;

import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.account.req.ActBalanceInfoReq;

public interface AccountService {

	/**
	 * 获取乐豆余额
	 * @param req
	 * @return
	 */
	ResultDto<?> getHCBalance(ActBalanceInfoReq req);
}
