package com.gl365.wxpub.web;


import com.gl365.wxpub.aspect.Log;
import com.gl365.wxpub.aspect.Login;
import com.gl365.wxpub.common.CommonHelper;
import com.gl365.wxpub.common.Constant;
import com.gl365.wxpub.common.JsonUtils;
import com.gl365.wxpub.common.build.CommentBuild;
import com.gl365.wxpub.common.build.MerchantBuild;
import com.gl365.wxpub.dto.PageDto;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.merchant.req.*;
import com.gl365.wxpub.dto.merchant.resp.*;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.service.FeignCilent.CommentClient;
import com.gl365.wxpub.service.FeignCilent.MemberClient;
import com.gl365.wxpub.service.FeignCilent.MerchantClient;
import com.gl365.wxpub.service.FeignCilent.OrderClient;
import com.gl365.wxpub.service.MerchantService;
import com.gl365.wxpub.util.AmountTransferUtils;
import com.gl365.wxpub.util.RespUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HttpMethod;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jdk.internal.dynalink.CallSiteDescriptor.OPERATOR;

@CrossOrigin(origins = "*", maxAge = 3600) // 跨域问题，与前端调试时打开
@RestController
@RequestMapping("/wxpubApi/merchant")
@Api(description = "商户相关",tags = "merchant")
public class MerchantController {
	
	private static final Logger logger = LoggerFactory.getLogger(MerchantController.class);
	
	private static final Integer OPERATOR = 3, SHOP_MANAGER = 2;

	@Autowired
	private OrderClient orderService;
	@Autowired
	private MerchantService merchantService;

	@Resource
	private MerchantClient merchantClient;

	@Resource
	private CommentClient commentClient;

	@Resource
	private MemberClient memberClient;

	@GetMapping("/queryMerBaseInfo")
	@Log("查询商户基本信息")
	@ApiOperation(value = "查询商户基本信息", httpMethod = "GET", response = ResultDto.class)
	@ApiImplicitParam(dataType = "String",name ="merchantNo",value = "商户号",required = true)
	public ResultDto queryMerBaseInfo(@RequestParam(value="merchantNo",required=true) String merchantNo){
		return merchantService.getMerchantBaseInfo(merchantNo);
	}

	/**
	 * 获取商家详情
	 *
	 * @param
	 * @return
	 */
	@Login
	@GetMapping("/detail/{merchantNo}")
	@ApiOperation(value = "查询商家详情",httpMethod = HttpMethod.GET)
	@ApiImplicitParam(dataType = "String",name ="merchantNo",value = "商户号",required = true)
	@Log
	public ResultDto getDetail(@PathVariable("merchantNo") String merchantNo, HttpServletRequest request) {
		logger.info("获取商家详情 > > > 商家编号或预绑定二维码：{}", merchantNo);
		// 为兼容此处merchantNo可能为barCode,因此先当做barCode查询商户在赋值 merchantNo后组装信息(barCode与merchantNo不存在相同情况)
		ResultDto<MerchantBaseInfo> rlt = merchantService.getMerchantBaseInfo(merchantNo);
		logger.info("商家详情 > > >{}", rlt);
		MerchantBaseInfo baseInfo = RespUtil.getData(rlt);
		if(baseInfo != null && StringUtils.isNotBlank(baseInfo.getMerchantNo())){
			merchantNo = baseInfo.getMerchantNo();
		}
		logger.info("获取商家详情 > > > 商家编号：{}", merchantNo);
		String userId = CommonHelper.getUserIdByRequest();
		// 调用Merchant服务取商家详情
		GetMerchantDetail4MerchantCommand command2Merchant = new GetMerchantDetail4MerchantCommand(merchantNo);
		ResultDto<List<MerchantInfoDto>> resultDto = merchantClient.getMerchantDetail(command2Merchant);
		List<MerchantInfoDto> result = RespUtil.getData(resultDto);
		if(CollectionUtils.isEmpty(result) ){
			return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR.getCode(), "查不到商户", null);
		}
		MerchantDetailCallback data = MerchantBuild.buildMerchantCallback(result.get(0));
		// 是否允许商家在线支付
		data.setOnlinePay(merchantService.isMerchantOnlinePay(merchantNo, Constant.RM_ORGAN_CODE).booleanValue());
		// 用户是否收藏
		IsFavoriteCommand isFavoriteCommand = new IsFavoriteCommand(userId, merchantNo);
		ResultDto<MerchantFavoritesDto> favoritesDtoResultDto = memberClient.getIsFavoritesList(isFavoriteCommand);
		MerchantFavoritesDto favoritesDto = RespUtil.getData(favoritesDtoResultDto);
		data.setFavorite(null == favoritesDto ? false : true);
		return ResultDto.success(data);
	}


	/**
	 * 获取商家评论列表
	 *
	 * @param command
	 * @return
	 */
	@PostMapping("/comments")
	@ApiOperation(value = "商户评论列表",httpMethod = HttpMethod.POST)
	@Log
	public ResultDto getCommentList(@RequestBody GetMerchantCommentsCommand command) {
		logger.info("获取商家评论列表 > > > 入参：{}", JsonUtils.toJsonString(command));
		GetMerchantComments4MemberCommand target = new GetMerchantComments4MemberCommand();
		target.setCurPage(command.getCurPage());
		target.setPageSize(command.getPageSize());
		target.setMerchantNo(command.getMerchantNo());
		target.setComment(true); // 取已评论
		ResultDto<BigDecimal>	resultDto = merchantClient.getMerchantCommentGrade(command.getMerchantNo());
		BigDecimal grade = RespUtil.getData(resultDto);
		ResultDto<PageDto<CommentDto>> commentResultDto = commentClient.getCommentList(target);
		return CommentBuild.commentCallbackBuild(commentResultDto, grade);
	}


	/**
	 * 获取商家评论列表
	 *
	 * @param command
	 * @return
	 */
	@PostMapping("/myComments")
	@ApiOperation(value = "商户我的评论列表",httpMethod = HttpMethod.POST)
	@Log
	@Login
	public ResultDto getMyCommentList(@RequestBody GetMerchantCommentsCommand command) {
		logger.info("获取商家评论列表 > > > 入参：{}", JsonUtils.toJsonString(command));
		String userId = CommonHelper.getUserIdByRequest();
		GetMerchantComments4MemberCommand target = new GetMerchantComments4MemberCommand();
		target.setUserId(userId);
		target.setCurPage(command.getCurPage());
		target.setPageSize(command.getPageSize());
		target.setMerchantNo(command.getMerchantNo());
		target.setComment(true); // 取已评论
		ResultDto<BigDecimal>	resultDto = merchantClient.getMerchantCommentGrade(command.getMerchantNo());
		BigDecimal grade = RespUtil.getData(resultDto);
		ResultDto<PageDto<CommentDto>> commentResultDto = commentClient.getCommentList(target);
		return CommentBuild.commentCallbackBuild(commentResultDto, grade);
	}

	/**
	 * 收藏商家
	 *
	 * @param request
	 * @return
	 */
	@PostMapping("/favorites")
	@ApiOperation(value = "收藏商家",httpMethod = HttpMethod.POST)
	@Login(needLogin = true)
	@Log
	public ResultDto saveFavorites(@RequestBody SaveFavoriteCommand command, HttpServletRequest request) {
		logger.info("收藏商家 > > > 商家编号：{}", command.getMerchantNo());
		String userId = CommonHelper.getUserIdByRequest();
		// 调用服务
		SaveFavorite4MemberCommand command2member = new SaveFavorite4MemberCommand();
		command2member.setUserId(userId);
		command2member.setMerchantNo(command.getMerchantNo());
		return memberClient.saveFavorites(command2member);
	}

	/**
	 * 取消收藏商家
	 *
	 * @param request
	 * @return
	 */
	@PostMapping("/deleteFavorites")
	@ApiOperation(value = "取消收藏商家",httpMethod = HttpMethod.POST)
	@Login(needLogin = true)
	@Log
	public ResultDto delteFavorites(@RequestBody SaveFavoriteCommand command, HttpServletRequest request) {
		logger.info("取消收藏商家 > > > 商家编号：{}", command.getMerchantNo());
		String userId = CommonHelper.getUserIdByRequest();
		// 调用服务
		SaveFavorite4MemberCommand command2member = new SaveFavorite4MemberCommand();
		command2member.setUserId(userId);
		command2member.setMerchantNo(command.getMerchantNo());
		return memberClient.deleteFavorites(command2member);
	}

	/**
	 * 发表评论
	 *
	 * @param command
	 * @return
	 */
	@PostMapping("/comment")
	@ApiOperation(value = "对商家评论",httpMethod = HttpMethod.POST)
	@Login(needLogin = true)
	@Log
	public ResultDto saveComment(@RequestBody SaveCommentCommand command,HttpServletRequest request) {
		logger.info("发表商家评论 > > > 入参：{}", JsonUtils.toJsonString(command));
		String userId = CommonHelper.getUserIdByRequest();
		// 保存评论
		UpdateComment4MemberCommand command4Member = new UpdateComment4MemberCommand();
		command4Member.setUserId(userId);
		command4Member.setMerchantNo(command.getMerchantNo());
		command4Member.setGrade(command.getGrade());
		command4Member.setLabels(command.getLabels());
		command4Member.setPaymentNo(command.getPaymentNo());
		command4Member.setContent(command.getContent());
		return commentClient.updateComment(command4Member);
	}


	/**
	 * 打赏
	 * 
	 * @param command
	 * @param request
	 * @return
	 */
	@Login(needLogin = true)
	@ApiOperation("个人评论（打赏）")
	@PostMapping("/commentPersonal")
	@Log
	public Object commentPersonal(@RequestBody SaveCommentPersonalCommand command, HttpServletRequest request) {
		logger.info("保存打赏信息 》》》入参：{}", JsonUtils.toJsonString(command));
		String userId = CommonHelper.getUserIdByRequest();
		if (null == userId || "null".equals(userId)){
			return ResultDto.result(ResultCodeEnum.System.ILLEGAL_REQUEST);
		}
		return orderService.saveCommentPersonal(new SaveCommentPersonal4MemberCommand(command, userId.toString()));
	}

	/**
	 * 举报商家
	 * 
	 * @param command
	 * @return
	 */
	@Login
	@PostMapping("/report")
	@Log("举报商家")
	public Object saveReport(@RequestBody SaveMerchantReport4FrontCommand command, HttpServletRequest request) {
		logger.info("举报商家> > > 入参：{}", JsonUtils.toJsonString(command));
		String userId = CommonHelper.getUserIdByRequest();
		if (null == userId || "null".equals(userId)){
			return ResultDto.result(ResultCodeEnum.System.ILLEGAL_REQUEST);
		}
		SaveMerchantReport4MemberCommand command4Member = new SaveMerchantReport4MemberCommand();
		command4Member.setUserId(userId.toString());
		return memberClient.saveReport(command4Member);
	}

	/**
	 * 查询商户线上支付方式
	 * 
	 * @return
	 */
	@ApiOperation("查询商户线上支付方式")
	@PostMapping("/queryOnPayInfo")
	@Log("查询商户线上支付方式")
	public Object queryOnPayInfo(@RequestBody MerPayAccount req) {
		logger.info("查询商户线上支付方式 queryOnPayInfo begin param={}", JsonUtils.toJsonString(req));
		Long beginTime = System.currentTimeMillis();
		ResultDto<?> rlt = null;
		try {
			if (StringUtils.isBlank(req.getMerchantNo())) {
				return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
			}
			rlt = merchantClient.queryOnPayInfo(req.getMerchantNo());
		} catch (Exception e) {
			logger.error("查询商户线上支付方式 queryOnPayInfo exception,e:{}", e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		logger.info("查询商户线上支付方式 queryOnPayInfo end rlt={},time={}ms", JsonUtils.toJsonString(rlt),(endTime - beginTime));
		return rlt;
	}
	
	@Login
	@Log("查询商户员工列表")
	@GetMapping("/employees")
	public Object getMerchantOpertatorList(HttpServletRequest request,@RequestParam("merchantNo")String merchantNo) {
		return merchantService.getEmployees(new GetOperatorListCommand(merchantNo, OPERATOR, SHOP_MANAGER));
	}
	
	@ApiOperation("百度批量查询商户信息")
	@PostMapping("/baidu/getMerchant")
	public Object getMerchant(@RequestBody GetMerchantDetail4MerchantCommand command) {
		logger.info("群买单记录  getMerchant begin loginUserId={} ", command.getMerchantNo().size());
		Long beginTime = System.currentTimeMillis();
		if(null==command.getMerchantNo()||0==command.getMerchantNo().size()){
			return new ResultDto(ResultCodeEnum.System.SUCCESS, new ArrayList());
		}
		
		ResultDto<?> rlt = null;
		try {
				List<MerchantInfoDto> merchantInfoList = merchantClient.getMerchantByMerchantNoList(command.getMerchantNo());
				logger.info("merchantInfoService result{}", JsonUtils.toJsonString(merchantInfoList));
				rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS, merchantInfoList);
		} catch (Exception e) {
			logger.error("百度批量查询商户信息 merchantInfoService exception,e:{}", e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		logger.info("百度批量查询商户信息  merchantInfoService end rlt={},time={}ms", JsonUtils.toJsonString(rlt), (endTime - beginTime));
		return rlt;
	}

}
