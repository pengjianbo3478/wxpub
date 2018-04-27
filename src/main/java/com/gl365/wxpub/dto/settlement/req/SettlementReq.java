package com.gl365.wxpub.dto.settlement.req;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gl365.wxpub.dto.BaseDomain;

public class SettlementReq extends BaseDomain{

	private static final long serialVersionUID = 4738040643076183601L;
	
	private String merchantNo;
	
	private String userId;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fromDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate toDate;

	private int numPerPage; //每页记录数
	
	private int pageNum; //页码

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public SettlementReq(String userId, LocalDate fromDate, LocalDate toDate, int numPerPage, int pageNum) {
		super();
		this.userId = userId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.numPerPage = numPerPage;
		this.pageNum = pageNum;
	}
	
}
