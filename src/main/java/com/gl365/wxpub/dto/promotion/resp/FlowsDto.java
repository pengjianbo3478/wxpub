package com.gl365.wxpub.dto.promotion.resp;

import com.gl365.wxpub.dto.BaseDomain;

/**
 * 活动中奖信息
 * @author dfs_519
 *2017年11月5日上午11:33:46
 */
public class FlowsDto extends BaseDomain{

	private static final long serialVersionUID = -410652295897743244L;

	private String activityId; //活动ID

	private String activityName; //活动名

	private String createTime; //中奖时间

	private String flag; //纪录标识

	private String flowId;

	private String merchantId; //商户ID

	private String phone; //手机号

	private String prizeId; //奖品ID

	private String prizeName; //奖品名

	private String prizeTypeDesc; //奖品类型名

	private String receiveTime; //领取时间

	private int status;

	private String userId; //用户ID

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
