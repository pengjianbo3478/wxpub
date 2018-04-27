package com.gl365.wxpub.web;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.wxpub.aspect.Login;
import com.gl365.wxpub.common.Constant;
import com.gl365.wxpub.common.JsonUtils;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.h5.req.BuildGroupReq;
import com.gl365.wxpub.dto.h5.req.CancelGroupReq;
import com.gl365.wxpub.dto.h5.req.InGroupReq;
import com.gl365.wxpub.dto.h5.req.WxGroupPayGetOrderNoReq;
import com.gl365.wxpub.dto.h5.resp.GroupMemberInfo;
import com.gl365.wxpub.dto.h5.resp.GroupPayAmount;
import com.gl365.wxpub.dto.h5.resp.GroupUserInfo;
import com.gl365.wxpub.dto.h5.resp.WxGroupPayGetOrderNoResp;
import com.gl365.wxpub.dto.order.req.GroupPayRevokeReq;
import com.gl365.wxpub.dto.order.req.UpdateOrderInfoReq;
import com.gl365.wxpub.dto.order.resp.CreateOrderResp;
import com.gl365.wxpub.enums.GroupStatus;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.handler.ConfigHandler;
import com.gl365.wxpub.service.GroupService;
import com.gl365.wxpub.service.OrderService;
import com.gl365.wxpub.service.FeignCilent.OrderClient;
import com.gl365.wxpub.util.GsonUtils;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600) // 跨域问题，与前端调试时打开
@RestController
@RequestMapping("/wxpubApi/groupManagement")
public class GroupManagementController {
	
	private static final Logger logger = LoggerFactory.getLogger(GroupManagementController.class);
	
	@Autowired
	private GroupService groupService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderClient orderClient;
	
	@Login
	@ApiOperation(value = "获取认领金额比例", httpMethod = "GET", response = ResultDto.class)
	@GetMapping("/rlAmountPro")
	public Object rlAmountPro(HttpServletRequest request){
		ResultDto<?> resp = null;
		
		Map<String,String> data = new HashMap<>();
		data.put("min", ConfigHandler.getInstance().getMinProportion());
		data.put("max", ConfigHandler.getInstance().getMaxProportion());
		data.put("totalAmountMin", ConfigHandler.getInstance().getTotalAmountMin());
		data.put("countDownSec", ConfigHandler.getInstance().getCountDownSec());
		resp = ResultDto.result(ResultCodeEnum.System.SUCCESS,data);
		
		logger.info("rlAmountPro end,resp:{}",JsonUtils.toJsonString(resp));
		return resp;
	}
	
	@Login
	@ApiOperation(value = "参与者扫码进群", httpMethod = "POST", response = ResultDto.class)
	@PostMapping("/qrCodeInGroup")
	public Object qrCodeInGroup(HttpServletRequest request,@RequestBody InGroupReq req){
		logger.info("qrCodeInGroup begin,req:{}",GsonUtils.toJson(req));
		ResultDto<?> resp = null;
		
		//参数校验
		if (req == null || !req.validateParamIsNull("userId", "userName","imgUrl", "groupId")) {
			return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
		}
		
		GroupUserInfo info = groupService.queryGroupUserInfo(req.getGroupId(), req.getUserId());
		GroupPayAmount gpa = groupService.queryGroupPayAmount(req.getGroupId());
		if(info != null){
//			if(req.getUserId().equals(gpa.getGroupOwner())){
//				resp = ResultDto.result(ResultCodeEnum.System.GROUP_OWNER_CANNOT_QRINGROUP);
//			}else{
//			}
			logger.info("member has in group");
			resp = ResultDto.result(ResultCodeEnum.System.SUCCESS,groupService.queryGroupPayAmount(req.getGroupId()));
		}else{
			resp = groupService.InGroup(req.getGroupId(), req.getUserId(), req.getUserName(), req.getImgUrl());
			if(ResultCodeEnum.System.SUCCESS.getCode().equals(resp.getResult())){
				resp = ResultDto.result(ResultCodeEnum.System.SUCCESS,groupService.queryGroupPayAmount(req.getGroupId()));
			}
		}
		
		logger.info("qrCodeInGroup end,resp:{}",GsonUtils.toJson(resp));
		return resp;
	}
	
	@Login
	@ApiOperation(value = "参与者退群", httpMethod = "GET", response = ResultDto.class)
	@GetMapping("/outGroup")
	public Object outGroup(HttpServletRequest request,@RequestParam(name="groupId",required=true) String groupId,@RequestParam(name="userId",required=true) String userId){
		logger.info("outGroup begin,userId:{},groupId:{}",userId,groupId);
		ResultDto<?> resp = null;
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(groupId)) {
			return ResultDto.result(ResultCodeEnum.System.PARAM_NULL);
		}
		
		GroupStatus status = groupService.queryGroupStatus(groupId);
		if(status == null){
			resp = ResultDto.result(ResultCodeEnum.GroupPay.GROUP_NOT_EXIST);
		}else if(GroupStatus.DISMISS.getStatus().equals(status.getStatus()) || GroupStatus.INIT.getStatus().equals(status.getStatus())){
			groupService.outGroup(groupId, userId);
			resp = ResultDto.result(ResultCodeEnum.System.SUCCESS);
		}else{
			resp = ResultDto.result(ResultCodeEnum.GroupPay.GROUP_PAY_BEGIN_OUTGROUP_FAIL);
		}
		logger.info("outGroup end,resp:{}",GsonUtils.toJson(resp));
		return resp;
	}
	
	@Login
	@ApiOperation(value = "查询群成员信息", httpMethod = "GET", response = ResultDto.class)
	@GetMapping("/queryGroupMember")
	public Object queryGroupMember(HttpServletRequest request,@RequestParam(name="groupId",required=true) String groupId){
		logger.info("queryGroupMember begin,groupId:{}",groupId);
		ResultDto<?> resp = null;
		if (StringUtils.isEmpty(groupId)) {
			return ResultDto.result(ResultCodeEnum.System.PARAM_NULL);
		}
		
		GroupStatus status = groupService.queryGroupStatus(groupId);
		GroupMemberInfo data = new GroupMemberInfo();
		data.setStatus(status);
		if(GroupStatus.INIT.getStatus().equals(status.getStatus())){
			List<GroupUserInfo> info = groupService.queryGroupInfo(groupId);
			data.setMemberInfo(info);
		}
		
		resp = ResultDto.result(ResultCodeEnum.System.SUCCESS,data);
		logger.info("queryGroupMember end,resp:{}",GsonUtils.toJson(resp));
		return resp;
	}
	
	@Login
	@ApiOperation(value = "发起群买单，完成建群，生成群买单二维码", httpMethod = "POST", response = ResultDto.class)
	@PostMapping("/buildGroup")
	public Object buildGroup(HttpServletRequest request,@RequestBody BuildGroupReq req){
		if(StringUtils.isBlank(req.getApiVersion())){
			return ResultDto.result(ResultCodeEnum.GroupPay.GROUP_PAY_IS_UPGRADING);
		}
		
		String userId = (String)request.getSession().getAttribute(Constant.GL_USER_ID);
		String openId = (String)request.getSession().getAttribute(Constant.GL_WXPUB_OPENID);
		int bindFlag = (int)request.getSession().getAttribute(Constant.GL_WXPUB_BINDFLAG);
		BigDecimal beanAmount = (BigDecimal)request.getSession().getAttribute(Constant.GL_WXPUB_BEANAMOUNT);
		logger.info("buildGroup begin userId:{},req:{}",userId,JsonUtils.toJsonString(req));
		ResultDto<?> resp = null;
		
		//参数校验
		if (req == null || !req.validateParamIsNull("userName","imgUrl", "totalAmount","merchantNo", "merchantName","merchantImgUrl")) {
			return ResultDto.result(ResultCodeEnum.System.PARAM_NULL);
		}
		
		//生成订单号
		String mainOrderNo = getOrderNo(userId, req.getMerchantNo(), 5, req.getTotalAmount(), req.getRlAmount(),null);
		if(StringUtils.isBlank(mainOrderNo)){
			return ResultDto.result(ResultCodeEnum.GroupPay.CREATE_MAINORDER_FAIL);
		}
		
		//建群
		String groupId = mainOrderNo;
		if(!groupService.buildGroup(groupId)){
			return ResultDto.result(ResultCodeEnum.GroupPay.BUILD_GROUP_FAIL);
		}
		
		//保存群公共参数
		GroupPayAmount gpa = buildGroupPayAmount(req.getMerchantNo(), req.getTotalAmount(), req.getRlAmount(), userId, mainOrderNo, req.getUserName(), req.getMerchantImgUrl(), req.getMerchantName());
		groupService.setGroupPayAmount(groupId, gpa);
		
		//发起者进群
		ResultDto<?> inRlt = groupService.InGroup(groupId, userId, req.getUserName(), req.getImgUrl());
		if(!ResultCodeEnum.System.SUCCESS.getCode().equals(inRlt.getResult())){
			return ResultDto.result(inRlt,null);
		}
		
		//拼接进群二维码地址
		StringBuffer sb = new StringBuffer(ConfigHandler.getInstance().getGroupPayQRCodeUrl());
		sb.append("groupId=").append(groupId);
		sb.append("&userId=").append(userId);
		sb.append("&userName=").append(req.getUserName());
		sb.append("&totalAmount=").append(req.getTotalAmount());
		sb.append("&rlAmount=").append(req.getRlAmount());
		sb.append("&mainOrderNo=").append(mainOrderNo);
		sb.append("&merchantNo=").append(req.getMerchantNo());
		sb.append("&glMerchantNo=").append(ConfigHandler.getInstance().getGlMerchantNo());
		sb.append("&merchantName=").append(req.getMerchantName());
		sb.append("&merchantImgUrl=").append(req.getMerchantImgUrl());
		sb.append("&myUserId=").append(userId);
		sb.append("&openId=").append(openId);
		sb.append("&bindFlag=").append(bindFlag);
		sb.append("&beanBalance=").append(beanAmount);
		
		
		resp = ResultDto.result(ResultCodeEnum.System.SUCCESS,sb.toString());
		logger.info("buildGroup end,resp:{}",JsonUtils.toJsonString(resp));
		return resp;
	}

	@Login
	@ApiOperation(value = "群主解散群", httpMethod = "GET", response = ResultDto.class)
	@GetMapping("/disMissGroup")
	public Object disMissGroup(HttpServletRequest request,@RequestParam(required=true) String groupId){
		String userId = (String)request.getSession().getAttribute(Constant.GL_USER_ID);
		logger.info("disMissGroup begin,userId:{},groupId:{}",userId,groupId);
		ResultDto<?> resp = null;
		
		GroupStatus status = groupService.queryGroupStatus(groupId);
		GroupPayAmount gpa = groupService.queryGroupPayAmount(groupId);
		if(gpa == null || status == null){
			resp = ResultDto.result(ResultCodeEnum.GroupPay.GROUP_NOT_EXIST);
		}else if(GroupStatus.DISMISS.getStatus().equals(status.getStatus())){
			resp = ResultDto.result(ResultCodeEnum.System.SUCCESS);
		}else if(GroupStatus.INIT.getStatus().equals(status.getStatus())){
			groupService.updateGroup(groupId, GroupStatus.DISMISS);
			updateOrderClose(gpa.getMainOrderNo(), 5);
			resp = ResultDto.result(ResultCodeEnum.System.SUCCESS);
		}else{
			resp = ResultDto.result(ResultCodeEnum.GroupPay.GROUP_PAY_BEGIN_DISGROUP_FAIL);
		}
		
		logger.info("disMissGroup end,resp:{}",JsonUtils.toJsonString(resp));
		return resp;
	}
	
	@Login
	@ApiOperation(value = "发起者撤销群买单", httpMethod = "POST", response = ResultDto.class)
	@PostMapping("/cancelGroup")
	public Object cancelGroup(HttpServletRequest request,@RequestBody CancelGroupReq req){
		String userId = (String)request.getSession().getAttribute(Constant.GL_USER_ID);
		logger.info("cancelGroup begin,userId:{},req:{}",userId,req.getGroupId());
		ResultDto<?> resp = null;
		if (req == null || !req.validateParamIsNull("mainOrderNo", "groupId")) {
			return ResultDto.result(ResultCodeEnum.System.PARAM_NULL);
		}
		
		GroupStatus status = groupService.queryGroupStatus(req.getGroupId());
		if(status == null){
			resp = ResultDto.result(ResultCodeEnum.GroupPay.GROUP_NOT_EXIST);
		}else if(GroupStatus.INIT.equals(status) || GroupStatus.DISMISS.equals(status)){
			resp = ResultDto.result(ResultCodeEnum.GroupPay.GROUP_INIT_CANNOT_CANCEL);
		}else if(GroupStatus.CANCEL.equals(status)){
			resp = ResultDto.result(ResultCodeEnum.System.SUCCESS);
		}else if(GroupStatus.MAINGROUPER_PAYED.equals(status)){
			resp = ResultDto.result(ResultCodeEnum.GroupPay.GROUP_OWNER_HAS_PAY_CANNOT_CANCEL);
		}else{
			GroupPayRevokeReq reqParam = new GroupPayRevokeReq();
			reqParam.setMemberId(userId);
			reqParam.setOrderSn(req.getMainOrderNo());
			reqParam.setPaymentConfig(5);
			boolean rlt = orderService.groupPayCancel(reqParam);
			if(rlt){
				groupService.updateGroup(req.getGroupId(), GroupStatus.CANCEL);
				resp = ResultDto.result(ResultCodeEnum.System.SUCCESS);
			}else{
				resp = ResultDto.result(ResultCodeEnum.GroupPay.CANCEL_GROUP_FAIL);
			}
		}
		logger.info("cancelGroup end,resp:{}",resp.toString());
		return resp;
	}

	private String getOrderNo(String userId,String merchantNo,int scene,BigDecimal totalAmount,BigDecimal rlAmount,String groupId){
		WxGroupPayGetOrderNoReq reqParam= new WxGroupPayGetOrderNoReq();
		reqParam.setRlAmount(rlAmount);
		reqParam.setUserId(userId);
		reqParam.setMerchantNo(merchantNo);
		reqParam.setPaySence(scene);
		reqParam.setTotalAmount(totalAmount);
		ResultDto<?> resp = orderService.groupPayGetOrderNo(reqParam);
		if(resp.getData()!=null){
			WxGroupPayGetOrderNoResp wr = (WxGroupPayGetOrderNoResp)resp.getData();
			return wr.getOrderNo();
		}else{
			return null;
		}
	}
	
	private GroupPayAmount buildGroupPayAmount(String merchantNo,BigDecimal totalAmount,BigDecimal rlAmount,String groupOwner,String mainOrderNo,String userName,String merchantImgUrl,String merchantName){
		GroupPayAmount gpa = new GroupPayAmount();
		gpa.setGroupOwner(groupOwner);
		gpa.setMainOrderNo(mainOrderNo);
		gpa.setMerchantNo(merchantNo);
		gpa.setRlAmount(rlAmount);
		gpa.setTotalAmount(totalAmount);
		gpa.setGlMerchantNo(ConfigHandler.getInstance().getGlMerchantNo());
		gpa.setGroupOwnerName(userName);
		gpa.setMerchantImgUrl(merchantImgUrl);
		gpa.setMerchantName(merchantName);
		return gpa;
	}
	
	private void updateOrderClose(String orderNo, int orderType) {
		UpdateOrderInfoReq req = new UpdateOrderInfoReq();
		req.setOrderSn(orderNo);
		req.setOrderType(orderType);
		req.setOrderStatus(7);
		orderClient.updataOrderInfo(req);
		return;
	}
}
