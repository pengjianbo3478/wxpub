package com.gl365.wxpub.service;

import com.gl365.wxpub.dto.PayMsgTemp;
import com.gl365.wxpub.dto.RefundMsgTemp;

public interface WxMsgService {

	/**
	 * 发送微信支付消息
	 * @param userId
	 * @param temp
	 * @return
	 */
	boolean sendWxPayMsg(String userId,PayMsgTemp temp);
	
	/**
	 * 发送微信退款消息
	 * @param userId
	 * @param temp
	 * @return
	 */
	boolean sendWxRefundMsg(String userId,RefundMsgTemp temp,String status);
}
