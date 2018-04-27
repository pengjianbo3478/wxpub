package com.gl365.wxpub.dto.merchant.req;

import java.util.ArrayList;
import java.util.List;

public class GetMerchantDetail4MerchantCommand {

	/**
	 * 商家编号 , 支持批量
	 */
	private List<String> merchantNo;

	public GetMerchantDetail4MerchantCommand() {
		super();
	}

	public GetMerchantDetail4MerchantCommand(String merchantNo) {
		super();
		this.merchantNo = new ArrayList<>();
		this.merchantNo.add(merchantNo);
	}

	public List<String> getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(List<String> merchantNo) {
		this.merchantNo = merchantNo;
	}

}
