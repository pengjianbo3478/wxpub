package com.gl365.wxpub.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * < 页码DTO >
 * 
 * @author hui.li 2017年4月20日 - 下午7:36:25
 * @Since 1.0
 */
public class PageDto<T> implements Serializable {

	private static final long serialVersionUID = -7498073051970322499L;

	public PageDto() {

	}

	/**
	 * 
	 * @param totalCount
	 *            总数
	 * @param curPage
	 *            页码
	 * @param pageSize
	 *            页Size
	 * @param list
	 *            数据
	 */
	public PageDto(Integer totalCount, Integer curPage, Integer pageSize, List<T> list) {
		this.totalCount = totalCount; // 总条数
		this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1; // 总页数
		this.curPage = curPage; // 页码
		this.pageSize = pageSize; // 页数
		this.list = list;
	}
	
	public PageDto(Integer totalCount, Integer curPage, Integer pageSize, Map<String, Object> dataMap, List<T> list) {
		this.totalCount = totalCount; // 总条数
		this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1; // 总页数
		this.curPage = curPage; // 页码
		this.pageSize = pageSize; // 页数
		this.dataMap = dataMap;
		this.list = list;
	}

	private Integer totalCount; // 总条数

	private Integer totalPage; // 总页数

	private Integer curPage; // 当前页

	private Integer pageSize; // 页Size

	/**
	 * 自定义分页头部
	 */
	private Map<String, Object> dataMap;
	
	/**
	 * T : 数据集
	 */
	private List<T> list;
	
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
