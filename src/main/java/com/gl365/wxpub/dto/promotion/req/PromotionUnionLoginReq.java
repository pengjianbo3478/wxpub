package com.gl365.wxpub.dto.promotion.req;

import java.math.BigDecimal;

import com.gl365.wxpub.dto.BaseDomain;

/**
 * 活动联合登录请求参数
 * @author dfs_519
 *2018年1月23日上午10:31:27
 */
public class PromotionUnionLoginReq extends BaseDomain{

	private static final long serialVersionUID = -7322333214782181246L;

	private BigDecimal credits; //乐豆余额
	
	private String redirectUrl;
	
	private String uid;
	
	private String urlType; //url类型，1：活动地址，2：积分商城

	public BigDecimal getCredits() {
		return credits;
	}

	public void setCredits(BigDecimal credits) {
		this.credits = credits;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}
	
}
