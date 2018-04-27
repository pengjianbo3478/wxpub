package com.gl365.wxpub.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.wxpub.aspect.Login;
import com.gl365.wxpub.common.JsonUtils;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.customize.command.GetCommentTempleteCommand;
import com.gl365.wxpub.service.FeignCilent.MemberClient;
import com.gl365.wxpub.service.FeignCilent.OrderClient;

/**
 * < 模板、广告定制Conroller >
 * 
 * @author hui.li 2017年4月21日 - 上午11:40:54
 * @Since 1.0
 */
@CrossOrigin(origins = "*", maxAge = 3600) // 跨域问题，与前端调试时打开
@RestController
@RequestMapping("/wxpubApi")
public class CustomizeController {

	private static final Logger LOG = LoggerFactory.getLogger(CustomizeController.class);

	@Autowired
	private MemberClient memberService;
	@Autowired
	private OrderClient orderService;
	
	/**
	 * 获取行业数据
	 * 
	 * @param industry
	 * @return
	 */
	@GetMapping("/industry")
	public Object industry() {
		LOG.info("获取行业数据 > > > ");
		try {
			return memberService.getIndustry();
		} catch (Exception e) {
			LOG.error("获取行业数据 > > > 错误：{}", e);
			return ResultDto.errorResult();
		}
	}

}
