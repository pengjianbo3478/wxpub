package com.gl365.wxpub.dto.message;

import com.gl365.wxpub.dto.BaseDomain;

public class WxpubMsg extends BaseDomain{

	private static final long serialVersionUID = -4300495383119437273L;

	private String orderNo;
	
	private String userId;

	private String openId;
	
	private String msgContent;
	
	private String resultCode;
	
	private String resultMsg;
	
	private String resultMsgId;
	
	private String remark;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getResultMsgId() {
		return resultMsgId;
	}

	public void setResultMsgId(String resultMsgId) {
		this.resultMsgId = resultMsgId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
