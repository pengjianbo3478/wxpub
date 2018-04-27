package com.gl365.wxpub.exception;

import com.gl365.wxpub.enums.ResultCodeEnum;

public class WxPubException extends RuntimeException{
	/**
	 *
	 */
	private static final long serialVersionUID = -1214099951456499043L;

	private String errorCode;

	private String errorMsg;

	public WxPubException(String errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public WxPubException(ResultCodeEnum.System code){
		super();
		this.errorCode = code.getCode();
		this.errorMsg = code.getMsg();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
}
