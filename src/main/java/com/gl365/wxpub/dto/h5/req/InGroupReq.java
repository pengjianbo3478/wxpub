package com.gl365.wxpub.dto.h5.req;

import com.gl365.wxpub.dto.BaseDomain;

import io.swagger.annotations.ApiModelProperty;

public class InGroupReq extends BaseDomain{

	private static final long serialVersionUID = -8278507804205941465L;

	@ApiModelProperty(value = "申请进群人userId", required = true)
	private String userId;
	
	@ApiModelProperty(value = "进群人姓名(掩码)", required = true)
	private String userName;
	
	@ApiModelProperty(value = "进群人头像url", required = true)
	private String imgUrl;
	
	@ApiModelProperty(value = "群号", required = true)
	private String groupId;

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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
