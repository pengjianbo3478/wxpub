package com.gl365.wxpub.dto;

import java.io.Serializable;

import com.gl365.wxpub.common.JsonUtils;
import com.gl365.wxpub.enums.ResultCodeEnum;

/**
 * < 基础响应 >
 * 
 * 包含常规请求响应的参数
 * 
 * @author hui.li 2017年4月12日 - 下午1:22:21
 * @Since 1.0
 */
public class PageResultDto<T> implements Serializable {

	private static final long serialVersionUID = 6344892873306191434L;

	/**
	 * result : 响应码
	 */
	private String result;

	/**
	 * description ： 响应描述
	 */
	private String description;

	/**
	 * pageData : 页码对象
	 */
	private PageDto<T> data;

	public PageResultDto() {

	}

	public PageResultDto(String result, String description, PageDto<T> data) {
		this.result = result;
		this.description = description;
		this.data = data;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PageDto<T> getData() {
		return data;
	}

	public void setData(PageDto<T> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}
	
	public static PageResultDto<?> systemResult(ResultCodeEnum.System source){
		PageResultDto<?> rlt = new PageResultDto<>();
		rlt.setResult(source.getCode());
		rlt.setDescription(source.getMsg());
		return rlt;
	}
	
	public PageResultDto(PageDto<T> data) {
		this.result = ResultCodeEnum.System.SUCCESS.getCode();
		this.description = ResultCodeEnum.System.SUCCESS.getMsg();
		this.data = data;
	}
	
	public static PageResultDto<?> PaymentErrResult(ResultCodeEnum.Payment source){
		PageResultDto<?> rlt = new PageResultDto<>();
		rlt.setResult(source.getCode());
		rlt.setDescription(source.getMsg());
		return rlt;
	}

}
