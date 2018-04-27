package com.gl365.wxpub.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.h5.resp.GroupPayAmount;
import com.gl365.wxpub.dto.h5.resp.GroupUserInfo;
import com.gl365.wxpub.enums.GroupPayStatus;
import com.gl365.wxpub.enums.GroupStatus;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.handler.ConfigHandler;
import com.gl365.wxpub.service.GroupService;
import com.gl365.wxpub.service.RedisService;
import com.gl365.wxpub.util.GsonUtils;

@Service
public class GroupServiceImpl implements GroupService{
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * redies 群状态key前缀
	 */
	private static final String GROUP_STATUS_PREFIX = "GROUP_STATUS_";
	
	/**
	 * 群买单金额key前缀
	 */
	private static final String GROUP_PAY_AMOUNT_PREFIX = "GROUP_PAY_AMOUNT__";
	
	private static final long Group_LiveTime = 86400L; //24小时
	
	@Override
	public boolean buildGroup(String groupId) {
		GroupStatus status = getGroupStatus(groupId);
		if(null == status){
			StringBuffer sb = new StringBuffer(GROUP_STATUS_PREFIX);
			sb.append(groupId);
			redisService.set(sb.toString(), GroupStatus.INIT.getStatus(), Group_LiveTime);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public ResultDto<?> InGroup(String groupId, String userId, String userName, String imgUrl) {
		GroupStatus status = getGroupStatus(groupId);
		if(status == null){
			return ResultDto.result(ResultCodeEnum.GroupPay.GROUP_NOT_EXIST);
		}else if(GroupStatus.INIT.getStatus().equals(status.getStatus())){
			if(redisService.IncGroupSize(groupId) - redisService.getDescGroupSize(groupId) > ConfigHandler.getInstance().getGroupMaxSize()){
				redisService.DescGroupSize(groupId);
				return ResultDto.result(ResultCodeEnum.GroupPay.GROUP_IS_FULL_INGROUP_FAIL);
			}else{
				initGrouperInfo2Redis(groupId, userId, userName, imgUrl);
				return ResultDto.result(ResultCodeEnum.System.SUCCESS);
			}
		}else if(GroupStatus.DISMISS.getStatus().equals(status.getStatus())){
			return ResultDto.result(ResultCodeEnum.GroupPay.GROUP_HAS_DISMISS);
		}else{
			return ResultDto.result(ResultCodeEnum.GroupPay.GROUP_PAY_BEGIN_INGROUP_FAIL);
		}
	}

	@Override
	public void outGroup(String groupId, String userId) {
		redisService.DescGroupSize(groupId);
		StringBuffer sb = new StringBuffer(groupId);
		sb.append("_").append(userId);
		if(StringUtils.isNotBlank(redisService.get(sb.toString()))){
			redisService.del(sb.toString());
		}
	}

	@Override
	public void updateGroup(String groupId, GroupStatus status) {
		StringBuffer sb = new StringBuffer(GROUP_STATUS_PREFIX);
		sb.append(groupId);
		redisService.set(sb.toString(), status.getStatus(), Group_LiveTime);
	}

	@Override
	public int queryGroupSize(String groupId) {
		return redisService.getIncGroupSize(groupId) - redisService.getDescGroupSize(groupId);
	}

	@Override
	public List<GroupUserInfo> queryGroupInfo(String groupId) {
		StringBuffer sb = new StringBuffer(groupId);
		Object obj = redisService.getKeys(sb.append("_*").toString());
		if(null == obj){
			return null;
		}
		Set<String> keys = (Set<String>)obj;
		List<GroupUserInfo> list = new ArrayList<>();
		for (String key : keys) {
			String jsonString = redisService.get(key);
			GroupUserInfo info = GsonUtils.fromJson2Object(jsonString, GroupUserInfo.class);
			if(info != null)
				list.add(info);
		}
		return list;
	}

	@Override
	public GroupStatus queryGroupStatus(String groupId) {
		return getGroupStatus(groupId);
	}
	
	@Override
	public GroupUserInfo queryGroupUserInfo(String groupId, String userId) {
		StringBuffer sb = new StringBuffer(groupId);
		sb.append("_").append(userId);
		String jsonString = redisService.get(sb.toString());
		GroupUserInfo info = GsonUtils.fromJson2Object(jsonString, GroupUserInfo.class);
		return info;
	}

	@Override
	public void updateGroupUserInfo(String groupId, GroupUserInfo info) {
		StringBuffer sb = new StringBuffer(groupId);
		sb.append("_").append(info.getUserId());
		redisService.set(sb.toString(), GsonUtils.toJson(info), Group_LiveTime);
	}
	
	@Override
	public void setGroupPayAmount(String groupId, GroupPayAmount gpa) {
		StringBuffer sb = new StringBuffer(GROUP_PAY_AMOUNT_PREFIX);
		sb.append(groupId);
		redisService.set(sb.toString(), GsonUtils.toJson(gpa), Group_LiveTime);
	}

	@Override
	public GroupPayAmount queryGroupPayAmount(String groupId) {
		StringBuffer sb = new StringBuffer(GROUP_PAY_AMOUNT_PREFIX);
		sb.append(groupId);
		String jsonString = redisService.get(sb.toString());
		return GsonUtils.fromJson2Object(jsonString, GroupPayAmount.class);
	}

	private GroupStatus getGroupStatus(String groupId){
		StringBuffer sb = new StringBuffer(GROUP_STATUS_PREFIX);
		sb.append(groupId);
		String status = redisService.get(sb.toString());
		if(GroupStatus.BUILD_COMPLETE.getStatus().equals(status)){
			return GroupStatus.BUILD_COMPLETE;
		}else if(GroupStatus.INIT.getStatus().equals(status)){
			return GroupStatus.INIT;
		}else if(GroupStatus.MAINGROUPER_PAYED.getStatus().equals(status)){
			return GroupStatus.MAINGROUPER_PAYED;
		}else if(GroupStatus.MAINGROUPER_PAYING.getStatus().equals(status)){
			return GroupStatus.MAINGROUPER_PAYING;
		}else if(GroupStatus.DISMISS.getStatus().equals(status)){
			return GroupStatus.DISMISS;
		}else if(GroupStatus.CANCEL.getStatus().equals(status)){
			return GroupStatus.CANCEL;
		}else{
			return null;
		}
	}
	
	/**
	 * 进群
	 * @param groupId
	 * @param userId
	 * @param userName
	 * @param imgUrl
	 */
	private void initGrouperInfo2Redis(String groupId, String userId, String userName, String imgUrl){
		GroupUserInfo info = new GroupUserInfo();
		info.setImgUrl(imgUrl);
		info.setPayStatus(GroupPayStatus.INIT);
		info.setUserId(userId);
		info.setUserName(userName);
		StringBuffer sb = new StringBuffer(groupId);
		sb.append("_").append(userId);
		redisService.set(sb.toString(), GsonUtils.toJson(info), Group_LiveTime);
	}

}
