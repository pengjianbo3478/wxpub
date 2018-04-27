package com.gl365.wxpub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.gl365.wxpub.service.RedisService;
import com.gl365.wxpub.util.SerializeUtil;

@Service
public class RedisServiceImpl implements RedisService{
	
	@Autowired
	private StringRedisTemplate redis;
	
	private final static long LiveTime = 3600L; //1小时
	
	private static String GROUP_INC_SIZE_ = "GROUP_INC_SIZE_";

	private static String GROUP_DESC_SIZE_ = "GROUP_DESC_SIZE_";

	@Override
	public void set(String key, String value, Long liveTime) {
		redis.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(key.getBytes(), value.getBytes());
				if(null != liveTime && liveTime > 0){
					connection.expire(key.getBytes(), liveTime);
				}else{
					connection.expire(key.getBytes(), LiveTime);
				}
				return null;
			}
		});
	}

	@Override
	public String get(String key) {
		return redis.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				try{
					byte[] data = connection.get(key.getBytes());
					if(data != null){
						return new String(data);
					}
				}catch(Exception e){
				}
				return null;
			}
			
		});
	}

	@Override
	public void del(String key) {
		redis.execute(new RedisCallback<String>() {

			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				try{
					connection.del(key.getBytes());
				}catch(Exception e){
					
				}
				return null;
			}
		});
	}

	@Override
	public int IncGroupSize(String key) {
		StringBuffer sb = new StringBuffer(GROUP_INC_SIZE_);
		return redis.boundValueOps(sb.append(key).toString()).increment(1).intValue();
	}

	@Override
	public int DescGroupSize(String key) {
		StringBuffer sb = new StringBuffer(GROUP_DESC_SIZE_);
		return redis.boundValueOps(sb.append(key).toString()).increment(1).intValue();
	}

	@Override
	public int getDescGroupSize(String key) {
		StringBuffer sb = new StringBuffer(GROUP_DESC_SIZE_);
		String rlt = redis.boundValueOps(sb.append(key).toString()).get();
		return rlt == null ? 0 : Integer.parseInt(rlt);
	}

	@Override
	public int getIncGroupSize(String key) {
		StringBuffer sb = new StringBuffer(GROUP_INC_SIZE_);
		String rlt = redis.boundValueOps(sb.append(key).toString()).get();
		return rlt == null ? 0 : Integer.parseInt(rlt);
	}

	@Override
	public Object getKeys(String pattern) {
		return redis.keys(pattern);
	}
}
