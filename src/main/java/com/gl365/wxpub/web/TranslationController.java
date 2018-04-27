package com.gl365.wxpub.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.wxpub.common.JsonUtils;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.SearchReq;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.service.TranslationService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.ApiOperation;

/**
 * 撤单控制器
 * @author dfs_508 2017年10月18日 下午5:54:46
 */
@CrossOrigin(origins = "*", maxAge = 3600) // 跨域问题，与前端调试时打开
@RestController
@RequestMapping("/wxpubApi/member/translation")
public class TranslationController {

	private static final Logger LOG = LoggerFactory.getLogger(TranslationController.class);
	
	@Autowired
	private TranslationService translationService;
	
	@ApiOperation(value = "百度查询")
	@PostMapping("/searchName")
	public ResultDto<?> cancelorderDetail(@RequestBody SearchReq command) {
		LOG.info("cancelorderDetail begin param={}", JsonUtils.toJsonString(command));
    	Long beginTime = System.currentTimeMillis();
    	ResultDto<?> rlt = null;
		try {
			if (null==command) {
				return new ResultDto<>(ResultCodeEnum.System.SUCCESS);
			}
			if (StringUtils.isBlank(command.getSearchName())) {
				return new ResultDto<>(ResultCodeEnum.System.SUCCESS);
			}
			rlt = translationService.selectBySearchName(command);
//			rlt = translationService.selectBySearchName();
		} catch (Exception e) {
			LOG.error("cancelorderDetail exception,e:{}",e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("cancelorderDetail end rlt={},time={}ms",JsonUtils.toJsonString(rlt),(endTime-beginTime));
		return rlt;
	}
	

}
