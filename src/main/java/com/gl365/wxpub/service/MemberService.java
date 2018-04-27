package com.gl365.wxpub.service;

import com.gl365.wxpub.dto.member.resp.UserBindInfo;

public interface MemberService {

	/**
	 * 根据openId获取会员信息
	 * @param openId
	 * @param nikeName
	 * @param headImgUrl
	 * @return
	 */
	UserBindInfo getuserBindInfo(String openId,String nickName,String headImgUrl,String recommendAgentId,String recommendAgentType,String recommendBy);
	
	String getOpenIdByUserId(String userId);
}
