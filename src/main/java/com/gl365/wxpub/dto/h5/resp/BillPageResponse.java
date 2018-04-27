package com.gl365.wxpub.dto.h5.resp;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.util.GsonUtils;


public class BillPageResponse<T> implements Serializable{

	private static final long serialVersionUID = -1869583637830266183L;

	/**
	 * SessionId : 用户身份的会话控制ID
	 */
	private String sessionId;

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
	private BillPageDto<T> data;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
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

	public BillPageDto<T> getData() {
		return data;
	}

	public void setData(BillPageDto<T> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return GsonUtils.toJson(this);
	}
	
	public BillPageResponse(){
		
	}
	
	public static BillPageResponse<?> settlementRltConvert(String settlementRltCode,String settlementRltMsg){
		BillPageResponse<?> resp = new BillPageResponse<>();
		if(StringUtils.isEmpty(settlementRltCode)){
			resp.setResult(ResultCodeEnum.System.SYSTEM_ERROR.getCode());
			resp.setDescription(ResultCodeEnum.System.SYSTEM_ERROR.getMsg());
		}else if("000001".equals(settlementRltCode) || "999999".equals(settlementRltCode)){
			resp.setResult(ResultCodeEnum.Settlement.GET_BILL_FAIL.getCode());
			resp.setDescription(ResultCodeEnum.Settlement.GET_BILL_FAIL.getMsg());
		}
		return resp;
	}
	
	public BillPageResponse(ResultCodeEnum.System source) {
		this.result = source.getCode();
		this.description = source.getMsg();
	}

	public BillPageResponse(ResultCodeEnum.System source, BillPageDto<T> data) {
		this.result = source.getCode();
		this.description = source.getMsg();
		this.data = data;
	}
	
	public static BillPageResponse<?> settlementError(ResultCodeEnum.Settlement st){
		BillPageResponse<?> resp = new BillPageResponse<>();
		resp.setResult(st.getCode());
		resp.setDescription(st.getMsg());
		return resp;
	}
		
}
