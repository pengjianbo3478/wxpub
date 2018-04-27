package com.gl365.wxpub.service.FeignCilent;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gl365.wxpub.dto.account.req.ActBalanceInfoReq;
import com.gl365.wxpub.dto.account.rlt.ActBalance;

/**
 * 账户系统服务
 * 
 */
@FeignClient(name="account",url="${${env:}.url.account:}")
public interface AccountClient {


	/**
	 * 查询账户余额信息
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/account/queryAccountBalanceInfo", method = RequestMethod.POST)
	ActBalance queryAccountBalanceInfo(@RequestBody ActBalanceInfoReq req);

}
