package com.gl365.wxpub.service.FeignCilent;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gl365.wxpub.dto.PageDto;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.customize.CommentLabelsTemplateDto;
import com.gl365.wxpub.dto.customize.command.GetCommentTempleteCommand;
import com.gl365.wxpub.dto.member.req.MerchantCommontDto;
import com.gl365.wxpub.dto.merchant.req.GetMerchantComments4MemberCommand;
import com.gl365.wxpub.dto.merchant.req.SaveCommentPersonal4MemberCommand;
import com.gl365.wxpub.dto.merchant.req.UpdateComment4MemberCommand;
import com.gl365.wxpub.dto.merchant.resp.CommentDto;
import com.gl365.wxpub.dto.order.req.CreateOrderReq;
import com.gl365.wxpub.dto.order.req.GroupPayInitOrderReq;
import com.gl365.wxpub.dto.order.req.GroupPayReq;
import com.gl365.wxpub.dto.order.req.GroupPayRevokeReq;
import com.gl365.wxpub.dto.order.req.QueryOrderInfoReq;
import com.gl365.wxpub.dto.order.req.UpdateOrderInfoReq;
import com.gl365.wxpub.dto.order.resp.CreateOrderResp;
import com.gl365.wxpub.dto.order.resp.MainOrderInfo;
import com.gl365.wxpub.dto.order.resp.OrderComfirmInfo;
import com.gl365.wxpub.dto.order.resp.OrderInfoRltResp;
import com.gl365.wxpub.dto.order.resp.QueryOrderInfoResp;

@FeignClient(name="order",url="${${env:}.url.order:}")
public interface OrderClient {
	
	/**
	 * 获取商家评论模板
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/order/comment/label/template", method = RequestMethod.POST)
	ResultDto<List<CommentLabelsTemplateDto>> templete(@RequestBody GetCommentTempleteCommand command);

	@RequestMapping(value = "/order/rm/create", method = RequestMethod.POST)
	ResultDto<CreateOrderResp> createOrder(@RequestBody CreateOrderReq req);
	
	@RequestMapping(value = "/order/rm/getOrderBySn", method = RequestMethod.POST)
	ResultDto<QueryOrderInfoResp> queryOrderInfo(@RequestBody QueryOrderInfoReq req);
	
	/**
	 * 按用户id查询订单
	 */
	@RequestMapping(value = "order/rm/getOrderByMember/{memberId}", method = RequestMethod.POST)
	public ResultDto<OrderInfoRltResp> getOrderByMember(@PathVariable("memberId") String memberId);
	
	/**
	 * 按用户id查询群买单主订单
	 */
	@RequestMapping(value = "order/rm/getOrderMainByMember/{memberId}/{pageNo}/{pageSize}", method = RequestMethod.POST)
	public PageDto<OrderInfoRltResp> getByOrderMainMemberId(@PathVariable("memberId") String memberId,@PathVariable("pageNo") int pageNo,@PathVariable("pageSize") int pageSize);
	
	/**
	 * 按用户id查询群买单子订单
	 */
	@RequestMapping(value = "order/rm/getOrderSonByMember/{memberId}/{pageNo}/{pageSize}", method = RequestMethod.POST)
	public PageDto<OrderInfoRltResp> getByOrderSonMemberId(@PathVariable("memberId") String memberId,@PathVariable("pageNo") int pageNo,@PathVariable("pageSize") int pageSize);
	
	/**
	 * 按用户id查询群买单交易成功的订单
	 */
	@RequestMapping(value = "order/rm/getOrderByGroup/{groupId}/{pageNo}/{pageSize}", method = RequestMethod.POST)
	public PageDto<OrderInfoRltResp> getByOrderGroupId(@PathVariable("groupId") String groupId,@PathVariable("pageNo") int pageNo,@PathVariable("pageSize") int pageSize);
	
	/**
	 * 根据状态和群id查询订单
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/order/rm/getByOrderGroupIdCancelOrder/{groupId}/{orderStatus}/{pageNo}/{pageSize}", method = RequestMethod.POST)
	public PageDto<OrderInfoRltResp> getByOrderGroupIdCancelOrder(@PathVariable("groupId") String groupId,@PathVariable("orderStatus") String orderStatus,@PathVariable("pageNo") int pageNo,@PathVariable("pageSize") int pageSize);
	
	/**
	 * 群买单获取订单号
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/order/rm/init/create", method = RequestMethod.POST)
	public ResultDto<CreateOrderResp> groupPayInitOrder(@RequestBody GroupPayInitOrderReq req);
	
	/**
	 * 群买单支付获取tokenId
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/order/rm/pay/create", method = RequestMethod.POST)
	public ResultDto<CreateOrderResp> groupPayOrder(@RequestBody GroupPayReq req);
	
	/**
	 * 订单号查询主单信息
	 * @param orderSn
	 * @return
	 */
	@RequestMapping(value = "order/rm/getOrderMainBySn/{orderSn}", method = RequestMethod.POST)
	public ResultDto<MainOrderInfo> getOrderMainBySn(@PathVariable("orderSn") String orderSn);
	
	/**
	 * 保存打赏信息
	 * 
	 * @param saveCommentPersonal4MemberCommand
	 * @return
	 */
	@RequestMapping(value = "/order/comment/saveCommentPersonal", method = RequestMethod.POST)
	ResultDto<Integer> saveCommentPersonal(@RequestBody SaveCommentPersonal4MemberCommand saveCommentPersonal4MemberCommand);
	
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
	 * 我的评论查询
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/order/comment/user/{userId}/{curPage}/{pageSize}", method = RequestMethod.GET)
	ResultDto<PageDto<MerchantCommontDto>> queryMyComment(@PathVariable("userId") String userId, @PathVariable("curPage") String curPage, @PathVariable("pageSize") String pageSize);
	
	/**
	 * 补全订单信息
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "order/update", method = RequestMethod.POST)
	public ResultDto<?> updataOrderInfo(@RequestBody UpdateOrderInfoReq req);
	
	/**
	 * 群买单群主撤销
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/order/rm/c/group/revoke", method = RequestMethod.POST)
	ResultDto<?> groupPayRevoke(@RequestBody GroupPayRevokeReq req);
	
	/**
	 * 支付确认
	 * @param orderSn
	 * @return
	 */
	@RequestMapping(value = "/order/rm/confirm/{orderSn}", method = RequestMethod.POST)
	ResultDto<OrderComfirmInfo> orderPayComfirm(@PathVariable("orderSn") String orderSn);
}
