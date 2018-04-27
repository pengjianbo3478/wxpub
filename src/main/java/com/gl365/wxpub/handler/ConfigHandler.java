package com.gl365.wxpub.handler;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


/**
 * 
 * @author dfs_518
 *
 * 2017年9月12日上午11:29:32
 */
@Component
@Lazy(false)
public class ConfigHandler implements InitializingBean, DisposableBean {


	@Autowired
	private ConfigCenter config;

	private static ConfigHandler picHandler;


	public ConfigCenter getConfig() {
		return config;
	}

	@Override
	public void destroy() throws Exception {
		picHandler = null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		picHandler = this;
	}
	
	public static ConfigCenter getInstance() {
		return picHandler.getConfig();
	}
}
