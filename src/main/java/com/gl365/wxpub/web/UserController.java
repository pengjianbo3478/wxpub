package com.gl365.wxpub.web;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HttpMethod;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.wxpub.aspect.Log;
import com.gl365.wxpub.aspect.Login;
import com.gl365.wxpub.common.CommonHelper;
import com.gl365.wxpub.common.Constant;
import com.gl365.wxpub.common.JsonUtils;
import com.gl365.wxpub.common.build.FavoriteBuild;
import com.gl365.wxpub.dto.LotteryDto;
import com.gl365.wxpub.dto.PageDto;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.UserPageInfoDto;
import com.gl365.wxpub.dto.account.req.ActBalanceInfoReq;
import com.gl365.wxpub.dto.account.rlt.ActBalance;
import com.gl365.wxpub.dto.member.req.UserIdCardReq;
import com.gl365.wxpub.dto.member.req.UserRegistReq;
import com.gl365.wxpub.dto.member.resp.ActiveMerchantInfoResp;
import com.gl365.wxpub.dto.member.resp.users.UserCoinAndPhotoDto;
import com.gl365.wxpub.dto.member.resp.users.UserDetailResp;
import com.gl365.wxpub.dto.member.resp.users.UserRelationResp;
import com.gl365.wxpub.dto.member.resp.users.UserResp;
import com.gl365.wxpub.dto.merchant.req.GetUserFavorites4MemberCommand;
import com.gl365.wxpub.dto.merchant.req.GetUserFavoritesCommand;
import com.gl365.wxpub.dto.merchant.resp.MerchantFavoriteCallback;
import com.gl365.wxpub.dto.merchant.resp.MerchantFavoritesDto;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.handler.ConfigHandler;
import com.gl365.wxpub.service.RedisService;
import com.gl365.wxpub.service.FeignCilent.AccountClient;
import com.gl365.wxpub.service.FeignCilent.CommentClient;
import com.gl365.wxpub.service.FeignCilent.MemberClient;
import com.gl365.wxpub.service.FeignCilent.MerchantClient;
import com.gl365.wxpub.service.FeignCilent.OrderClient;
import com.gl365.wxpub.service.FeignCilent.PromotionClient;
import com.gl365.wxpub.service.FeignCilent.ValidatorClient;
import com.gl365.wxpub.service.impl.LotteryServiceImpl;
import com.gl365.wxpub.util.RespUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600) // 跨域问题，与前端调试时打开
@RestController
@RequestMapping("/wxpubApi/user")
@Api(description = "用户相关",tags = "user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private RedisService redisService;
	@Autowired
	private MemberClient memberClient;

	@Resource
	private MerchantClient merchantClient;

	@Resource
	private CommentClient commentClient;

	@Autowired
	private ValidatorClient validatorService;
	@Autowired
	private AccountClient accountService;
	@Autowired
	private OrderClient orderService;
	@Autowired
	private LotteryServiceImpl lotteryServiceImpl;
	
	@Autowired
	private PromotionClient promotionClient;

	private static final String RESULT_SUCCESS_CODE = ResultCodeEnum.System.SUCCESS.getCode();//成功返回码

	@ApiOperation("引导注册")
	@PostMapping("/register")
	@Login
	public ResultDto<?> register(@RequestBody UserRegistReq req) {
		String userId = CommonHelper.getUserIdByRequest();
		logger.info("引导注册 register begin param={},userId={}", JsonUtils.toJsonString(req),userId);
		Long beginTime = System.currentTimeMillis();
		ResultDto<?> rlt = null;
		try {
			String wxToken = req.getWxToken();
			String mobilePhone = req.getMobilePhone();
			String channel = req.getChannel();
			if (StringUtils.isEmpty(channel) || StringUtils.isEmpty(wxToken) || StringUtils.isEmpty(mobilePhone) || StringUtils.isEmpty(userId)){
				return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
			}
			String key = SmsController.WX_SEND_SMS_PREFIX+mobilePhone+"7";
			String token = (String) redisService.get(key);
			if(!wxToken.equals(token)){
				return new ResultDto<>(ResultCodeEnum.System.PARAM_ERROR.getCode(), "短信超时,请重新发送", null);
			}
			String active = "01";//00活动注册，01非活动注册
			if("00".equals(req.getActive())){
				active = "00";
				if(StringUtils.isBlank(req.getActiveId())){
					return new ResultDto<>(ResultCodeEnum.System.PARAM_ERROR.getCode(), "活动id不能为空", null);
				}
			}
			rlt = memberClient.registPayOrgan(new UserRegistReq(userId, mobilePhone, channel, active, req.getActiveId()));
			
			if ("00".equals(req.getActive()) && ResultCodeEnum.System.SUCCESS.getCode().equals(rlt.getResult())) {
				if(null != rlt.getData()){
					@SuppressWarnings("unchecked")
					Map<String, Object> rltMap = (Map<String, Object>) rlt.getData();
					String id = (String) rltMap.get("userId");
					String payOrganId = (String) rltMap.get("payOrganId");
					//放入缓存
					LotteryDto dto = new LotteryDto();
					dto.setOpenId(payOrganId);
					dto.setMobileNo(mobilePhone);
					dto.setActivityId(req.getActiveId());
					dto.setMerchantNo(req.getMerchantNo());
					dto.setUserId(id);
					dto.setDraw(false);
					
					
					LotteryDto result = lotteryServiceImpl.getLotteryFromRedis(payOrganId, req.getActiveId(),req.getSource());
					if(result == null || Boolean.FALSE.equals(result.isDraw())){
						//20171214 增加活动开始和结束时间
						try{
							ResultDto<ActiveMerchantInfoResp> resp = promotionClient.getActivityInfoByMerchantNo(req.getMerchantNo());
							dto.setBeginTime(resp.getData().getBeginTime());
							dto.setEndTime(resp.getData().getBeginTime());
						}catch(Exception e){
							logger.error("引导注册 register ===> promotionClient.getActivityInfoByMerchantNo exception:",e);
						}
						
						logger.info("放入活动redis begin param={}", JsonUtils.toJsonString(dto));
						lotteryServiceImpl.savaLottery2Redis(dto,req.getSource());
						logger.info("放入活动redis end success");
						// 读取活动配置文url
						String activeUrl = ConfigHandler.getInstance().getH5ActiveUrl() + req.getActiveId() + "/";
						rltMap.put("activeUrl", activeUrl);
						rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS, rltMap);
					}else{
						rlt = ResultDto.result(ResultCodeEnum.Lottery.HAS_DRAW_PRIZE);
					}
				}
			}
		} catch (Exception e) {
			logger.error("引导注册 register exception,e:{}", e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		logger.info("引导注册 register end rlt={},time={}ms", JsonUtils.toJsonString(rlt), (endTime - beginTime));
		return rlt;
	}

	/**
	 * 获取收藏商家列表
	 *
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/getFavoritesList")
	@ApiOperation(value = "获取收藏商家列表",httpMethod = HttpMethod.POST)
	@Login(needLogin = true)
	@Log
	public ResultDto getfavortesList(@RequestBody GetUserFavoritesCommand command, HttpServletRequest request) {
		logger.info("获取已收藏的商家列表 > > > 入参：{}", JsonUtils.toJsonString(command));
		String userId = CommonHelper.getUserIdByRequest();
		// 获取收藏的商家编号
		GetUserFavorites4MemberCommand command2Member = new GetUserFavorites4MemberCommand(userId, command.getCurPage(), command.getPageSize());
		ResultDto<PageDto<MerchantFavoritesDto>> responseTemp = memberClient.getfavoritesList(command2Member);
		PageDto<MerchantFavoritesDto> resultDate = RespUtil.getData(responseTemp);
		if (CollectionUtils.isEmpty(resultDate.getList())) {
			return ResultDto.result(ResultCodeEnum.System.SUCCESS, new PageDto<>(0, 0, command.getCurPage(), resultDate.getList()));
		}
		// 批量获取商家详情
		List<String> merchantNos = resultDate.getList().stream().map(MerchantFavoritesDto::getMerchantNo).collect(Collectors.toList());
		// 商家明细绑定到收藏的商家列表中
		ResultDto<List<MerchantFavoriteCallback>> details = FavoriteBuild.buildFavoriteCallback(merchantClient.getMerchantBasicDetail4Favorite(merchantNos));
		List<MerchantFavoriteCallback> detailList = RespUtil.getData(details);
		return new ResultDto<>(details.getResult(), details.getDescription(), new PageDto<>(resultDate.getTotalCount(), resultDate.getTotalPage(), resultDate.getCurPage(), detailList));
	}

	@Login
	@ApiOperation("判断用户是否绑定")
	@PostMapping("/userBind")
	public Object userBind(HttpServletRequest request) {
		String openId = CommonHelper.getOpenIdByRequest();
		logger.info("判断用户是否绑定 userBind begin param={}", openId);
		Long beginTime = System.currentTimeMillis();
		ResultDto<?> rlt = null;
		try {
			if (StringUtils.isEmpty(openId)){
				return new ResultDto<>(ResultCodeEnum.System.ILLEGAL_REQUEST);
			}
			List<String> payOrganIds = new ArrayList<>();
			payOrganIds.add(openId);
			List<UserRelationResp> list = memberClient.getUserRelationByPayOrganId(payOrganIds);
			logger.info("判断用户是否绑定result={}",JsonUtils.toJsonString(list));
			if(list.size()!=1){
				return new ResultDto<>(ResultCodeEnum.System.SYSTEM_DATA_EXECEPTION);
			}
			String newUserId = list.get(0).getNewUserId();
			String oldUserId = list.get(0).getOldUserId();
			Map<String, Object> rltMap = new HashMap<>();
			rltMap.put("isBind", true);
			rltMap.put("userId", null);
			if(StringUtils.isBlank(oldUserId)){
				rltMap.put("isBind", false);
				rltMap.put("userId", newUserId);
				rltMap.put("amount", getMemberInfoToM(newUserId));
			}else{
				UserResp userResp = new UserResp();
				userResp.setUserId(oldUserId);
				UserDetailResp user  = memberClient.getUserInfoByUserId(userResp);
				if(null == user){
					return new ResultDto<>(ResultCodeEnum.User.NO_MEMBER_INFO_ERROR);
				}
				if(null != user.getAuthStatus() && 0 != user.getAuthStatus()){
					rltMap.put("authStatus", 1);
				}else{
					rltMap.put("authStatus", 0);
				}
			}
			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS, rltMap);
		} catch (Exception e) {
			logger.error("判断用户是否绑定 userBind exception,e:{}", e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		logger.info("判断用户是否绑定 userBind end rlt={},time={}ms", JsonUtils.toJsonString(rlt), (endTime - beginTime));
		return rlt;
	}

	/**
	 * 查询乐豆余额
	 * @param userId
	 * @return BigDecimal
	 */
	private BigDecimal getMemberInfoToM(String userId) {
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		ActBalanceInfoReq param = new ActBalanceInfoReq();
		param.setUserId(userId);
		param.setAgentId(Constant.hcAgentID);
		logger.info("accountService.queryAccountBalanceInfo param:{}", JsonUtils.toJsonString(param));
		ActBalance actBalance = accountService.queryAccountBalanceInfo(param);
		logger.info("accountService.queryAccountBalanceInfo success rlt:{}", JsonUtils.toJsonString(actBalance));
		if (!Constant.actRespSuccess.equals(actBalance.getResultCode())) {
			return null;
		}
		return actBalance.getBalance();
	}

	@Login
	@ApiOperation("实名认证")
	@PostMapping("/certification")
	public Object certification(HttpServletRequest request, @RequestBody UserIdCardReq command) {
		String userId = CommonHelper.getUserIdByRequest();
		logger.info("实名认证 certification begin command={},userId={}", command, userId);
		Long beginTime = System.currentTimeMillis();
		ResultDto<?> rlt = null;
		try {
			if (StringUtils.isEmpty(userId)){
				return new ResultDto<>(ResultCodeEnum.System.ILLEGAL_REQUEST);
			}
			if (StringUtils.isBlank(command.getIdCard()) || StringUtils.isBlank(command.getName())) {
				return ResultDto.result(ResultCodeEnum.System.PARAM_NULL);
			}
			rlt = certification(command, userId);
		} catch (Exception e) {
			logger.error("实名认证 certification exception,e:{}", e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		logger.info("实名认证 certification end rlt={},time={}ms", JsonUtils.toJsonString(rlt), (endTime - beginTime));
		return rlt;
	}

	private ResultDto<?> certification(UserIdCardReq userIdCardDto, String userId) {
		//校验用户
		UserResp userResp = new UserResp();
		userResp.setUserId(userId);
		UserDetailResp user  = memberClient.getUserInfoByUserId(userResp);
		if(null == user){
			return new ResultDto<>(ResultCodeEnum.User.NO_MEMBER_INFO_ERROR);
		}
		//校验身份证是否存在
		ResultDto<Boolean> hasIdCard = memberClient.getUserInfoByIdCard(userIdCardDto.getIdCard());
		if(!RESULT_SUCCESS_CODE.equals(hasIdCard.getResult())){
			return hasIdCard;
		}else{
			if(hasIdCard.getData()){
				return new ResultDto<>(ResultCodeEnum.User.IDCARD_LIMIT_ERROR);
			}
		}
		//实名认证
		ResultDto<Boolean> rlt = validatorService.certification(userIdCardDto.getIdCard(), userIdCardDto.getName());
		logger.info("实名认证结果   > > > rlt={}", JsonUtils.toJsonString(rlt));
		if(!RESULT_SUCCESS_CODE.equals(rlt.getResult())){
			return rlt;
		}

		ResultDto<Map<String,Integer>> rtData = new ResultDto<>();
		if(null != rlt.getData() && rlt.getData()){
			//实名认证成功后调用手机认证
			ResultDto<Boolean> phoneRlt = validatorService.phoneValidate(user.getMobilePhone(), userIdCardDto.getIdCard(), userIdCardDto.getName());
			logger.info("手机认证结果   > > > rlt={}", JsonUtils.toJsonString(rlt));
			if(!RESULT_SUCCESS_CODE.equals(rlt.getResult())){
				return rlt;
			}
			//更新用户认证数据
			UserDetailResp userDetailResp = new UserDetailResp();
			userDetailResp.setUserId(userId);
			if(0 == user.getAuthStatus()){
				Integer authStatus = 11000000;
				if(null != phoneRlt.getData() && phoneRlt.getData()){
					authStatus = 11100000;
				}
				userDetailResp.setAuthStatus(authStatus);
				userDetailResp.setCertType(1);
			}
			userDetailResp.setCertNum(userIdCardDto.getIdCard());
			userDetailResp.setRealName(userIdCardDto.getName());
			userDetailResp.setModifyTime(LocalDateTime.now());
			ResultDto<?> userResult = memberClient.updateUserByUserId(userDetailResp);
			if(!RESULT_SUCCESS_CODE.equals(userResult.getResult())){
				return userResult;
			}

			Map<String,Integer> map = new HashMap<>();
			Integer authStatus = user.getAuthStatus();
			if(null ==authStatus || 0 == authStatus){
				authStatus = 1;
			}
			map.put("authStatus", 1);
			rtData = new ResultDto<>(ResultCodeEnum.System.SUCCESS, map);
		}else{
			rtData = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
			rtData.setDescription(rlt.getDescription());
		}
		return rtData;
	}

	@Login
	@ApiOperation("获取当前用户信息")
	@GetMapping("/info")
	public Object getCurUserInfo(HttpServletRequest request) {
		try {
			String userId = CommonHelper.getUserIdByRequest();
			logger.info("获取当前用户状态 > > > 入参：{}", userId);
			if (!StringUtils.isBlank(userId)) {
				return memberClient.getCurUserInfo(userId);
			} else {
				return ResultDto.result(ResultCodeEnum.System.ILLEGAL_REQUEST);
			}
		} catch (Exception e) {
			logger.error("获取当前用户状态  > > > 异常：{}", e);
			return ResultDto.errorResult();
		}
	}

	@Login
	@ApiOperation("我的评论查询")
	@PostMapping("/comments")
	@Log
	public Object queryMyComment(HttpServletRequest request, @RequestBody UserPageInfoDto pageInfo) {
		logger.info("我的评论查询 > > > 入参：{}", JsonUtils.toJsonString(pageInfo));
		String userId = CommonHelper.getUserIdByRequest();
		if (StringUtils.isBlank(userId)) {
			return ResultDto.result(ResultCodeEnum.System.ILLEGAL_REQUEST);
		}
		String pageSize = pageInfo.getPageSize();
		String curPage = pageInfo.getCurPage();
		if (StringUtils.isBlank(curPage)) {
			return ResultDto.result(ResultCodeEnum.System.PARAM_NULL);
		}
		return orderService.queryMyComment(userId, curPage, pageSize);
	}

	@Login
	@ApiOperation("修改乐豆开关")
	@PostMapping("/enableHappyCoin")
	public Object updateHappycoin(HttpServletRequest request, @RequestBody UserCoinAndPhotoDto userDto) {
		try {
			logger.info("修改乐豆开关 > > > 入参：{}", JsonUtils.toJsonString(userDto));
			String userId = CommonHelper.getUserIdByRequest();
			if (!StringUtils.isBlank(userId)) {
				UserDetailResp userUpdateDto = new UserDetailResp();
				userUpdateDto.setUserId(userId);
				boolean flag = false;
				if (userDto.getValue() == 1) {
					flag = true;
				}
				userUpdateDto.setEnableHappycoin(flag);
				return memberClient.updateUserByUserId(userUpdateDto);
			} else {
				return ResultDto.result(ResultCodeEnum.System.ILLEGAL_REQUEST);
			}
		} catch (Exception e) {
			logger.error("修改乐豆开关   > > > 异常：{}", e);
			return ResultDto.errorResult();
		}
	}
	
	@Log
	@ApiOperation("强制打开乐豆开关")
	@GetMapping("/openHCSwitch")
	public Object openHCSwitch(@RequestParam("userId") String userId){
		try{
			UserDetailResp userUpdateDto = new UserDetailResp();
			userUpdateDto.setUserId(userId);
			userUpdateDto.setEnableHappycoin(true);
			return memberClient.updateUserByUserId(userUpdateDto);
		}catch(Exception e){
			return ResultDto.errorResult();
		}
	}
}
