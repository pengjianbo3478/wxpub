package com.gl365.wxpub.dto;

public class WxJsApiTicket extends BaseDomain{

	private static final long serialVersionUID = 3704108892816152285L;

	private String ticket;
	
	private int expiresIn;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	
}
