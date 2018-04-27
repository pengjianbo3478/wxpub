package com.gl365.wxpub.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.member.req.GetPayOrganIdReq;
import com.gl365.wxpub.dto.member.req.GetUserByOpenIdReq;
import com.gl365.wxpub.dto.member.resp.UserBindInfo;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.service.MemberService;
import com.gl365.wxpub.service.FeignCilent.MemberClient;
import com.gl365.wxpub.util.SLEmojiFilter;

@Service
public class MemberServiceImpl implements MemberService{
	
	private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

	@Autowired
	private MemberClient memberClient;
	
	@Override
	public UserBindInfo getuserBindInfo(String openId, String nickName, String headImgUrl,String recommendAgentId,String recommendAgentType,String recommendBy) {
		GetUserByOpenIdReq req = new GetUserByOpenIdReq();
		req.setNickName(SLEmojiFilter.filterEmoji(nickName));
		req.setPayOrganId(openId);
		req.setPhoto(headImgUrl);
		req.setRecommendAgentId(recommendAgentId);
		req.setRecommendAgentType(recommendAgentType);
		req.setRecommendBy(recommendBy);
		ResultDto<UserBindInfo> rlt = null;
		try{
			rlt = memberClient.createUserByPayOrganId(req);
		}catch(Exception e){
			logger.error("根据openId获取会员信息  memberClient.createUserByPayOrganId,exception:",e);
			return null;
		}
		logger.info("根据openId:{}获得会员信息:{}",openId,rlt.getData());
		return rlt.getData();
	}

	@Override
	public String getOpenIdByUserId(String userId) {
		logger.info("getOpenIdByUserId begin,userId:{}",userId);
		GetPayOrganIdReq req = new GetPayOrganIdReq();
		req.setUserId(userId);
		ResultDto<String> rlt = null;
		try{
			rlt = memberClient.getPayOrganIdByUserId(req);
			logger.info("getOpenIdByUserId ===> memberClient.getPayOrganIdByUserId success,rlt:{}",rlt);
		}catch(Exception e){
			logger.error("getOpenIdByUserId ===> memberClient.getPayOrganIdByUserId exception,e:",e);
		}
		if(null != rlt && ResultCodeEnum.System.SUCCESS.getCode().equals(rlt.getResult())){
			return rlt.getData();
		}else{
			return null;
		}
	}

}
