package com.gl365.wxpub.dto.merchant.req;

public class GetUserFavorites4MemberCommand {

	/**
	 * 用户Id
	 */
	private String userId;
	/**
	 * 当前页
	 */
	private Integer curPage;

	/**
	 * 页数据大小
	 */
	private Integer pageSize;

	public GetUserFavorites4MemberCommand() {
		super();
	}

	public GetUserFavorites4MemberCommand(String userId, Integer curPage, Integer pageSize) {
		super();
		this.userId = userId;
		this.curPage = curPage;
		this.pageSize = pageSize;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
