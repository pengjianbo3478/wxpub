package com.gl365.wxpub.service.FeignCilent;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.merchant.req.GetMerchantDetail4MerchantCommand;
import com.gl365.wxpub.dto.merchant.req.GetOperatorListCommand;
import com.gl365.wxpub.dto.merchant.req.MerPayAccount;
import com.gl365.wxpub.dto.merchant.req.MerchantOperatorDto;
import com.gl365.wxpub.dto.merchant.resp.MerPayAccountDto;
import com.gl365.wxpub.dto.merchant.resp.MerchantBaseInfo;
import com.gl365.wxpub.dto.merchant.resp.MerchantInfo;
import com.gl365.wxpub.dto.merchant.resp.MerchantInfoDto;

@FeignClient(name="merchant",url="${${env:}.url.merchant:}")
public interface MerchantClient {

	@RequestMapping(value = "/merchant/basics/detailBybarCodeOrMerchantNo", method = RequestMethod.GET)
	ResultDto<MerchantBaseInfo> queryMerBaseInfo(@RequestParam("merchantNo") String merchantNo);


	/**
	 * 根据多个商户号获取商家信息
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/merchant/getMerchantByMerchantNoList", method = RequestMethod.POST)
	List<MerchantInfoDto> getMerchantByMerchantNoList(@RequestBody List<String> merchantNos);

	/**
	 * 根据预绑定二维码查询商户信息
	 * 
	 * @param barCode
	 * @return
	 */
	@RequestMapping(value = "/merchant/getMerchantByBarCode", method = RequestMethod.GET)
	ResultDto<MerchantInfo> getMerchantByBarCode(@RequestParam("barCode") String barCode);
	
	/**
	 * 获取商家详情,支持批量
	 * 
	 * @param command2Merchant
	 * @return
	 */
	@RequestMapping(value = "/merchant/detail", method = RequestMethod.POST)
	ResultDto<List<MerchantInfoDto>> getMerchantDetail(GetMerchantDetail4MerchantCommand command2Merchant);
	
	/**
	 * 查询商户在第三方的商户号
	 * 
	 * @param organCode
	 *            第三方机构代码
	 * @param merchantNo
	 *            商户号
	 * @return
	 */
	@RequestMapping(value = "/merchant/onPayAccount/query", method = RequestMethod.GET)
	ResultDto<MerPayAccountDto> queryPayAccount(@RequestParam("organCode") String organCode, @RequestParam("merchantNo") String merchantNo);
	
	/**
	 * 获取店家综合评论分数
	 * 
	 * @return
	 */
	@RequestMapping(value = "/merchant/getMerchantScore", method = RequestMethod.GET)
	ResultDto<BigDecimal> getMerchantCommentGrade(@RequestParam("merchantNo") String merchantNo);

	/**
	 * 获取商家基本信息,支持批量
	 * 
	 * @param merchantNos
	 * @return
	 */
	@RequestMapping(value = "/merchant/basics/batch/favoriteDetail", method = RequestMethod.POST)
	ResultDto<List<MerchantInfoDto>> getMerchantBasicDetail4Favorite(@RequestParam("merchantNoList") List<String> merchantNos);

	/**
	 * 员工列表
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/merchant/operator/queryMerchantOpertator", method = RequestMethod.POST)
	public ResultDto<List<MerchantOperatorDto>> getMerchantOpertatorList(@RequestBody GetOperatorListCommand command);

	/**
	 * 查询商户线上支付方式
	 * @param merchantNo
	 * @return
	 */
	@RequestMapping(value = "/merchant/query/onPay/info", method = RequestMethod.GET)
	ResultDto<List<MerPayAccount>> queryOnPayInfo(@RequestParam("merchantNo") String merchantNo);


}
