package com.gl365.wxpub.dto.promotion.resp;

import com.gl365.wxpub.dto.BaseDomain;

public class PromotionUnionLoginResp extends BaseDomain{

	private static final long serialVersionUID = 6590726355778849158L;

	private String redirectUrl;

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	
}
