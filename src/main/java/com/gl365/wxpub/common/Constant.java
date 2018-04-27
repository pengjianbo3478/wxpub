package com.gl365.wxpub.common;

public class Constant {
	
	/**
	 * 账户系统返回码：成功
	 */
	public static final String actRespSuccess = "000000";
	
	/**
	 * 账户系统查询账单标示，给乐豆 40001
	 */
	public static final String hcAgentID = "40001";
	
	/**
	 * 付费通组织机构代码
	 */
	public static final String fftOrganCode ="10001";

	public static final String wx_openId_scope = "snsapi_base";

	public static final String wx_userInfo_scope = "snsapi_userinfo";
	
	/**
	 * 群主
	 */
	public static final String GROUPOWNER = "GROUPOWNER";

	/**
	 * 群成员
	 */
	public static final String GROUPMEMBER = "GROUPMEMBER";
	
	public static final String RECOMMENDAGENTTYPE = "5";
	
	public static final String GL_WXPUB_USER_TOKEN = "GL_WXPUB_USER_TOKEN";

	public static final String GL_USER_ID = "GL_USER_ID";
	
	public static final String GL_WXPUB_OPENID = "GL_WXPUB_OPENID";
	public static final String GL_WXPUB_BINDFLAG = "GL_WXPUB_BINDFLAG";
	public static final String GL_WXPUB_BEANAMOUNT = "GL_WXPUB_BEANAMOUNT";
	public static final String GL_WXPUB_MOBLIEPHONE = "GL_WXPUB_MOBLIEPHONE";

	/**
	 * 融脉机构编码
	 */
	public static final String RM_ORGAN_CODE = "10003";

	public static final String WXAPI_ACCESSTOKEN_KEY = "WXAPI_ACCESSTOKEN_KEY";
	public static final String WXJSAPI_TICKET_KEY = "WXJSAPI_TICKET_KEY";
	
	public static final String KEFU_MSG_1 = "该员工账户不存在，请尝试重新打赏，若该问题无法解决，请联系人工客服";
	public static final String KEFU_MSG_2 = "您的账户不存在，请尝试重新登录，若该问题无法解决，请联系人工客服";
	public static final String KEFU_MSG_3 = "您的账户存在异常状况，请尝试重新登录，若该问题无法解决，请联系人工客服";
	public static final String KEFU_MSG_4 = "该员工账户存在异常状况，请尝试重新打赏，若该问题无法解决，请联系人工客服";
	public static final String KEFU_MSG_5 = "您的账户存在异常状况，请尝试重新登录，若该问题无法解决，请联系给乐客服";
	
	/**
	 *  refundIng 退款完成通知|refund 退款完成通知|confirm交易确认通知
	 */
	public static final String REFUNDING = "refunding";
	public static final String REFUND = "refund";
	public static final String CONFIRM = "confirm";
	
}
