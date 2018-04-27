package com.gl365.wxpub.dto.settlement.resp;

import java.util.List;

import com.gl365.wxpub.dto.BaseDomain;

public class SettlementBaseRlt<T> extends BaseDomain{

	private static final long serialVersionUID = 2436716293947543498L;
	
	private String resultCode;
	
	private String resultDesc;
	
	private int totalNum;
	
	private List<T> data;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
}
