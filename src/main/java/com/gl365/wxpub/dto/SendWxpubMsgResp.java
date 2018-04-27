package com.gl365.wxpub.dto;

public class SendWxpubMsgResp extends BaseDomain{

	private static final long serialVersionUID = 1180675373567493864L;

	private String errcode;
	
	private String errmsg;
	
	private String msgid;

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	
}
