package com.gl365.wxpub.dto;

/**
 * 微信公众号消息对象
 * @author dfs_519
 *2017年12月8日上午11:06:27
 * @param <T>
 */
public class WxpubSendMsg<T> extends BaseDomain{

	private static final long serialVersionUID = 2996855441530950751L;

	/**
	 * 接收人的openId
	 */
	private String touser;
	
	/**
	 * 消息模板ID
	 */
	private String template_id;
	
	/**
	 * 点击消息跳转url
	 */
	private String url;
	
	/**
	 * 消息体
	 */
	private T data;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
}
