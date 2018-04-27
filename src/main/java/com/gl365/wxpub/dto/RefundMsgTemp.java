package com.gl365.wxpub.dto;

/**
 * 微信公众号退款消息体
 * @author dfs_519
 *2017年12月8日上午11:49:56
 */
public class RefundMsgTemp extends BaseDomain{

	private static final long serialVersionUID = -734221601304686036L;

	public RefundMsgTemp() {
		super();
		this.first = new form();
		this.keyword1 = new form();
		this.keyword2 = new form();
		this.keyword3 = new form();
		this.keyword4 = new form();
		this.remark = new form();
	}

	public class form{
		String value;
		String color = "#173177";
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
	}
	
	/**
	 * 标题
	 */
	private form first;
	
	/**
	 * 订单编号
	 */
	private form keyword1;
	
	/**
	 * 订单金额
	 */
	private form keyword2;
	
	/**
	 * 退回支付乐豆
	 */
	private form keyword3;
	
	/**
	 * 收回赠送乐豆
	 */
	private form keyword4;
	
	/**
	 * 底部
	 */
	private form remark;

	public form getFirst() {
		return first;
	}

	public void setFirst(form first) {
		this.first = first;
	}

	public form getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(form keyword1) {
		this.keyword1 = keyword1;
	}

	public form getKeyword2() {
		return keyword2;
	}

	public void setKeyword2(form keyword2) {
		this.keyword2 = keyword2;
	}

	public form getRemark() {
		return remark;
	}

	public void setRemark(form remark) {
		this.remark = remark;
	}

	public form getKeyword3() {
		return keyword3;
	}

	public void setKeyword3(form keyword3) {
		this.keyword3 = keyword3;
	}

	public form getKeyword4() {
		return keyword4;
	}

	public void setKeyword4(form keyword4) {
		this.keyword4 = keyword4;
	}
	
}
