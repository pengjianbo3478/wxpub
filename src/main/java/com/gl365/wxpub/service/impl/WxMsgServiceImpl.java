package com.gl365.wxpub.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl365.wxpub.dto.PayMsgTemp;
import com.gl365.wxpub.dto.RefundMsgTemp;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.SendWxpubMsgResp;
import com.gl365.wxpub.dto.WxpubSendMsg;
import com.gl365.wxpub.dto.message.WxpubMsg;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.handler.ConfigHandler;
import com.gl365.wxpub.service.MemberService;
import com.gl365.wxpub.service.WeixinService;
import com.gl365.wxpub.service.WxMsgService;
import com.gl365.wxpub.service.FeignCilent.MessageClient;
import com.gl365.wxpub.util.GsonUtils;

@Service
public class WxMsgServiceImpl implements WxMsgService{
	
	private static final Logger logger = LoggerFactory.getLogger(WxMsgServiceImpl.class);
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MessageClient messageClient;
	
	@Autowired
	private WeixinService weixinService;

	@Override
	public boolean sendWxPayMsg(String userId, PayMsgTemp temp) {
		logger.info("sendWxPayMsg begin,userId:{},temp:{}",userId,temp);
		
		//1获取openId
		String openId = memberService.getOpenIdByUserId(userId);
		logger.info("sendWxPayMsg =============== 1、根据userId:{}获取openId:{}",userId,openId);
		if(StringUtils.isBlank(openId)){
			logger.error("sendWxPayMsg end,openId is null");
			return false;
		}
		
		//2保存消息
		if(saveWxpubMsg(temp.getKeyword1().getValue(), GsonUtils.toJson(temp), openId, userId)){
			logger.info("sendWxPayMsg =============== 2、保存消息成功,orderNo:{}",temp.getKeyword1().getValue());
		}else{
			logger.error("sendWxPayMsg end,saveWxpubMsg fail");
			return false;
		}
		
		//3发送消息
		WxpubSendMsg<PayMsgTemp> msg = new WxpubSendMsg<PayMsgTemp>();
		msg.setData(temp);
		msg.setTemplate_id(ConfigHandler.getInstance().getPayMsgTemplateID());
		msg.setTouser(openId);
		msg.setUrl(String.format(ConfigHandler.getInstance().getPayDetailUrl(), temp.getKeyword1().getValue(),temp.getKeyword4().getValue()));
		String jsonStr = null;
		try{
			jsonStr = weixinService.sendMsg(msg);
		}catch(Exception e){
			logger.error("sendWxPayMsg ===> weixinService.sendMsg exception,e:",e);
		}
		logger.info("sendWxPayMsg =============== 3、发送消息,微信返回:{},orderNo:{}",jsonStr,temp.getKeyword1().getValue());
		
		//更新消息发送状态
		SendWxpubMsgResp resp = null;
		if(StringUtils.isNotBlank(jsonStr)){
			resp = GsonUtils.fromJson2Object(jsonStr, SendWxpubMsgResp.class);
		}
		
		logger.info("sendWxPayMsg  =============== 4、更新消息发送状态,orderNo:{}",temp.getKeyword1().getValue());
		updateSendMsgStatus(resp, temp.getKeyword1().getValue());
		return true;
	}

	@Override
	public boolean sendWxRefundMsg(String userId, RefundMsgTemp temp,String status) {

		logger.info("sendWxRefundMsg begin,userId:{},temp:{},status:{}",userId,temp,status);
		
		//1获取openId
		String openId = memberService.getOpenIdByUserId(userId);
		logger.info("sendWxRefundMsg =============== 1、根据userId:{}获取openId:{}",userId,openId);
		if(StringUtils.isBlank(openId)){
			logger.error("sendWxRefundMsg end,openId is null");
			return false;
		}
		
		//2保存消息
		StringBuffer orderNo = new StringBuffer(temp.getKeyword1().getValue());
		orderNo.append("_").append(status);
		if(saveWxpubMsg(orderNo.toString(), GsonUtils.toJson(temp), openId, userId)){
			logger.info("sendWxRefundMsg =============== 2、保存消息成功,orderNo:{},status:{}",temp.getKeyword1().getValue(),status);
		}else{
			logger.error("sendWxRefundMsg end,saveWxpubMsg fail");
			return false;
		}
		
		//3发送消息
		WxpubSendMsg<RefundMsgTemp> msg = new WxpubSendMsg<RefundMsgTemp>();
		msg.setData(temp);
		msg.setTemplate_id(ConfigHandler.getInstance().getRefundMsgTemplateID());
		msg.setTouser(openId);
		msg.setUrl(String.format(ConfigHandler.getInstance().getRefundDetailUrl(), temp.getKeyword1().getValue()));
		String jsonStr = null;
		try{
			jsonStr = weixinService.sendMsg(msg);
		}catch(Exception e){
			logger.error("sendWxRefundMsg ===> weixinService.sendMsg exception,e:",e);
		}
		logger.info("sendWxRefundMsg =============== 3、发送消息,微信返回:{}，orderNo:{},status:{}",jsonStr,temp.getKeyword1().getValue(),status);
		
		//更新消息发送状态
		SendWxpubMsgResp resp = null;
		if(StringUtils.isNotBlank(jsonStr)){
			resp = GsonUtils.fromJson2Object(jsonStr, SendWxpubMsgResp.class);
		}
		
		logger.info("sendWxRefundMsg  =============== 4、更新消息发送状态,orderNo:{},status:{}",temp.getKeyword1().getValue(),status);
		updateSendMsgStatus(resp, orderNo.toString());
		return true;
	
	}

	private boolean saveWxpubMsg(String orderNo, String msgContent, String openId, String userId){
		WxpubMsg req = new WxpubMsg();
		req.setOrderNo(orderNo);
		req.setMsgContent(msgContent);
		req.setOpenId(openId);
		req.setUserId(userId);
		ResultDto<?> rlt = null;
		try{
			rlt = messageClient.saveWxpubMsg(req);
			logger.info("saveWxpubMsg ===> messageClient.saveWxpubMsg rlt:{}",rlt);
		}catch(Exception e){
			logger.error("saveWxpubMsg ===> messageClient.saveWxpubMsg exceprtion,e:",e);
		}
		if(rlt != null && ResultCodeEnum.System.SUCCESS.getCode().equals(rlt.getResult())){
			return true;
		}else{
			return false;
		}
	}
	
	private void updateSendMsgStatus(SendWxpubMsgResp resp,String orderNo){
		WxpubMsg req = new WxpubMsg();
		req.setOrderNo(orderNo);
		if(null == resp){
			req.setResultCode("fail");
			req.setRemark("调用微信发送接口失败");
		}else{
			req.setResultCode(resp.getErrcode());
			req.setResultMsg(resp.getErrmsg());
			req.setResultMsgId(resp.getMsgid());
		}
		messageClient.updateSendMsgRlt(req);
	}
}
