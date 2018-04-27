package com.gl365.wxpub.dto.merchant.req;

public class GetUserBillsCommand {

	/**
	 * 商户编号
	 */
	private String merchantNo;
	
	/**
	 * 群买单单号
	 */
	private String orderSn;
	
	/**
	 * 群买单群号
	 */
	private String groupId;

	/**
	 * 当前页
	 */
	private Integer curPage;

	/**
	 * 页数据大小
	 */
	private Integer pageSize;

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
