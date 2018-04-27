package com.gl365.wxpub.dto.merchant.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("商户评论列表请求对象")
public class GetMerchantCommentsCommand {

	/**
	 * 商户编号
	 */
	@ApiModelProperty(value = "商户编号")
	private String merchantNo;

	/**
	 * 当前页
	 */
	@ApiModelProperty(value = "当前页面")
	private Integer curPage;

	/**
	 * 页数据大小
	 */
	@ApiModelProperty(value = "页面数据大小")
	private Integer pageSize;

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
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
