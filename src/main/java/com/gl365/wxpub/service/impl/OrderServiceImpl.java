package com.gl365.wxpub.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gl365.wxpub.common.JsonUtils;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.h5.req.WxGroupPayGetOrderNoReq;
import com.gl365.wxpub.dto.h5.req.WxGroupPayReq;
import com.gl365.wxpub.dto.h5.req.PrePayReq;
import com.gl365.wxpub.dto.h5.resp.GroupUserInfo;
import com.gl365.wxpub.dto.h5.resp.MainOrderStatue;
import com.gl365.wxpub.dto.h5.resp.PayFinishResp;
import com.gl365.wxpub.dto.h5.resp.PrePayResp;
import com.gl365.wxpub.dto.h5.resp.WxGroupPayGetOrderNoResp;
import com.gl365.wxpub.dto.merchant.resp.MerchantBaseInfo;
import com.gl365.wxpub.dto.order.req.CommentPersonal;
import com.gl365.wxpub.dto.order.req.CreateOrderReq;
import com.gl365.wxpub.dto.order.req.GroupPayInitOrderReq;
import com.gl365.wxpub.dto.order.req.GroupPayReq;
import com.gl365.wxpub.dto.order.req.GroupPayRevokeReq;
import com.gl365.wxpub.dto.order.req.QueryOrderInfoReq;
import com.gl365.wxpub.dto.order.resp.CreateOrderResp;
import com.gl365.wxpub.dto.order.resp.MainOrderInfo;
import com.gl365.wxpub.dto.order.resp.OrderComfirmInfo;
import com.gl365.wxpub.dto.order.resp.QueryOrderInfoResp;
import com.gl365.wxpub.enums.GroupPayStatus;
import com.gl365.wxpub.enums.GroupStatus;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.handler.ConfigHandler;
import com.gl365.wxpub.service.GroupService;
import com.gl365.wxpub.service.MerchantService;
import com.gl365.wxpub.service.OrderService;
import com.gl365.wxpub.service.FeignCilent.OrderClient;
import com.gl365.wxpub.service.async.AsyncServiceImpl;
import com.gl365.wxpub.util.AmountTransferUtils;
import com.gl365.wxpub.util.GsonUtils;
import com.google.gson.JsonObject;

@Component
public class OrderServiceImpl implements OrderService{

	private static final String CALLBACKURL_SUFFIX = "/wx_pay_finish?orderNo=%s";

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private OrderClient orderClient;
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private AsyncServiceImpl asyncServiceImpl;
	
	@Override
	public ResultDto<?> preOrder(PrePayReq req) {
		if(StringUtils.isNotBlank(req.getUserId()) && StringUtils.isNotBlank(req.getRewardUserId()) && req.getUserId().equals(req.getRewardUserId())){
			return new ResultDto<>(ResultCodeEnum.Payment.CANNOT_REWARD_TO_SELF);
		}
		
		CreateOrderReq reqParam = buildCreateOrderReq(req);
		ResultDto<CreateOrderResp> rlt = null;
		ResultDto<?> resp = null;
		try{
			rlt = orderClient.createOrder(reqParam);
			logger.info("preOrder ===> orderClient.createOrder rlt:{}",rlt.toString());
		}catch(Exception e){
			logger.error("preOrder ===> orderClient.createOrder exception,e:",e);
		}
		if(9 == req.getPaySence()){
			if(rlt != null){
				if( ResultCodeEnum.System.SUCCESS.getCode().equals(rlt.getResult()) ){
					asyncUpdatePersonCommentStatus(req.getUserId(), req.getPerOrderNo());
					resp = new ResultDto<PrePayResp>(ResultCodeEnum.System.SUCCESS);
				}else{
					resp = ResultDto.wxPayRltConvert(rlt.getResult(), rlt.getDescription());
					if(resp == null){
						resp = new ResultDto<PrePayResp>(ResultCodeEnum.Payment.BEAN_TIP_PAY_FAIL);
					}
				}
			}else{
				resp = new ResultDto<PrePayResp>(ResultCodeEnum.Payment.BEAN_TIP_PAY_FAIL);
			}
		}else{
			if(rlt != null){
				if(ResultCodeEnum.System.SUCCESS.getCode().equals(rlt.getResult()) && null != rlt.getData() && StringUtils.isNotBlank(rlt.getData().getTokenId())){
					PrePayResp data = new PrePayResp();
					data.setPayInfo(rlt.getData().getPayInfo());
					if(StringUtils.isNotBlank(rlt.getData().getTokenId())){
						if(rlt.getData().getTokenId().startsWith("http")){
							data.setPayUrl(rlt.getData().getTokenId());
						}else{
							data.setPayUrl(String.format(ConfigHandler.getInstance().getJsPayUrl(), rlt.getData().getTokenId()));
						}
					}
					resp = new ResultDto<PrePayResp>(ResultCodeEnum.System.SUCCESS, data);
				}else{
					resp = ResultDto.wxPayRltConvert(rlt.getResult(), rlt.getDescription());
					if(resp == null){
						resp = new ResultDto<>(ResultCodeEnum.Payment.PERPAY_FAIL);
					}
				}
			}else{
				resp = new ResultDto<>(ResultCodeEnum.Payment.PERPAY_FAIL);
			}
		}
		return resp;
	}
	
	@Override
	public ResultDto<?> payInfo(String orderNo,String flag) {
		QueryOrderInfoReq req = new QueryOrderInfoReq();
		req.setOrderSn(orderNo);
		ResultDto<QueryOrderInfoResp> oi = null;
		try{
			oi = orderClient.queryOrderInfo(req);
			logger.info("payInfo ===> orderClient.queryOrderInfo,rlt:{}",oi.toString());
		}catch(Exception e){
			logger.error("买单成功后查询订单信息 payInfo ===> orderClient.queryOrderInfo exception,e:",e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		logger.info("买单成功后查询订单信息 rlt:{}",oi.toString());
		if(ResultCodeEnum.System.SUCCESS.getCode().equals(oi.getResult()) && oi.getData() != null){
			PayFinishResp data = new PayFinishResp();
			BeanUtils.copyProperties(oi.getData(), data);
			data.setUserId(oi.getData().getMemberId());
			if("0".equals(flag)){
				ResultDto<MerchantBaseInfo> mi = merchantService.getMerchantBaseInfo(data.getMerchantNo());
				logger.info("买单成功后查询商家信息 rlt:{}",mi.toString());
				if(ResultCodeEnum.System.SUCCESS.getCode().equals(mi.getResult()) && null != mi.getData()){
					data.setMerchantShortName(mi.getData().getMerchantShortName());
					data.setMainImage(mi.getData().getMainImage());
					data.setSaleRate(mi.getData().getSaleRate());
				}
			}else if("1".equals(flag) || "2".equals(flag)){
				updateGroupMemberPayStatus(oi.getData().getGroupId(), oi.getData().getMemberId(),flag);
			}
			return new ResultDto<PayFinishResp>(ResultCodeEnum.System.SUCCESS, data);
		}else{
			return new ResultDto<>(ResultCodeEnum.Payment.GET_ORDER_INFO_FAIL);
		}
	}

	@Override
	public ResultDto<?> groupPayGetOrderNo(WxGroupPayGetOrderNoReq req) {
		GroupPayInitOrderReq reqParam = buildGroupPayInitOrderReq(req);
		ResultDto<CreateOrderResp> resp = null;
		try{
			resp = orderClient.groupPayInitOrder(reqParam);
			logger.info("groupPayGetOrderNo ===> orderClient.groupPayInitOrder resp:{}",resp.toString());
		}catch(Exception e){
			logger.error("groupPayGetOrderNo ===> orderClient.groupPayInitOrder exception,e:",e);
		}
		if(resp != null && ResultCodeEnum.System.SUCCESS.getCode().equals(resp.getResult()) && null != resp.getData()){
			WxGroupPayGetOrderNoResp data = new WxGroupPayGetOrderNoResp();
			data.setOrderNo(resp.getData().getOrderSn());
			return new ResultDto<WxGroupPayGetOrderNoResp>(ResultCodeEnum.System.SUCCESS, data);
		}else{
			return new ResultDto<>(ResultCodeEnum.Payment.GROUP_PAY_GET_ORDER_NO_FAIL);
		}
	}

	@Override
	public ResultDto<?> groupPay(WxGroupPayReq req) {
		GroupPayReq reqParam = buildGroupPayReq(req);
		ResultDto<CreateOrderResp> rlt = null;
		ResultDto<?> resp = null;
		try{
			rlt = orderClient.groupPayOrder(reqParam);
			logger.info("groupPay ===> orderClient.groupPayOrder rlt:{}",rlt.toString());
		}catch(Exception e){
			logger.error("groupPay ===> orderClient.groupPayOrder exception,e:",e);
		}
		if(rlt != null){
			if(ResultCodeEnum.System.SUCCESS.getCode().equals(rlt.getResult()) && null != rlt.getData() && StringUtils.isNotBlank(rlt.getData().getTokenId())){
				PrePayResp data = new PrePayResp();
				data.setPayInfo(rlt.getData().getPayInfo());
				if(StringUtils.isNotBlank(rlt.getData().getTokenId())){
					if(rlt.getData().getTokenId().startsWith("http")){
						data.setPayUrl(rlt.getData().getTokenId());
					}else{
						data.setPayUrl(String.format(ConfigHandler.getInstance().getJsPayUrl(), rlt.getData().getTokenId()));
					}
				}
				resp = new ResultDto<PrePayResp>(ResultCodeEnum.System.SUCCESS, data);
			}else{
				resp = ResultDto.wxPayRltConvert(rlt.getResult(), rlt.getDescription());
				if(resp == null){
					resp = new ResultDto<>(ResultCodeEnum.Payment.PERPAY_FAIL);
				}
			}
		}else{
			resp = new ResultDto<>(ResultCodeEnum.Payment.PERPAY_FAIL);
		}
		return resp;
	}
	
	private CreateOrderReq buildCreateOrderReq(PrePayReq reqParam){
		CreateOrderReq req = new CreateOrderReq();
		if(9 == reqParam.getPaySence() || 10 == reqParam.getPaySence()){
			req.setMerchantNo(ConfigHandler.getInstance().getGlMerchantNo());
			req.setTotalAmount(AmountTransferUtils.add(reqParam.getTotalAmount(), reqParam.getRewardBeanAmount()));
		}else{
			req.setMerchantNo(reqParam.getMerchantNo());
			req.setTotalAmount(reqParam.getTotalAmount());
		}
		
		req.setMemberId(reqParam.getUserId());
		req.setOpenId(reqParam.getOpenId());
		req.setOperatorId(reqParam.getOperatorId());
		req.setOrderTitle(reqParam.getOrderTitle());
		req.setOrigOrderSn(reqParam.getPerOrderNo());
		req.setPayLdMoney(reqParam.getRewardBeanAmount());
		req.setPaymentConfig(reqParam.getPaySence());
		req.setRewardUserId(reqParam.getRewardUserId());
		req.setNoBenefitAmount(reqParam.getNoBenefitAmount());
		req.setTerminal(reqParam.getTerminal());
		StringBuffer sb = new StringBuffer(ConfigHandler.getInstance().getCallBackUrl());
		if(StringUtils.isNotBlank(reqParam.getCallbackUrlSuffix())){
			req.setCallbackUrl(sb.append(reqParam.getCallbackUrlSuffix()).toString());
		}else{
			req.setCallbackUrl(sb.append(CALLBACKURL_SUFFIX).toString());
		}
		return req;
	}

	private GroupPayInitOrderReq buildGroupPayInitOrderReq(WxGroupPayGetOrderNoReq req){
		GroupPayInitOrderReq reqParam = new GroupPayInitOrderReq();
		BeanUtils.copyProperties(req, reqParam);
		reqParam.setMemberId(req.getUserId());
		reqParam.setAloneAmount(req.getRlAmount());
		reqParam.setPaymentConfig(req.getPaySence());
		if(6 == req.getPaySence()){
			reqParam.setMerchantNo(ConfigHandler.getInstance().getGlMerchantNo());
		}
		return reqParam;
	}
	
	private GroupPayReq buildGroupPayReq(WxGroupPayReq req){
		GroupPayReq reqParam = new GroupPayReq();
		BeanUtils.copyProperties(req, reqParam);
		reqParam.setOrderSn(req.getOrderNo());
		reqParam.setPaymentConfig(req.getPaySence());
		reqParam.setGroupMerchantNo(req.getMerchantNo());
		return reqParam;
	}

	@Override
	public ResultDto<?> queryMainOrderStatue(String orderNo) {
		logger.info("queryMainOrderStatue begin, orderNo={}", orderNo);
		ResultDto<?> resp = null;
		ResultDto<MainOrderInfo> rlt = null;
		try{
			rlt = orderClient.getOrderMainBySn(orderNo);
			if(null == rlt || null == rlt.getData() || StringUtils.isBlank(rlt.getData().getOrderSn()) || StringUtils.isBlank(rlt.getData().getGroupId())){
				resp = ResultDto.result(ResultCodeEnum.System.SYSTEM_ERROR);
			}else{
				MainOrderStatue info = new MainOrderStatue();
				info.setPayStatue(rlt.getData().getOrderStatus());
				info.setGroupId(rlt.getData().getGroupId());
				resp = ResultDto.result(ResultCodeEnum.System.SUCCESS,info);
			}
		}catch(Exception e){
			logger.error("queryMainOrderStatue ===> orderService.getOrderMainBySn exception,e:" , e);
			resp = ResultDto.result(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		logger.info("queryMainOrderStatue end, resp:{}", GsonUtils.toJson(resp));
		return resp;
	}
	
	private void updateGroupMemberPayStatus(String groupId,String userId,String role){
		if("1".equals(role)){
			groupService.updateGroup(groupId, GroupStatus.MAINGROUPER_PAYED);
		}
		GroupUserInfo info = groupService.queryGroupUserInfo(groupId, userId);
		if(null != info){
			info.setPayStatus(GroupPayStatus.SUCCESS);
			groupService.updateGroupUserInfo(groupId, info);
		}
	}
	
	private void asyncUpdatePersonCommentStatus(String userId,String perOrderNo){
		CommentPersonal req = new CommentPersonal();
		req.setStatus(2);
		req.setPaymentNo(perOrderNo);
		req.setUserId(userId);
		asyncServiceImpl.updatePersonCommentStatus(req);
		return;
	}

	@Override
	public boolean groupPayCancel(GroupPayRevokeReq req) {
		logger.info("groupPayCancel begin,req:{}",req.toString());
		ResultDto<?> rlt = null;
		try{
			rlt = orderClient.groupPayRevoke(req);
		}catch(Exception e){
			logger.error("groupPayCancel ===> orderClient.groupPayRevoke exception,e:",e);
			return false;
		}
		logger.info("groupPayCancel ===> orderClient.groupPayRevoke rlt:{}",JsonUtils.toJsonString(rlt));
		if(ResultCodeEnum.System.SUCCESS.getCode().equals(rlt.getResult())){
			return true;
		}else{
			return false;
		}
	
	}
	
	@Override
	public ResultDto<?> orderPayComfirm(String orderNo) {
		logger.info("orderPayComfirm begin,orderNo:{}",orderNo);
		ResultDto<?> rlt = null;
		try{
			ResultDto<OrderComfirmInfo> resp = orderClient.orderPayComfirm(orderNo);
			logger.info("orderPayComfirm ===> orderClient.orderPayComfirm success,resp:{}",resp.toString());
			if(resp.getData().getOrderStatus() == 1){
				rlt = ResultDto.result(ResultCodeEnum.System.SUCCESS);
			}else if(resp.getData().getOrderStatus() == 5 || resp.getData().getOrderStatus() == 8){
				rlt = ResultDto.result(ResultCodeEnum.Payment.PAY_COMFIRM_FAIL);
			}else{
				rlt = ResultDto.result(ResultCodeEnum.Payment.PAY_STATUS_UNKNOW);
			}
		}catch(Exception e){
			logger.error("orderPayComfirm ===> orderClient.orderPayComfirm exception,e:",e);
			rlt = ResultDto.errorResult();
		}
		return rlt;
	}

	@Override
	public ResultDto<?> queryOrderInfoByOrderNo(String orderNo) {
		logger.info("queryOrderInfoByOrderNo begin,orderNo:{}",orderNo);
		QueryOrderInfoReq req = new QueryOrderInfoReq();
		req.setOrderSn(orderNo);
		ResultDto<QueryOrderInfoResp> oi = null;
		try{
			oi = orderClient.queryOrderInfo(req);
			logger.info("queryOrderInfoByOrderNo ===> orderClient.queryOrderInfo,rlt:{}",oi.toString());
		}catch(Exception e){
			logger.error("queryOrderInfoByOrderNo ===> orderClient.queryOrderInfo exception,e:",e);
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		if(ResultCodeEnum.System.SUCCESS.getCode().equals(oi.getResult()) && oi.getData() != null){
			PayFinishResp data = new PayFinishResp();
			BeanUtils.copyProperties(oi.getData(), data);
			data.setUserId(oi.getData().getMemberId());
			ResultDto<MerchantBaseInfo> mi = merchantService.getMerchantBaseInfo(data.getMerchantNo());
			logger.info("查询订单详情补全商户信息 rlt:{}",mi.toString());
			if(ResultCodeEnum.System.SUCCESS.getCode().equals(mi.getResult()) && null != mi.getData()){
				data.setMerchantShortName(mi.getData().getMerchantShortName());
				data.setMainImage(mi.getData().getMainImage());
				data.setSaleRate(mi.getData().getSaleRate());
			}
			return new ResultDto<PayFinishResp>(ResultCodeEnum.System.SUCCESS, data);
		}else{
			return new ResultDto<>(ResultCodeEnum.Payment.GET_ORDER_INFO_FAIL);
		}
	}
	
}
