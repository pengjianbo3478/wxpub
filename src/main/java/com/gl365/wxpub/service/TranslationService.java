package com.gl365.wxpub.service;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.SearchReq;


/**
 * < 行业配置接口消费 >
 * 
 * @author hui.li 2017年4月27日 - 上午10:32:40
 * @Since 1.0
 */
@FeignClient(name="member")
public interface TranslationService {

	@RequestMapping(value = "/member/translation/searchName", method = RequestMethod.POST)
	public ResultDto<Map<String,String>> selectBySearchName( @RequestBody SearchReq command);
}
