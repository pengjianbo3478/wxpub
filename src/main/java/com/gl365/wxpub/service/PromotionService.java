package com.gl365.wxpub.service;

import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.member.resp.ActiveMerchantResp;
import com.gl365.wxpub.dto.member.resp.UserBindInfo;
import com.gl365.wxpub.dto.merchant.req.MerchantActivityReq;
import com.gl365.wxpub.dto.promotion.req.DrawLotteryReq;

public interface PromotionService {

	/**
	 * 获取活动的奖品列表
	 * @param activityId
	 * @return
	 */
	ResultDto<?> getPrizeList(String activityId);
	
	/**
	 * 查询最新的10条中奖纪录
	 * @param activityId
	 * @return
	 */
	ResultDto<?> getFlowsList(String activityId);
	
	/**
	 * 抽奖
	 * @param req
	 * @return
	 */
	ResultDto<?> drawLottery(DrawLotteryReq req);
	
	/**
	 * 判断用户在商家买单后是否可参加活动
	 * @param req
	 * @param bindFlag //0:未绑定，1：已绑定
	 * @param req 1:买单后参与活动；2：分享注册后参与活动
	 * @return
	 */
	ActiveMerchantResp isActivity(MerchantActivityReq req,int bindFlag,String source);
	
	/**
	 * 活动获取免登url
	 * @param ubi
	 * @param redirectUrl
	 * @param urlType
	 * @return
	 */
	String unionLogin(UserBindInfo ubi, String redirectUrl, String urlType);
}
