package com.gl365.wxpub.dto.merchant.req;

public class SaveCommentPersonal4MemberCommand extends SaveCommentPersonalCommand {

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public SaveCommentPersonal4MemberCommand(SaveCommentPersonalCommand command, String userId) {
		super(command);
		this.userId = userId;
	}

	
}
