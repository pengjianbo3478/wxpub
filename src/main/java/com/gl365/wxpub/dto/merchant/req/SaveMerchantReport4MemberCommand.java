package com.gl365.wxpub.dto.merchant.req;

import java.io.Serializable;

/**
 * < 举报商家指令 >
 * 
 * @author hui.li 2017年4月27日 - 下午4:00:15
 * @Since 1.0
 */
public class SaveMerchantReport4MemberCommand implements Serializable {

	private static final long serialVersionUID = 4390472273983765371L;

	private String merchantNo;

	private String content;

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
