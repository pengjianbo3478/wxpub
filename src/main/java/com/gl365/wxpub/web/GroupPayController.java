package com.gl365.wxpub.web;

import java.math.BigDecimal;
import java.util.List;

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
import com.gl365.wxpub.dto.h5.req.WxGroupPayGetOrderNoReq;
import com.gl365.wxpub.dto.h5.req.WxGroupPayReq;
import com.gl365.wxpub.dto.h5.resp.GroupPayAmount;
import com.gl365.wxpub.dto.h5.resp.GroupUserInfo;
import com.gl365.wxpub.enums.GroupPayStatus;
import com.gl365.wxpub.enums.GroupStatus;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.service.GroupService;
import com.gl365.wxpub.service.OrderService;
import com.gl365.wxpub.util.AmountTransferUtils;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600) // 跨域问题，与前端调试时打开
@RestController
@RequestMapping("/wxpubApi/groupPay")
public class GroupPayController {
	
	private static final Logger logger = LoggerFactory.getLogger(GroupPayController.class);

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private GroupService groupService;
	
	@PostMapping("/memberGroupPay")
	@Log("群买单参与者支付接口")
	@ApiOperation(value = "群买单参与者支付接口", httpMethod = "POST", response = ResultDto.class)
	public Object memberGroupPay(@RequestBody WxGroupPayReq req){
		ResultDto<?> resp = null;
		resp = groupMemberBeforePay(req.getGroupId());
		if(ResultCodeEnum.System.SUCCESS.getCode().equals(resp.getResult())){
			return orderService.groupPay(req);
		}else{
			return resp;
		}
	}
	
	@Login
	@PostMapping("/ownerGroupPay")
	@Log("群买单发起者支付接口")
	@ApiOperation(value = "群买单发起者支付接口", httpMethod = "POST", response = ResultDto.class)
	public Object ownerGroupPay(HttpServletRequest request,@RequestBody WxGroupPayReq req){
		String userId = (String)request.getSession().getAttribute(Constant.GL_USER_ID);
		req.setOpenId((String)request.getSession().getAttribute(Constant.GL_WXPUB_OPENID));
		//判断是否有群成员支付状态为支付中，有：更新群状态为群主未支付，返回
		if (!memberPayStatus(req.getGroupId(), userId)) {
			logger.info("有群成员支付状态为支付中，群主暂时无法支付");
			groupService.updateGroup(req.getGroupId(), GroupStatus.BUILD_COMPLETE);
			return ResultDto.result(ResultCodeEnum.GroupPay.GROUP_MEMBER_PAYING);
		}
		return orderService.groupPay(req);
	}
	
	@PostMapping("/memberGetOrderNo")
	@Log("群买单参与者获取订单号接口")
	@ApiOperation(value = "群买单参与者获取订单号接口", httpMethod = "POST", response = ResultDto.class)
	public Object memberGetOrderNo(@RequestBody WxGroupPayGetOrderNoReq req){
		return orderService.groupPayGetOrderNo(req);
	}
	
	@Login
	@PostMapping("/ownerGetOrderNo")
	@Log("群买单发起者获取订单号接口")
	@ApiOperation(value = "群买单发起者获取订单号接口", httpMethod = "POST", response = ResultDto.class)
	public Object ownerGetOrderNo(HttpServletRequest request,@RequestBody WxGroupPayGetOrderNoReq req){
		req.setUserId((String)request.getSession().getAttribute(Constant.GL_USER_ID));
		return orderService.groupPayGetOrderNo(req);
	}
	
	@Login
	@ApiOperation(value = "微信支付-群买单判断是否可以支付", httpMethod = "GET", response = ResultDto.class)
	@GetMapping("/beforePay")
	public Object beforePay(HttpServletRequest request,@RequestParam(value = "groupId",required=true) String groupId,@RequestParam(value = "userId",required=false) String userId,@RequestParam(required=true) String role){
		userId = (String)request.getSession().getAttribute(Constant.GL_USER_ID);
		logger.info("微信支付-群买单判断是否可以支付 begin,userId:{},groupId:{},role:{}",userId,groupId,role);
		GroupUserInfo info = groupService.queryGroupUserInfo(groupId, userId);
		ResultDto<?> resp = null;
		if(null == info){
			resp = ResultDto.result(ResultCodeEnum.GroupPay.GROUP_MEMBER_NOT_EXIST);
		}
		
		if(Constant.GROUPOWNER.equals(role)){
			//先更新群状态为群主支付中
			groupService.updateGroup(groupId, GroupStatus.MAINGROUPER_PAYING);
			
			//获取群信息
			GroupPayAmount gpa = groupService.queryGroupPayAmount(groupId);
			List<GroupUserInfo> list = groupService.queryGroupInfo(groupId);
			if(null == gpa){
				resp = ResultDto.result(ResultCodeEnum.GroupPay.GROUP_NOT_EXIST);
			}else{
				//计算群成员已付金额
				BigDecimal amount = countGroupOwnerAmount(gpa.getTotalAmount(), gpa.getRlAmount(), userId, list);
				if(amount == null){
					//先更新群状态为群主未支付
					groupService.updateGroup(groupId, GroupStatus.BUILD_COMPLETE);
					resp = ResultDto.result(ResultCodeEnum.GroupPay.GROUP_MEMBER_PAYING);
				}else{
					resp = ResultDto.result(ResultCodeEnum.System.SUCCESS,amount);
				}
			}
			
		}else{
			resp = groupMemberBeforePay(groupId);
			if(ResultCodeEnum.System.SUCCESS.getCode().equals(resp.getResult())){
				info.setPayStatus(GroupPayStatus.PAYING);
				groupService.updateGroupUserInfo(groupId, info);
			}
		}
		
		return resp;
	}
	
	@Login
	@ApiOperation(value = "支付页面点击返回", httpMethod = "GET", response = ResultDto.class)
	@GetMapping("/payBack")
	public Object payBack(HttpServletRequest request,@RequestParam(value="userId",required=false) String userId,@RequestParam(value="groupId") String groupId){
		userId = (String)request.getSession().getAttribute(Constant.GL_USER_ID);
		logger.info("payBack begin, userId={},groupId={}", userId, groupId);
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(groupId)) {
			return  ResultDto.result(ResultCodeEnum.System.PARAM_NULL);
		}
		GroupPayAmount gpa = groupService.queryGroupPayAmount(groupId);
		if(gpa != null){
			GroupUserInfo info = groupService.queryGroupUserInfo(groupId, userId);
			if(gpa.getGroupOwner().equals(userId)){
				GroupStatus groupStatue = groupService.queryGroupStatus(groupId);
				if(!GroupStatus.MAINGROUPER_PAYED.equals(groupStatue)){
					groupService.updateGroup(groupId, GroupStatus.BUILD_COMPLETE);
				}
			}
			if(!info.getPayStatus().equals(GroupPayStatus.SUCCESS)){
				info.setPayStatus(GroupPayStatus.INIT);
				groupService.updateGroupUserInfo(groupId, info);
			}
		}
		return ResultDto.result(ResultCodeEnum.System.SUCCESS);
	}
	
	private ResultDto<?> groupMemberBeforePay(String groupId){
		GroupStatus status = groupService.queryGroupStatus(groupId);
		if(null == status){
			return ResultDto.result(ResultCodeEnum.GroupPay.GROUP_NOT_EXIST);
		}else if(GroupStatus.BUILD_COMPLETE.equals(status)){
			return ResultDto.result(ResultCodeEnum.System.SUCCESS);
		}else if(GroupStatus.MAINGROUPER_PAYING.equals(status)){
			return ResultDto.result(ResultCodeEnum.GroupPay.GROUP_OWNER_PAYING);
		}else if(GroupStatus.MAINGROUPER_PAYED.equals(status)){
			return ResultDto.result(ResultCodeEnum.GroupPay.GROUP_OWNER_PAYED);
		}else if(GroupStatus.CANCEL.equals(status)){
			return  ResultDto.result(ResultCodeEnum.GroupPay.GROUP_HAS_CANCEL);
		}
		return ResultDto.result(ResultCodeEnum.System.SUCCESS);
	}
	
	/**
	 * 计算群成员已付金额
	 * @param totalAmount
	 * @param rlAmount
	 * @param userId
	 * @param list
	 * @return null：群成员支付状态为中间状态，群主无法支付
	 */
	private BigDecimal countGroupOwnerAmount(BigDecimal totalAmount,BigDecimal rlAmount,String userId,List<GroupUserInfo> list){
		BigDecimal hasPayAmount = new BigDecimal(0);
		for (GroupUserInfo groupUserInfo : list) {
			if(!userId.equals(groupUserInfo.getUserId())){
				if(GroupPayStatus.SUCCESS.equals(groupUserInfo.getPayStatus())){
					hasPayAmount = AmountTransferUtils.add(hasPayAmount, groupUserInfo.getTotalAmount());
				}else if(GroupPayStatus.PAYING.equals(groupUserInfo.getPayStatus())){
					return null;
				}
			}
		}
		return hasPayAmount;
	}
	
	private boolean memberPayStatus(String groupId,String userId){
		List<GroupUserInfo> list = groupService.queryGroupInfo(groupId);
		for (GroupUserInfo groupUserInfo : list) {
			if(!userId.equals(groupUserInfo.getUserId())){
				if(GroupPayStatus.PAYING.equals(groupUserInfo.getPayStatus())){
					return false;
				}
			}
		}
		return true;
	}
	
}
