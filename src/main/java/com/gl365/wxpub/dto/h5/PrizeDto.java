package com.gl365.wxpub.dto.h5;

import com.gl365.wxpub.dto.BaseDomain;

public class PrizeDto extends BaseDomain{

	private static final long serialVersionUID = 523017642173973346L;

	private String prizeId; // 奖品ID
	
	private String prizeImg; // 奖品图片名称
	
	private String prizeName; // 奖品名

	public String getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(String prizeId) {
		this.prizeId = prizeId;
	}

	public String getPrizeImg() {
		return prizeImg;
	}

	public void setPrizeImg(String prizeImg) {
		this.prizeImg = prizeImg;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	
}
