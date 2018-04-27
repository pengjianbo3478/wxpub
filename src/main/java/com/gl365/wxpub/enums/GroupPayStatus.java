package com.gl365.wxpub.enums;

public enum GroupPayStatus {

	INIT("INIT","未支付"),
	PAYING("PAYING","支付中"),
	SUCCESS("SUCCESS","支付成功"),
	FAIL("FAIL","支付失败");
	
	private String status;

	private String msg;

	private GroupPayStatus(String status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
