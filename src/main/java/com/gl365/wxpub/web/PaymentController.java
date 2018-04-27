package com.gl365.wxpub.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.wxpub.aspect.Log;
import com.gl365.wxpub.aspect.Login;
import com.gl365.wxpub.common.Constant;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.h5.req.PrePayReq;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.service.OrderService;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600) // 跨域问题，与前端调试时打开
@RestController
@RequestMapping("/wxpubApi/payment")
public class PaymentController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/prePay")
	@Log("微信扫码支付-预下单接口")
	@ApiOperation(value = "微信扫码支付-预下单接口", httpMethod = "POST", response = ResultDto.class)
	public Object prePay(@RequestBody PrePayReq req){
		return orderService.preOrder(req);
	}
	
	@GetMapping("/pay_finish")
	@Log("微信扫码普通支付成功,获取订单信息")
	public Object payFinish(@RequestParam(value="orderNo",required=true) String orderNo){
		return orderService.payInfo(orderNo,"0");
	}
	
	@GetMapping("/order_detail")
	@Log("微信交易消息查询订单详情")
	public Object orderDetail(@RequestParam(value="orderNo",required=true) String orderNo){
		return orderService.queryOrderInfoByOrderNo(orderNo);
	}
	
	@Login
	@GetMapping("/groupPay_finish")
	@Log("微信扫码群买单成功,获取订单信息")
	public Object groupPayFinish(@RequestParam(value="orderNo",required=true) String orderNo,@RequestParam(value="role",required=true) String role){
		return orderService.payInfo(orderNo,role);
	}
	
	@Login
	@GetMapping("/mainOrderStatue")
	@Log("微信扫码群买单成功,查询主单支付状态")
	public Object mainOrderStatue(@RequestParam(value="orderNo",required=true) String orderNo){
		return orderService.queryMainOrderStatue(orderNo);
	}
	
	@Login
	@PostMapping("/wxpub_prePay")
	@Log("微信公众号支付-预下单接口")
	@ApiOperation(value = "微信公众号支付-预下单接口", httpMethod = "POST", response = ResultDto.class)
	public Object wxpubPrePay(HttpServletRequest request,@RequestBody PrePayReq req){
		req.setUserId((String)request.getSession(false).getAttribute(Constant.GL_USER_ID));
		req.setOpenId((String)request.getSession(false).getAttribute(Constant.GL_WXPUB_OPENID));
		return orderService.preOrder(req);
	}
	
	@Login
	@Log("微信公众号支付-交易确认")
	@GetMapping("/pay_comfirm")
	public Object payComfirm(@RequestParam(value="orderNo",required=true) String orderNo,@RequestParam(value="status",required=false) String status){
		if(StringUtils.isNotBlank(status) && "1".equals(status)){
			return orderService.orderPayComfirm(orderNo);
		}else{
			return ResultDto.result(ResultCodeEnum.Payment.PAY_STATUS_UNKNOW);
		}
	}
}
