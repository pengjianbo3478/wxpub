package com.gl365.wxpub.dto.promotion.resp;

import com.gl365.wxpub.dto.BaseDomain;

public class DrawLotteryResp extends BaseDomain{

	private static final long serialVersionUID = 5154646477281493204L;

	private String flowId; //中奖编号 
	
	private String prizeId; //抽中奖品ID

	private String prizeName; //奖品名

	private String prizeTypeDesc; //奖品类型

	private String winText; //中奖语

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
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
