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
import com.gl365.wxpub.dto.settlement.req.SettlementReq;
import com.gl365.wxpub.service.SettlementService;

@CrossOrigin(origins = "*", maxAge = 3600) // 跨域问题，与前端调试时打开
@RestController
@RequestMapping("/wxpubApi/bill")
public class BillController {
	
	@Autowired
	private SettlementService settlementService;
	
	@Login
	@Log("获取账单详情")
	@GetMapping("/getBill")
	public Object getBill(HttpServletRequest request, @RequestParam(name = "month", required = true) Integer month, @RequestParam(name = "curPage", required = true) Integer curPage, @RequestParam(name = "pageSize", required = true) Integer pageSize){
		LocalDate now = LocalDate.now();
		LocalDate date = now.minusMonths(month);
		LocalDate fromDate = date.with(TemporalAdjusters.firstDayOfMonth());
		LocalDate toDate = date.with(TemporalAdjusters.lastDayOfMonth());
		SettlementReq req = new SettlementReq((String)request.getSession().getAttribute(Constant.GL_USER_ID), fromDate, toDate, pageSize, curPage);
		return settlementService.queryBillDetail(req);
	}

}
