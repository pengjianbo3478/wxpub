package com.gl365.wxpub.dto;

/**
 * 存放用户参与抽奖活动信息的对象，保存在redis
 * @author dfs_519
 *2017年11月5日上午10:31:21
 */
public class LotteryDto extends BaseDomain{

	private static final long serialVersionUID = -7113374417129657930L;

	private String openId;
	
	private String mobileNo;
	
	private String activityId;
	
	private String userId;
	
	private boolean isDraw; //是否抽过奖
	
	private String prizeId; //奖品id
	
	private String prizeName; //奖品名称
	
	private String prizeTypeDesc; //奖品类型
	
	private String winText; //奖品描述
	
	private String merchantNo;
	
	private String flowId; //中奖编号
	
	private String beginTime; //活动开始时间
	
	private String endTime; //活动结束时间

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isDraw() {
		return isDraw;
	}

	public void setDraw(boolean isDraw) {
		this.isDraw = isDraw;
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

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
