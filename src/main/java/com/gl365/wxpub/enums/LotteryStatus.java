package com.gl365.wxpub.enums;

public enum LotteryStatus {

	NO_PERMIS("没有抽奖权限"),
	
	YI_CHOU_JIANG("已抽奖"),
	
	WEI_CHOU_JIANG("未抽奖");
	
	private String desc;

	private LotteryStatus(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
