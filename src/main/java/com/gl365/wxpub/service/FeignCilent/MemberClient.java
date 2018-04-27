package com.gl365.wxpub.service.FeignCilent;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gl365.wxpub.dto.PageDto;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.member.req.GetPayOrganIdReq;
import com.gl365.wxpub.dto.member.req.GetPhotoReq;
import com.gl365.wxpub.dto.member.req.GetUserByOpenIdReq;
import com.gl365.wxpub.dto.member.req.IndustryDto;
import com.gl365.wxpub.dto.member.req.UserRegistReq;
import com.gl365.wxpub.dto.member.resp.UserBindInfo;
import com.gl365.wxpub.dto.member.resp.users.UserDetailResp;
import com.gl365.wxpub.dto.member.resp.users.UserPhotoAndName;
import com.gl365.wxpub.dto.member.resp.users.UserRelationResp;
import com.gl365.wxpub.dto.member.resp.users.UserResp;
import com.gl365.wxpub.dto.merchant.req.GetUserFavorites4MemberCommand;
import com.gl365.wxpub.dto.merchant.req.IsFavoriteCommand;
import com.gl365.wxpub.dto.merchant.req.SaveFavorite4MemberCommand;
import com.gl365.wxpub.dto.merchant.req.SaveMerchantReport4MemberCommand;
import com.gl365.wxpub.dto.merchant.resp.MerchantFavoritesDto;


@FeignClient(name = "member", url = "${${env:}.url.member:}")
public interface MemberClient {
	
	/**
	 * 根据第三方支付id获取关联关系
	 */
	@RequestMapping(value = "/member/userRelation/getUserRelationByPayOrganId", method = RequestMethod.POST)
	public List<UserRelationResp> getUserRelationByPayOrganId(@RequestBody List<String> payOrganIds);
	
	/**
	 * 根据第三方支付id获取头像和昵称
	 */
	@RequestMapping(value = "/member/userRelation/getUserInfoByuserId", method = RequestMethod.POST)
	public List<UserRelationResp> getUserInfoByuserId(@RequestBody GetPhotoReq getPhotoReq);
	
	/**
	 * 引导用户注册合并用户
	 */
	@RequestMapping(value = "/member/userRelation/registPayOrgan", method = RequestMethod.POST)
	public ResultDto<?> registPayOrgan(@RequestBody UserRegistReq req);

	@RequestMapping(value = "/member/userRelation/createUserByPayOrganId", method = RequestMethod.POST)
	public ResultDto<UserBindInfo> createUserByPayOrganId(@RequestBody GetUserByOpenIdReq req);

	/**
	 * 根据userid用户信息
	 */
	@RequestMapping(value = "/member/user/info/userId", method = RequestMethod.POST)
	public UserDetailResp getUserInfoByUserId(@RequestBody UserResp userResp);
	
	/**
	 * 根据身份证判断用户是否存在
	 */
	@RequestMapping(value = "/member/user/info/idCard", method = RequestMethod.POST)
	public ResultDto<Boolean> getUserInfoByIdCard(@RequestParam("cardId") String cardId);
	
	/**
	 * 修改用户
	 */
	@RequestMapping(value = "/member/user/updateUserByUserId", method = RequestMethod.PUT)
	public ResultDto<Integer> updateUserByUserId(@RequestBody UserDetailResp user);
	
	/**
	 * 行业配置接口消费
	 * @return
	 */
	@RequestMapping(value = "/member/comment/industry", method = RequestMethod.POST)
	public ResultDto<IndustryDto> getIndustry();
	
	/**
	 * 收藏商家
	 * 
	 * @param command2member
	 * @return
	 */
	@RequestMapping(value = "/member/merchant/saveFavorites", method = RequestMethod.POST)
	ResultDto<Integer> saveFavorites(@RequestBody SaveFavorite4MemberCommand command2member);
	
	/**
	 * 取消收藏商家
	 * 
	 * @param command2member
	 * @return
	 */
	@RequestMapping(value = "/member/merchant/deleteFavorites", method = RequestMethod.POST)
	ResultDto<Integer> deleteFavorites(@RequestBody SaveFavorite4MemberCommand command2member);
	
	/**
	 * 用户是否收藏商家
	 * 
	 * @param isFavoriteCommand
	 * @return
	 */
	@RequestMapping(value = "/member/merchant/getIsFavorites", method = RequestMethod.POST)
	ResultDto<MerchantFavoritesDto> getIsFavoritesList(SaveFavorite4MemberCommand isFavoriteCommand);
	
	/**
	 * 举报商家
	 * 
	 * @param command2Member
	 * @return
	 */
	@RequestMapping(value = "/member/merchant/saveReport", method = RequestMethod.POST)
	ResultDto<Integer> saveReport(SaveMerchantReport4MemberCommand command2Member);
	
	/**
	 * 获取当前用户状态
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/member/user/info/{userId}", method = RequestMethod.GET)
	String getCurUserInfo(@PathVariable("userId") String userId);

	/**
	 * 获取收藏的商家列表
	 *
	 * @param command2Member
	 * @return
	 */
	@RequestMapping(value = "/member/merchant/getUserFavorites", method = RequestMethod.POST)
	ResultDto<PageDto<MerchantFavoritesDto>> getfavoritesList(@RequestBody GetUserFavorites4MemberCommand command2Member);

	/**
	 * 用户是否收藏商家
	 *
	 * @param isFavoriteCommand
	 * @return
	 */
	@RequestMapping(value = "/member/merchant/getIsFavorites", method = RequestMethod.POST)
	ResultDto<MerchantFavoritesDto> getIsFavoritesList(IsFavoriteCommand isFavoriteCommand);
	
	/**
	 * 根据用户Id获取头像和名称
	 * @param userIds
	 * @return
	 */
	@RequestMapping(value = "/member/userRelation/getUserPhotoAndName", method = RequestMethod.POST)
	List<UserPhotoAndName> getUserPhotoAndName(@RequestBody List<String> userIds);
	
	@RequestMapping(value = "/member/userRelation/getPayOrganIdByUserId", method = RequestMethod.POST)
	ResultDto<String> getPayOrganIdByUserId(GetPayOrganIdReq req);
	
}
