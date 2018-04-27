package com.gl365.wxpub.dto.h5.resp;

import java.io.Serializable;
import java.math.BigDecimal;

import com.gl365.wxpub.dto.BaseDomain;
import com.gl365.wxpub.enums.GroupPayStatus;
import com.gl365.wxpub.util.GsonUtils;

public class GroupUserInfo implements Serializable{

	private static final long serialVersionUID = -1945178450556254636L;

	private String userId;
	
	private String userName;
	
	private String imgUrl;
	
	private BigDecimal totalAmount;
	
	private GroupPayStatus payStatus;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public GroupPayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(GroupPayStatus payStatus) {
		this.payStatus = payStatus;
	}

	@Override
	public String toString() {
		return GsonUtils.toJson(this);
	}
}
