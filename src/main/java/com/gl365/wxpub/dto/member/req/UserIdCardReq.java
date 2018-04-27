package com.gl365.wxpub.dto.member.req;

import io.swagger.annotations.ApiModelProperty;

public class UserIdCardReq {

	@ApiModelProperty("身份证号码")
	private String idCard;
	
	@ApiModelProperty("真实姓名")
	private String name;

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
