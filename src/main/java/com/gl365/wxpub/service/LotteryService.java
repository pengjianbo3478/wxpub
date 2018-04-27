package com.gl365.wxpub.service;

import com.gl365.wxpub.dto.LotteryDto;

public interface LotteryService {

	/**
	 * 保存抽奖对象到redis
	 * @param dto
	 */
	void savaLottery2Redis(LotteryDto dto,String from);
	
	/**
	 * 从redis获取抽奖对象
	 * @param openId
	 * @param activityId
	 * @return
	 */
	LotteryDto getLotteryFromRedis(String openId,String activityId,String from);
}
