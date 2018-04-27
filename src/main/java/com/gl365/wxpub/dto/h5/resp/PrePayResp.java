package com.gl365.wxpub.dto.h5.resp;

import com.gl365.wxpub.dto.BaseDomain;
import com.google.gson.JsonObject;

public class PrePayResp extends BaseDomain{

	private static final long serialVersionUID = 5263773612136680994L;

	private String payUrl;
	
	private String payInfo;

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

	public String getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}

}
