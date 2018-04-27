package com.gl365.wxpub.service;

import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.h5.req.PrePayReq;
import com.gl365.wxpub.dto.h5.req.WxGroupPayGetOrderNoReq;
import com.gl365.wxpub.dto.h5.req.WxGroupPayReq;
import com.gl365.wxpub.dto.order.req.GroupPayRevokeReq;

public interface OrderService {

	/**
	 * 预下单
	 * @param req
	 * @return
	 */
	ResultDto<?> preOrder(PrePayReq req);
	
	/**
	 * 买单成功后查询订单信息
	 * @param orderNo
	 * @param flag 0:普通买单，1：群主买单，2：参与者买单
	 * @return
	 */
	ResultDto<?> payInfo(String orderNo,String flag);
	
	/**
	 * 群买单获取订单号
	 * @param req
	 * @return
	 */
	ResultDto<?> groupPayGetOrderNo(WxGroupPayGetOrderNoReq req);
	
	/**
	 * 群买单发起支付获取tokenId
	 * @param req
	 * @return
	 */
	ResultDto<?> groupPay(WxGroupPayReq req);
	
	/**
	 * 查询群买单主单支付状态
	 * @param orderNo
	 * @return
	 */
	ResultDto<?> queryMainOrderStatue(String orderNo);
	
	/**
	 * 群主发起撤单
	 * @param req
	 * @return
	 */
	boolean groupPayCancel(GroupPayRevokeReq req);
	
	/**
	 * 支付确认
	 * @param orderNo
	 * @return
	 */
	ResultDto<?> orderPayComfirm(String orderNo);
	
	/**
	 * 查询订单详情
	 * @param orderNo
	 * @return
	 */
	ResultDto<?> queryOrderInfoByOrderNo(String orderNo);
}
