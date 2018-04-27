package com.gl365.wxpub.dto.settlement.resp;

import java.math.BigDecimal;

import com.gl365.wxpub.util.GsonUtils;

public class PayMainDetailRlt<T> extends SettlementBaseRlt<T>{

	private static final long serialVersionUID = -5029857507490307347L;

	private	BigDecimal totalMoney; //总消费金额
	
	private BigDecimal totalBeanAmount; //乐豆支付总额
	
	private BigDecimal totalGiftAmount; //返豆数量总额

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public BigDecimal getTotalBeanAmount() {
		return totalBeanAmount;
	}

	public void setTotalBeanAmount(BigDecimal totalBeanAmount) {
		this.totalBeanAmount = totalBeanAmount;
	}

	public BigDecimal getTotalGiftAmount() {
		return totalGiftAmount;
	}

	public void setTotalGiftAmount(BigDecimal totalGiftAmount) {
		this.totalGiftAmount = totalGiftAmount;
	}

}
