package com.gl365.wxpub.dto;

/**
 * 用户参与活动抽中奖品实体
 * @author dfs_519
 *2017年11月5日上午10:34:36
 */
public class DrowPrizeDto extends BaseDomain{

	private static final long serialVersionUID = 8001935320981332281L;

	private String mobileNo;
	
	private String flowId; //中奖编号
	
	private String prizeId; //奖品id
	
	private String prizeName; //奖品名称
	
	private String prizeTypeDesc; //奖品类型
	
	private String winText; //奖品描述

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(String prizeId) {
		this.prizeId = prizeId;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public String getPrizeTypeDesc() {
		return prizeTypeDesc;
	}

	public void setPrizeTypeDesc(String prizeTypeDesc) {
		this.prizeTypeDesc = prizeTypeDesc;
	}

	public String getWinText() {
		return winText;
	}

	public void setWinText(String winText) {
		this.winText = winText;
	}
	
}
