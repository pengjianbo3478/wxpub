package com.gl365.wxpub.dto.h5.resp;

import com.gl365.wxpub.dto.BaseDomain;
import com.gl365.wxpub.util.MaskUtils;

public class FlowsResp extends BaseDomain{

	private static final long serialVersionUID = 496852996046010010L;

	private String mobileNo;
	
	private String prizeTypeDesc;
	
	private String prizeName; //奖品名

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = MaskUtils.moblieNoMask(mobileNo);
	}

	public String getPrizeTypeDesc() {
		return prizeTypeDesc;
	}

	public void setPrizeTypeDesc(String prizeTypeDesc) {
		this.prizeTypeDesc = prizeTypeDesc;
	}
	
	
}
