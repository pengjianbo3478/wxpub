package com.gl365.wxpub.service.FeignCilent;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gl365.wxpub.dto.ResultDto;

@FeignClient(name="validator",url="${${env:}.url.validator:}")
public interface ValidatorClient {

	/**
	 * 实名认证
	 * @param cardId 身份证
	 * @param name 名字
	 * @return ResultDto<Boolean>
	 */
	@RequestMapping(value = "/validIdCard" , method= RequestMethod.POST)
	public ResultDto<Boolean> certification(@RequestParam("cardId") String cardId,@RequestParam("name") String name);

	/**
	* 验证银行卡
	* @param bankId 银行卡
	* @param cardId 身份证
	* @param name 名字
	* @return ResultDto<Boolean>
	*/
	@RequestMapping(value = "validBankCard" , method= RequestMethod.POST)
	public ResultDto<Boolean> validBankCard (@RequestParam("bankId") String bankId,@RequestParam("cardId") String cardId,
			@RequestParam("name") String name);


	/**
	* 验证银行卡4要素
	* @param bankId 银行卡
	* @param cardId 身份证
	* @param name 名字
	* @param mobile 银行卡绑定的手机
	* @return ResultDto<Boolean>
	*/
	@RequestMapping(value = "validBankCardMobile" , method= RequestMethod.POST)
	public ResultDto<Boolean> validBankCardMobile (@RequestParam("bankId") String bankId, @RequestParam("cardId") String cardId, 
			@RequestParam("name") String name, @RequestParam("mobile") String mobile);

	/**
	 * 手机认证
	 * @param mobileNum 手机号
	 * @param idNum 身份证
	 * @param name 名字
	 * @return ResultDto<Boolean>
	 */
	@RequestMapping(value = "/validMobile" , method= RequestMethod.POST)
	public ResultDto<Boolean> phoneValidate(@RequestParam("mobileNum") String mobileNum, @RequestParam("idNum") String idNum, @RequestParam("name") String name);

}
