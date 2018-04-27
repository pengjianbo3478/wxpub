package com.gl365.wxpub.service;

import java.util.List;

import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.h5.resp.GroupPayAmount;
import com.gl365.wxpub.dto.h5.resp.GroupUserInfo;
import com.gl365.wxpub.enums.GroupStatus;

/**
 * 群买单服务
 * @author dfs_519
 *2017年9月6日上午10:19:15
 */
public interface GroupService {
	
	/**
	 * 建群
	 * @param groupId 群号
	 * @return true：成功     false：失败
	 */
	public boolean buildGroup(String groupId);
	
	/**
	 * 进群
	 * @param groupId 群号
	 * @param userId
	 * @param userName
	 * @param imgUrl 头像url
	 * @return code:000000 成功
	 */
	public ResultDto<?> InGroup(String groupId,String userId,String userName,String imgUrl);
	
	/**
	 * 退群
	 * @param groupId 群号
	 * @param userId
	 * @return
	 */
	public void outGroup(String groupId,String userId);
	
	/**
	 * 更新群状态
	 * @param groupId 群号
	 * @param status ("INIT", "建群中")("BUILD_COMPLETE","完成建群，开始分配支付金额，禁止进群")("MAINGROUPER_PAYING","群主支付中，其他人未支付的禁止发起支付")("MAINGROUPER_PAYED","群支付完成")
	 * @return
	 */
	public void updateGroup(String groupId,GroupStatus status);
	
	/**
	 * 查询当前群人数
	 * @param groupId 群号
	 * @return
	 */
	public int queryGroupSize(String groupId);
	
	/**
	 * 查询群所有成员信息
	 * @param groupId
	 * @return
	 */
	public List<GroupUserInfo> queryGroupInfo(String groupId);
	
	/**
	 * 查询群状态
	 * @param groupId 群号
	 * @return GroupStatus:("INIT", "建群中")("BUILD_COMPLETE","完成建群，开始分配支付金额，禁止进群")("MAINGROUPER_PAYING","群主支付中，其他人未支付的禁止发起支付")("MAINGROUPER_PAYED","群支付完成")
	 */
	public GroupStatus queryGroupStatus(String groupId);
	
	/**
	 * 查询支付群中某个成员的信息
	 * @param groupId
	 * @param userId
	 * @return 没有返回null
	 */
	public GroupUserInfo queryGroupUserInfo(String groupId,String userId);
	
	/**
	 * 更新支付群中某个成员的信息
	 * @param groupId
	 * @param info
	 * @return
	 */
	public void updateGroupUserInfo(String groupId,GroupUserInfo info);
	
	/**
	 * 设置群买单总金额和认领金额
	 * @param groupId
	 * @param gpa
	 */
	public void setGroupPayAmount(String groupId,GroupPayAmount gpa);
	
	/**
	 * 查询群买单总金额和认领金额
	 * @param groupId
	 * @return
	 */
	public GroupPayAmount queryGroupPayAmount(String groupId);
}
