package com.gl365.wxpub.dto.member.req;

import java.io.Serializable;
import java.math.BigDecimal;

public class GroupPayRedisDto implements Serializable {
	
	private static final long serialVersionUID = 6878252931658601631L;

	//用户id
	private String userId;
	
	//用户分配金额
	private BigDecimal money;
	
	//是否点击转盘领取
	private Boolean receive;
	
	//支付状态
	private String payStatus;
	
	//用户名
	private String userName;
	
	//用户头像
	private String imgUrl;
	
	//是否是发起者
	private Boolean isCreate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Boolean getReceive() {
		return receive;
	}

	public void setReceive(Boolean receive) {
		this.receive = receive;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Boolean getIsCreate() {
		return isCreate;
	}

	public void setIsCreate(Boolean isCreate) {
		this.isCreate = isCreate;
	}
}
