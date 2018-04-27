package com.gl365.wxpub.enums;

public enum GroupStatus {

	INIT("INIT", "乐抢单建群中"),
	DISMISS("DISMISS","乐抢单撤销"),
	BUILD_COMPLETE("BUILD_COMPLETE","完成建群，开始分配支付金额，禁止进群、退群"),
	CANCEL("CANCEL","乐抢单已撤销，若您已完成支付，则支付的金额会在1-3个工作日内返还到您的支付账户"),
	MAINGROUPER_PAYING("MAINGROUPER_PAYING","乐抢单发起者支付中，其他人未支付的禁止发起支付"),
	MAINGROUPER_PAYED("MAINGROUPER_PAYED","乐抢单支付完成");

	private String status;

	private String msg;

	private GroupStatus(String status, String msg) {
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
