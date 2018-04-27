package com.gl365.wxpub.dto.account.rlt;

import java.io.Serializable;

import com.gl365.wxpub.common.JsonUtils;

public class ActServiceRlt implements Serializable{

	private static final long serialVersionUID = -1347300839300748700L;

	private String resultCode;
	
	private String resultDesc;
	
	public String getResultCode() {
		return resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}
	
}
