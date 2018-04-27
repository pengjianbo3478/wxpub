package com.gl365.wxpub.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.wxpub.aspect.Log;
import com.gl365.wxpub.aspect.Login;
import com.gl365.wxpub.common.CommonHelper;
import com.gl365.wxpub.common.Constant;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.member.resp.ActiveMerchantResp;
import com.gl365.wxpub.dto.merchant.req.MerchantActivityReq;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.service.PromotionService;

/**
 * 重写判断活动商户方法
 * @author dfs_519
 *2017年12月6日下午3:11:29
 */
@CrossOrigin(origins = "*", maxAge = 3600) // 跨域问题，与前端调试时打开
@RestController
@RequestMapping("/wxpubApi/active")
public class ActiveController {

	@Autowired
	private PromotionService promotionService;
	
	@Login
	@Log("判断商户是否为参与活动商户")
	@PostMapping("/activeMerchant")
	public ResultDto<ActiveMerchantResp> activeMerchant(@RequestBody MerchantActivityReq req) {
		if(StringUtils.isBlank(req.getMerchantNo()) || null == req.getTotalAmout()){
			return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
		}
		req.setOpenId((String)CommonHelper.getRequest().getSession().getAttribute(Constant.GL_WXPUB_OPENID));
		req.setUserId((String)CommonHelper.getRequest().getSession().getAttribute(Constant.GL_USER_ID));
		int bindFlag = (int)CommonHelper.getRequest().getSession().getAttribute(Constant.GL_WXPUB_BINDFLAG);
		if(1 == bindFlag){
			req.setMobileNo((String)CommonHelper.getRequest().getSession().getAttribute(Constant.GL_WXPUB_MOBLIEPHONE));
		}
		ActiveMerchantResp data = promotionService.isActivity(req, bindFlag, "1");
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, data);
	}
	
}
