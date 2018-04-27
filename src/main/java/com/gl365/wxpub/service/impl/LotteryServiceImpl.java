package com.gl365.wxpub.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl365.wxpub.dto.LotteryDto;
import com.gl365.wxpub.handler.ConfigHandler;
import com.gl365.wxpub.service.LotteryService;
import com.gl365.wxpub.service.RedisService;
import com.gl365.wxpub.util.GsonUtils;

@Service
public class LotteryServiceImpl implements LotteryService{
	
	@Autowired
	private RedisService redisService;
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public void savaLottery2Redis(LotteryDto dto,String from) {
		String key = generatorLotteryKey(dto.getOpenId(), dto.getActivityId(),from);
		long expriedTime = 3456000;//40天
		if(StringUtils.isNotBlank(dto.getEndTime())){
			expriedTime = calculExpriedTime(LocalDateTime.parse(dto.getEndTime(), formatter));
		}
		redisService.set(key, GsonUtils.toJson(dto), expriedTime);
		return;
	}

	@Override
	public LotteryDto getLotteryFromRedis(String openId, String activityId,String from) {
		String key = generatorLotteryKey(openId, activityId,from);
		String jsonString = redisService.get(key);
		return GsonUtils.fromJson2Object(jsonString, LotteryDto.class);
	}
	
	private String generatorLotteryKey(String openId,String activityId,String from){
		StringBuffer sb = new StringBuffer(openId);
		sb.append("_").append(activityId);
		sb.append("_").append(from);
		return sb.toString();
	}
	
	/**
	 * 计算时间差（秒）
	 * @param endTime
	 * @return
	 */
	private long calculExpriedTime(LocalDateTime endTime){
		LocalDateTime nowTime = LocalDateTime.now();
		Duration dr = Duration.between(nowTime, endTime);
		return dr.toMillis()/1000;
	}

}
