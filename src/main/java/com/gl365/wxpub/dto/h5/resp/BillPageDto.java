package com.gl365.wxpub.dto.h5.resp;

import java.math.BigDecimal;
import java.util.List;

import com.gl365.wxpub.dto.PageDto;

/**
 * 账单页码
 * @author dfs_519
 * 2017年4月22日下午4:28:41
 * @param <T>
 */
public class BillPageDto<T> extends PageDto<T>{


	public BillPageDto(Integer totalCount, Integer curPage, Integer pageSize, List<T> list,BigDecimal totalMoney,BigDecimal totalPayHappyCoin,BigDecimal totalReturnHappyCoin) {
		super(totalCount, curPage, pageSize, list);
		this.totalMoney = totalMoney;
		this.totalPayHappyCoin = totalPayHappyCoin;
		this.totalReturnHappyCoin = totalReturnHappyCoin;
	}

	private BigDecimal totalMoney; //总金额
	
	private BigDecimal totalPayHappyCoin; //乐豆支付总额
	
	private BigDecimal totalReturnHappyCoin; //乐豆支付总额

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public BigDecimal getTotalPayHappyCoin() {
		return totalPayHappyCoin;
	}

	public void setTotalPayHappyCoin(BigDecimal totalPayHappyCoin) {
		this.totalPayHappyCoin = totalPayHappyCoin;
	}

	public BigDecimal getTotalReturnHappyCoin() {
		return totalReturnHappyCoin;
	}

	public void setTotalReturnHappyCoin(BigDecimal totalReturnHappyCoin) {
		this.totalReturnHappyCoin = totalReturnHappyCoin;
	}
	
	
}
