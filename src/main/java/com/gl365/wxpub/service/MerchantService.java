package com.gl365.wxpub.service;

import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.merchant.req.GetOperatorListCommand;
import com.gl365.wxpub.dto.merchant.resp.MerchantBaseInfo;

public interface MerchantService {

	ResultDto<MerchantBaseInfo> getMerchantBaseInfo(String merchantNo);

	/**
	 * 获取是否允许商户在线支付
	 *
	 * @param merchantNo
	 * @param organCode
	 * @return
	 */
	Boolean isMerchantOnlinePay(String merchantNo, String organCode);
	
	/**
	 * 获取商家员工列表
	 * @param req
	 * @return
	 */
	ResultDto<?> getEmployees(GetOperatorListCommand req);
}
