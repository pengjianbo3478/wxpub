package com.gl365.wxpub.dto.account.rlt;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ActBalance extends ActServiceRlt{

	private static final long serialVersionUID = 8211481495226833416L;

	private String userId;
	
	private String accountId;
	
	private String accountName;
	
	private BigDecimal balance;
	
	private String exchangeRate;
	
	private String agentAccountNo;
	
	private String agentId;
	
	private String status;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;
	
	private String createBy;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifyTime;
	
	private String modifyBy;

	public String getUserId() {
		return userId;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public String getAgentAccountNo() {
		return agentAccountNo;
	}

	public String getAgentId() {
		return agentId;
	}

	public String getStatus() {
		return status;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public LocalDateTime getModifyTime() {
		return modifyTime;
	}

	public String getModifyBy() {
		return modifyBy;
	}
	
}
