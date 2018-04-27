package com.gl365.wxpub.service;

public interface RedisService {

	public abstract void del(String key);
	
	public abstract int IncGroupSize(String key);
	
	public abstract int DescGroupSize(String key);
	
	public abstract int getDescGroupSize(String key);
	
	public abstract int getIncGroupSize(String key);
	
	public abstract Object getKeys(String pattern);
	
	public abstract void set(String key, String value, Long liveTime);
	
	public abstract String get(String key);
}
