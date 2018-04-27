package com.gl365.wxpub.enums;

/**
 * api接口请求结果枚举 规则： 长度6位 |0000|00 前四位表示模块|后两位递增
 * 
 * 列如 系统类异常：0000** 用户类异常：1000**
 * 
 * @author dfs_519 2017年4月27日下午2:02:21
 */
public class ResultCodeEnum {

	public enum System {
		/**
		 * 系统保留100以下的错误码,提示可以调整,code不能变
		 */
		SUCCESS("000000", "成功"),
		
		GROUP_OWNER_CANNOT_QRINGROUP("000001","群主不能扫码进群"),

		PARAM_NULL("000002", "参数为空"),

		PARAM_ERROR("000003", "参数非法"),
		
		NO_MERCHANT_INFO("000004","该收款二维码暂未关联商户"),
		
		REQUEST_IS_NULL("000005", "错误请求"),
		
		ILLEGAL_REQUEST("000006","会话失效"),
		
		SYSTEM_DATA_EXECEPTION("000008", "系统数据异常"),

		SYSTEM_ERROR("000099", "亲，小乐开了会小差，待会再来吧！:)"),

		INVOKE_ERROR("000007","服务器通讯异常");
		private String code;

		private String msg;

		private System(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	public enum Comment {
		GET_ORDER_COMMENT_STATUS_FAIL("200001", "获取订单评论状态失败"),
		;
		private String code;

		private String msg;

		private Comment(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	public enum Payment{
		PERPAY_FAIL("300001","支付失败,请稍后重试"),
		GET_MERCHANT_INFO_FAIL("300002","获取商户信息失败"),
		GET_ORDER_INFO_FAIL("300003","获取订单信息失败"),
		GROUP_PAY_GET_ORDER_NO_FAIL("300004","获取订单信息失败"),
		GET_HAPPY_COIN_COUNT_FAIL("300010","查询乐豆余额失败"),
		MERCHANT_NOT_OPEN_WXPAY("400037","该商家在给乐平台尚未开通微信支付"),
		BEAN_TIP_PAY_FAIL("400038","乐豆打赏失败"),
		INSUFFICIENT_BALANCE("400039","乐豆余额不足，请检查您的支付账户余额"),
		CANNOT_REWARD_TO_SELF("400040","暂不支持打赏给自己，打赏给店里其他小伙伴吧！"),
		KEFU_ERROR("KF0001",""),
		PAY_COMFIRM_FAIL("400041","买单失败，您支付的金额将在1-3工作日返回付款账户"),
		PAY_STATUS_UNKNOW("400042","订单状态查询中，支付结果以商家账单通知为准"),
		;
		
		private String code;

		private String msg;

		private Payment(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	public enum Merchant {
		GET_EMPLOYEES_FAIL("400001", "获取商家员工列表失败"),
		;
		private String code;

		private String msg;

		private Merchant(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	public enum Lottery {

		NO_PERMIS("500001","亲，您已经是给乐老司机，分享抽奖的机会让给新来的小伙伴吧！"),
		HAS_DRAW_PRIZE("500002","亲，您已抽过奖啦，下次再来吧！"),
		DRAW_PRIZE_FAIL("500003","亲，抽奖没有成功，请再来一次吧！"),
		USER_HAS_NOT_BIND("500004","用户微信号未绑定"),
		;
		private String code;

		private String msg;

		private Lottery(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	public enum Settlement {

		GET_BILL_FAIL("600001","获取账单失败");
		private String code;

		private String msg;

		private Settlement(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	public enum GroupPay {

		CREATE_MAINORDER_FAIL("700001","生成群订单失败"),
		BUILD_GROUP_FAIL("700002","乐抢单建群失败"),
		GROUP_NOT_EXIST("700003","群不存在"),
		GROUP_IS_FULL_INGROUP_FAIL("700004","群人数已满，进群失败"),
		GROUP_PAY_BEGIN_INGROUP_FAIL("700005","乐抢单已开始支付，进群失败"),
		GROUP_HAS_DISMISS("700006","群已解散"),
		GROUP_PAY_BEGIN_DISGROUP_FAIL("700007","乐抢单已开始支付，撤销群失败"),
		GROUP_PAY_BEGIN_OUTGROUP_FAIL("700008","乐抢单已开始支付，退群失败"),
		GROUP_MEMBER_PAYING("700009","参与者正在进行支付，请您稍后再试"),
		GROUP_OWNER_PAYING("700010","乐抢单发起人正在买单，其他人暂时无法支付"),
		GROUP_OWNER_PAYED("700011","乐抢单已完成"),
		GROUP_MEMBER_NOT_EXIST("700012","群不存在"),
		GROUP_PAY_RECEIVE_AGAIN("700013","请不要重复领取哦"),
		GROUP_INIT_CANNOT_CANCEL("700014","建群未完成，无法取消乐抢单"),
		GROUP_OWNER_HAS_PAY_CANNOT_CANCEL("700015","乐抢单发起者已完成支付，无法取消乐抢单，请联系商家发起退款"),
		CANCEL_GROUP_FAIL("700016","乐抢单取消失败"),
		GROUP_HAS_CANCEL("700017","乐抢单已撤销，若您已完成支付，则支付的金额会在1-3个工作日内返还到您的支付账户，关注给乐服务号，随时关注退款动态"),
		GROUP_PAY_IS_UPGRADING("799999","乐抢单系统升级中、暂停使用，小乐将更加努力给你提供更好的产品体验"),
		;
		private String code;

		private String msg;

		private GroupPay(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	public enum User {
		NO_MEMBER_INFO_ERROR("100017", "没有用户信息"),
		IDCARD_LIMIT_ERROR("100018", "该身份证已验证,不可重复认证,若身份证被盗用请联系客服"),
		;
		private String code;

		private String msg;

		private User(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
}
