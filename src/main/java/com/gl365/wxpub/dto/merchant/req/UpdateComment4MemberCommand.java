package com.gl365.wxpub.dto.merchant.req;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * < 商家评论DTO >
 * 
 * @author hui.li 2017年4月20日 - 下午4:47:32
 * @Since 1.0
 */
public class UpdateComment4MemberCommand {

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 用户真实姓名
	 */
	private String realName;
	/**
	 * 商户编号
	 */
	private String merchantNo;

	/**
	 * 评分
	 */
	private Integer grade;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 支付订单编号
	 */
	private String paymentNo;

	/**
	 * 评论时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createDateTime;
	/**
	 * 商家评论标签
	 */
	private Integer[] labels;

	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer[] getLabels() {
		return labels;
	}

	public void setLabels(Integer[] labels) {
		this.labels = labels;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

}