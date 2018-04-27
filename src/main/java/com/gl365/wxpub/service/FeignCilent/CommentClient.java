package com.gl365.wxpub.service.FeignCilent;

import com.gl365.wxpub.dto.PageDto;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.merchant.req.*;
import com.gl365.wxpub.dto.merchant.req.UpdateComment4MemberCommand;
import com.gl365.wxpub.dto.merchant.resp.*;
import com.gl365.wxpub.dto.order.req.CommentPersonal;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="order",url="${${env:}.url.order:}")
public interface CommentClient {

	/**
	 * 获取商家评论模板
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/order/comment/label/template", method = RequestMethod.POST)
	ResultDto<List<CommentLabelsTemplateDto>> templete(@RequestBody GetCommentTempleteCommand command);

	/**
	 * 更新商家评论
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/order/comment/updateComment", method = RequestMethod.POST)
	ResultDto<Integer> updateComment(@RequestBody UpdateComment4MemberCommand command);

	/**
	 * 获取商家评论列表
	 * 
	 * @param command4member
	 * @return
	 */
	@RequestMapping(value = "/order/comment/getCommentList", method = RequestMethod.POST)
	ResultDto<PageDto<CommentDto>> getCommentList(@RequestBody GetMerchantComments4MemberCommand command4member);

	/**
	 * 获取商家群买单评论列表
	 * 
	 * @param command4member
	 * @return
	 */
	@RequestMapping(value = "/order/comment/getCommentListForGroupPay", method = RequestMethod.POST)
	ResultDto<CommentDto> getCommentListForGroupPay(GetMerchantComments4MemberCommand command4member);
	
	/**
	 * 查询评论状态
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/order/comment/getCommentStatus", method = RequestMethod.POST)
	ResultDto<Integer> getCommentStatus(@RequestBody GetCommentStatusReq req);

	/**
	 * 查询打赏状态
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/order/comment/personalComment/status", method = RequestMethod.POST)
	List<PersonalCommentStatus> getPersonalCommentStatus(@RequestBody String req);

	/**
	 * 保存打赏信息
	 * 
	 * @param saveCommentPersonal4MemberCommand
	 * @return
	 */
	@RequestMapping(value = "/order/comment/saveCommentPersonal", method = RequestMethod.POST)
	ResultDto<Integer> saveCommentPersonal(@RequestBody SaveCommentPersonal4MemberCommand saveCommentPersonal4MemberCommand);

	/**
	 * 我的评论查询
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/order/comment/user/{userId}/{curPage}/{pageSize}", method = RequestMethod.GET)
	ResultDto<PageDto<MerchantCommontDto>> queryMyComment(@PathVariable("userId") String userId, @PathVariable("curPage") String curPage, @PathVariable("pageSize") String pageSize);

	@RequestMapping(value = "/order/comment/updateCommentPersonalStatus", method = RequestMethod.POST)
	ResultDto<?> updateCommentPersonalStatus(@RequestBody CommentPersonal command);
}
