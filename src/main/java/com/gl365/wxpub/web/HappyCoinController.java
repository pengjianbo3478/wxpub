package com.gl365.wxpub.web;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.wxpub.aspect.Log;
import com.gl365.wxpub.aspect.Login;
import com.gl365.wxpub.common.Constant;
import com.gl365.wxpub.dto.account.req.ActBalanceInfoReq;
import com.gl365.wxpub.service.AccountService;

@CrossOrigin(origins = "*", maxAge = 3600) // 跨域问题，与前端调试时打开
@RestController
@RequestMapping("/wxpubApi/happyCoin")
public class HappyCoinController {

	@Autowired
	private AccountService accountService;
	
	@Login
	@Log("公众号获取乐豆余额")
	@GetMapping("/getBalance")
	public Object getBalance(HttpServletRequest request){
		ActBalanceInfoReq req = new ActBalanceInfoReq();
		req.setUserId((String)request.getSession().getAttribute(Constant.GL_USER_ID));
		req.setAgentId(Constant.hcAgentID);
		return accountService.getHCBalance(req);
	}
	
	@Log("微信扫码支付获取乐豆余额")
	@GetMapping("/getBalanceByUserID")
	public Object getBalanceByUserID(@RequestParam("userId") String userId){
		ActBalanceInfoReq req = new ActBalanceInfoReq();
		req.setUserId(userId);
		req.setAgentId(Constant.hcAgentID);
		return accountService.getHCBalance(req);
	}
}
