package com.gl365.wxpub.dto.merchant.req;

/**
 * @author DELL
 *
 */
public class GetOperatorListCommand {

	private String merchantNo;

	// 角色ID 默认为操作员
	private Integer[] roldId;

	public GetOperatorListCommand(String merchantNo, Integer... roldId) {
		super();
		this.merchantNo = merchantNo;
		this.roldId = roldId;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public Integer[] getRoldId() {
		return roldId;
	}

	public void setRoldId(Integer[] roldId) {
		this.roldId = roldId;
	}

}
