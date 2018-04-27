package com.gl365.wxpub.web;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

import com.gl365.wxpub.aspect.Login;
import com.gl365.wxpub.common.CommonHelper;
import com.gl365.wxpub.common.JsonUtils;
import com.gl365.wxpub.dto.PageDto;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.h5.resp.GroupPayAmount;
import com.gl365.wxpub.dto.h5.resp.GroupUserInfo;
import com.gl365.wxpub.dto.member.req.GroupPayDistributeReq;
import com.gl365.wxpub.dto.member.req.GroupPayRedisDto;
import com.gl365.wxpub.dto.member.resp.users.UserPhotoAndName;
import com.gl365.wxpub.dto.merchant.resp.MerchantInfoDto;
import com.gl365.wxpub.dto.order.req.GroupPayInitOrderReq;
import com.gl365.wxpub.dto.order.resp.CreateOrderResp;
import com.gl365.wxpub.dto.order.resp.OrderInfoRltResp;
import com.gl365.wxpub.enums.GroupPayStatus;
import com.gl365.wxpub.enums.GroupStatus;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.handler.ConfigHandler;
import com.gl365.wxpub.service.GroupService;
import com.gl365.wxpub.service.RedisService;
import com.gl365.wxpub.service.FeignCilent.MemberClient;
import com.gl365.wxpub.service.FeignCilent.MerchantClient;
import com.gl365.wxpub.service.FeignCilent.OrderClient;
import com.gl365.wxpub.util.GsonUtils;
import com.gl365.wxpub.util.RedEnvelopesUtils;

import io.swagger.annotations.ApiOperation;

/**
 * 群买单控制器
 * 
 * @author dfs_508 2017年9月5日 下午4:37:32
 */
@CrossOrigin(origins = "*", maxAge = 3600) // 跨域问题，与前端调试时打开
@RestController
@RequestMapping("/wxpubApi/turnTable")
public class TurnTableController {

	private static final Logger LOG = LoggerFactory.getLogger(TurnTableController.class);
	private static final long liveTime = 86400L; // 群买单生成金额缓存时间：30分钟
	private static final String GROUP_PAY_SAVE_MONEY_PREFIX = "GROUP_PAY_SAVE_MONEY_PREFIX";// 群买单缓存前缀
	private static final String GROUP_PAY_SAVE_MONEY_USER_PREFIX = "GROUP_PAY_SAVE_MONEY_USER_PREFIX";// 群买单个人用户缓存前缀

	@Autowired
	private RedisService redisService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private OrderClient orderService;
	@Autowired
	private MerchantClient merchantInfoService;
	@Autowired
	private MemberClient memberService;
	
	@ApiOperation("群买单分配金额")
	@Login
	@PostMapping("/allocationAmount")
	public Object allocationAmount(@RequestBody GroupPayDistributeReq groupPayDistributeReq) {
		String userId = CommonHelper.getUserIdByRequest();
		LOG.info("群买单分配金额 	allocationAmount begin param={},userId={}", JsonUtils.toJsonString(groupPayDistributeReq), userId);
		Long beginTime = System.currentTimeMillis();
		ResultDto<?> rlt = null;
		try {
			String groupId = groupPayDistributeReq.getGroupId();
			//String userId = groupPayDistributeReq.getUserId();
			if (StringUtils.isBlank(groupId) || StringUtils.isBlank(userId)) {
				return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
			}
			// 获取群redis参数
			int allCount = groupService.queryGroupSize(groupId);
			GroupPayAmount groupPayAmount = groupService.queryGroupPayAmount(groupId);
			List<GroupUserInfo> groupUserInfos = groupService.queryGroupInfo(groupId);
			GroupStatus groupStatus = groupService.queryGroupStatus(groupId);// 判断群状态
			if (null == groupPayAmount || null == groupUserInfos || groupUserInfos.isEmpty()) {
				return new ResultDto<>(ResultCodeEnum.GroupPay.GROUP_MEMBER_NOT_EXIST);
			}
			// 只有INIT状态才可分配金额
			if (!GroupStatus.INIT.getStatus().equals(groupStatus.getStatus())) {
				return new ResultDto<>(ResultCodeEnum.System.PARAM_ERROR, groupStatus.getMsg());
			}

			// 只有群主才有权限调用此接口
			String groupOwnerId = groupPayAmount.getGroupOwner();
			if (!userId.equals(groupOwnerId)) {
				return new ResultDto<>(ResultCodeEnum.System.PARAM_ERROR);
			}

			LOG.info("群买单查询参数  allCount={} groupPayAmount={} groupUserInfos={}", allCount,
					JsonUtils.toJsonString(groupPayAmount), JsonUtils.toJsonString(groupUserInfos));
			Map<String, GroupUserInfo> usersMap = groupUserInfos.stream()
					.collect(Collectors.toMap(GroupUserInfo::getUserId, Function.identity()));

			// 设置分配金额参数
			List<String> userIds = new ArrayList<>();
			userIds.addAll(usersMap.keySet());
			BigDecimal totalMoney = null;
			int count = 0;
			boolean claim = false;
			if (null == groupPayAmount.getRlAmount()
					|| groupPayAmount.getRlAmount().compareTo(BigDecimal.valueOf(0)) == 0) {
				// 无认领金额
				totalMoney = groupPayAmount.getTotalAmount();
				count = allCount;
			} else {
				// 有认领金额
				totalMoney = groupPayAmount.getTotalAmount().subtract(groupPayAmount.getRlAmount());
				count = allCount - 1;
				userIds.remove(userId);
				claim = true;
			}
			
			if(count < 2){
				if(claim){
					return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR.getCode(), "乐抢单认领后,最少还需2人参与", null);
				}else{
					return new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR.getCode(), "乐抢单最少2人参与", null);
				}
			}
			// 根据人分配红包
			Map<String, BigDecimal> rltMap = RedEnvelopesUtils.distributionById(totalMoney, count, userIds);
			List<GroupPayRedisDto> value = new ArrayList<>();
			for (String uuid : usersMap.keySet()) {
				GroupUserInfo source = usersMap.get(uuid);
				GroupPayRedisDto target = new GroupPayRedisDto();
				target.setUserId(uuid);
				if (claim && uuid.equals(groupOwnerId)) {
					target.setMoney(groupPayAmount.getRlAmount());
				} else {
					target.setMoney(rltMap.get(uuid));
				}
				target.setImgUrl(source.getImgUrl());
				target.setUserName(source.getUserName());
				if(uuid.equals(groupOwnerId)){
					target.setIsCreate(true);
				}else{
					target.setIsCreate(false);
				}
				target.setReceive(false);
				target.setPayStatus(GroupPayStatus.INIT.getStatus());
				value.add(target);
			}

			String key = GROUP_PAY_SAVE_MONEY_PREFIX + groupId;
			LOG.info("save redis key={},value={},time={}", key, JsonUtils.toJsonString(value), liveTime);
			redisService.set(key, GsonUtils.toJson(value), liveTime);

			// 更新群状态
			groupService.updateGroup(groupId, GroupStatus.BUILD_COMPLETE);

			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS, value);
		} catch (Exception e) {
			LOG.error("群买单分配金额 	allocationAmount exception,e:{}", e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR.getCode(), e.getMessage(), null);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("群买单分配金额 	allocationAmount end rlt={},time={}ms", JsonUtils.toJsonString(rlt),
				(endTime - beginTime));
		return rlt;
	}

	@ApiOperation("群买单领取金额")
	@Login
	@GetMapping("/getMyPayMoney")
	public Object getMyPayMoney(@RequestParam("userId") String userId, @RequestParam("groupId") String groupId) {
		userId = CommonHelper.getUserIdByRequest();
		LOG.info("群买单领取金额 	getMyPayMoney begin userId={},groupId={}", userId, groupId);
		Long beginTime = System.currentTimeMillis();
		ResultDto<?> rlt = null;
		try {
			if (StringUtils.isBlank(userId) || StringUtils.isBlank(groupId)) {
				return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
			}
			String key = GROUP_PAY_SAVE_MONEY_PREFIX + groupId;
			String obj = redisService.get(key);
			GroupStatus groupStatus = groupService.queryGroupStatus(groupId);// 获取群状态
			GroupPayAmount groupPayAmount = groupService.queryGroupPayAmount(groupId);
			LOG.info("cache data:{}", obj);
			if (null == obj || null == groupStatus || null == groupPayAmount) {
				return new ResultDto<>(ResultCodeEnum.GroupPay.GROUP_MEMBER_NOT_EXIST);
			}
			// 只有BUILD_COMPLETE状态才可领取金额,群状态详细信息已经再返回的消息中
			if (!GroupStatus.BUILD_COMPLETE.getStatus().equals(groupStatus.getStatus())) {
				return new ResultDto<>(ResultCodeEnum.System.SUCCESS.getCode(), groupStatus.getMsg(), "GROUP_STATUS_ERROR");
			}
			
			// merchantNo = groupPayAmount.getMerchantNo();//给乐商户号
			
			String orderSn = null;
			//防止并发时无法正确写入状态,新建一个redis存储是否领取
			String reciveKey = GROUP_PAY_SAVE_MONEY_USER_PREFIX+userId+groupId;
			@SuppressWarnings("unchecked")
			List<GroupPayRedisDto> value = GsonUtils.fromJson2Object(obj, List.class, GroupPayRedisDto.class);
			for (GroupPayRedisDto groupPayRedisDto : value) {
				if (userId.equals(groupPayRedisDto.getUserId())) {
					// 防止重复领取
					String jsonStr = redisService.get(reciveKey);
					GroupPayRedisDto redis = GsonUtils.fromJson2Object(jsonStr, GroupPayRedisDto.class) ;
					boolean flag = false;
					if (redis != null) {
						flag = redis.getReceive();
					}
					if (!(groupPayRedisDto.getReceive() || flag)) {
						GroupUserInfo info = groupService.queryGroupUserInfo(groupId, userId);
						groupPayRedisDto.setReceive(true);
						groupPayRedisDto.setPayStatus(info.getPayStatus().getStatus());
						if (null == info.getTotalAmount()
								|| info.getTotalAmount().compareTo(BigDecimal.valueOf(0)) == 0) {
							info.setTotalAmount(groupPayRedisDto.getMoney());
						}
						// 下副单
						if (!userId.equals(groupPayAmount.getGroupOwner())) {
							GroupPayInitOrderReq reqParam = new GroupPayInitOrderReq();
							reqParam.setMemberId(groupPayRedisDto.getUserId());
							reqParam.setMerchantNo(ConfigHandler.getInstance().getGlMerchantNo());
							reqParam.setPaymentConfig(6);// 群买单子单场景码
							reqParam.setGroupId(groupId);// 群号
							reqParam.setTotalAmount(groupPayRedisDto.getMoney());// 用户分配金额
							// 提供新的接口
							 ResultDto<CreateOrderResp> orderRlt = orderService.groupPayInitOrder(reqParam);
							LOG.info("order result reqParam={},orderRlt={}", JsonUtils.toJsonString(reqParam), JsonUtils.toJsonString(orderRlt));
							if (!ResultCodeEnum.System.SUCCESS.getCode().equals(orderRlt.getResult())) {
								return orderRlt;
							}
							orderSn = null == orderRlt.getData() ? null : orderRlt.getData().getOrderSn();
						}
						// 将金额放入redis
						groupService.updateGroupUserInfo(groupId, info);
						LOG.info("save redis key={},value={},time={}", key, JsonUtils.toJsonString(value), liveTime);
						redisService.set(key, GsonUtils.toJson(value), liveTime);

						// 防止并发时无法正确写入状态,新建一个redis存储是否领取
						redisService.set(reciveKey, GsonUtils.toJson(groupPayRedisDto), liveTime);
						break;
					} else {
						return new ResultDto<>(ResultCodeEnum.GroupPay.GROUP_PAY_RECEIVE_AGAIN);
					}
				}
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("turnTableData", value);
			data.put("orderSn", orderSn);
			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS, data);
		} catch (Exception e) {
			LOG.error("群买单领取金额 	getMyPayMoney exception,e:{}", e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("群买单领取金额 	getMyPayMoney end rlt={},time={}ms", JsonUtils.toJsonString(rlt), (endTime - beginTime));
		return rlt;
	}

	@ApiOperation("群买单获取他人领取金额")
	@PostMapping("/getOtherPayMoney")
	public Object getOtherPayMoney(@RequestBody GroupPayDistributeReq groupPayDistributeReq) {
		LOG.info("群买单获取他人领取金额 	getOtherPayMoney begin param={}", JsonUtils.toJsonString(groupPayDistributeReq));
		Long beginTime = System.currentTimeMillis();
		ResultDto<?> rlt = null;
		try {
			String groupId = groupPayDistributeReq.getGroupId();
			if (StringUtils.isBlank(groupId)) {
				return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
			}
			String key = GROUP_PAY_SAVE_MONEY_PREFIX + groupId;
			String obj = redisService.get(key);
			List<GroupUserInfo> groupUserInfos = groupService.queryGroupInfo(groupId);
			LOG.info("群买单查询参数  groupUserInfos={}, obj={}", JsonUtils.toJsonString(groupUserInfos), obj);
			if (null == obj || null == groupUserInfos || groupUserInfos.isEmpty()) {
				return new ResultDto<>(ResultCodeEnum.GroupPay.GROUP_MEMBER_NOT_EXIST);
			}
			Map<String, GroupUserInfo> usersMap = groupUserInfos.stream()
					.collect(Collectors.toMap(GroupUserInfo::getUserId, Function.identity()));
			LOG.info("群买单查询参数  usersMap={}", JsonUtils.toJsonString(usersMap));
			@SuppressWarnings("unchecked")
			List<GroupPayRedisDto> value = GsonUtils.fromJson2Object(obj, List.class, GroupPayRedisDto.class);
			List<GroupPayRedisDto> result = new ArrayList<>();
			for (GroupPayRedisDto groupPayRedisDto : value) {
				GroupUserInfo groupUserInfo = usersMap.get(groupPayRedisDto.getUserId());
				LOG.info("个人信息{},支付状态{}", JsonUtils.toJsonString(groupUserInfo), JsonUtils.toJsonString(groupUserInfo.getPayStatus().getStatus()));
				groupPayRedisDto.setPayStatus(groupUserInfo.getPayStatus().getStatus());
				if (groupPayRedisDto.getReceive()) {
					result.add(groupPayRedisDto);
				}else{
					String userId = groupPayRedisDto.getUserId();
					String reciveKey = GROUP_PAY_SAVE_MONEY_USER_PREFIX+userId+groupId;
					String user = redisService.get(reciveKey);
					LOG.info("判断用户是否未领取={}",JsonUtils.toJsonString(user));
					if(null != user){
						GroupPayRedisDto userRedisInfo = GsonUtils.fromJson2Object(user, GroupPayRedisDto.class);
						groupPayRedisDto.setReceive(userRedisInfo.getReceive());
						result.add(groupPayRedisDto);
					}else{
						LOG.info("群买单获取他人领取金额未领取用户{}", JsonUtils.toJsonString(groupPayRedisDto));
					}
				}
			}
			
			LOG.info("save redis key={},value={},time={}", key, JsonUtils.toJsonString(value), liveTime);
			redisService.set(key, GsonUtils.toJson(value), liveTime);
			
			Map<String, Object> data = new HashMap<String, Object>();
			GroupStatus groupStatus = groupService.queryGroupStatus(groupId);// 获取群状态
			GroupPayAmount groupPayAmount = groupService.queryGroupPayAmount(groupId);
			data.put("turnTableData", result);
			data.put("totalMoney", groupPayAmount.getTotalAmount());
			data.put("groupStatus", null == groupStatus ? "" : groupStatus.getStatus());
			data.put("groupStatusName", null == groupStatus ? "" : groupStatus.getMsg());
			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS, data);
		} catch (Exception e) {
			LOG.error("群买单获取他人领取金额    getOtherPayMoney exception,e:{}", e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("群买单获取他人领取金额 	getOtherPayMoney end rlt={},time={}ms", JsonUtils.toJsonString(rlt),
				(endTime - beginTime));
		return rlt;
	}

	@ApiOperation("首页提示跳转获取群号")
	@Login
	@PostMapping("/getOrderByUserId")
	public Object getOrderByUserId(@RequestBody GroupPayDistributeReq groupPayDistributeReq) {
		String userId = CommonHelper.getUserIdByRequest();
		LOG.info("首页提示跳转获取群号  getOrderByUserId begin param={},userId={}", JsonUtils.toJsonString(groupPayDistributeReq), userId);
		Long beginTime = System.currentTimeMillis();
		ResultDto<?> rlt = null;
		try {
			//String userId = groupPayDistributeReq.getUserId();
			if (StringUtils.isBlank(userId)) {
				return new ResultDto<>(ResultCodeEnum.System.PARAM_NULL);
			}
			//按用户id查询订单     	暂时不变
			ResultDto<OrderInfoRltResp> orderRlt = orderService.getOrderByMember(userId);
			LOG.info("orderService param ={},data={}",userId, JsonUtils.toJsonString(orderRlt));
			if(!ResultCodeEnum.System.SUCCESS.getCode().equals(orderRlt.getResult())){
				return orderRlt;
			}
			OrderInfoRltResp orderInfoRlt = orderRlt.getData();
			String groupId = orderInfoRlt == null ? "" : (orderInfoRlt.getGroupId() == null ? "" : orderInfoRlt.getGroupId());
			GroupPayDistributeReq result= new GroupPayDistributeReq();
			result.setGroupId(groupId);
			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS, result);
		} catch (Exception e) {
			LOG.error("首页提示跳转获取群号  getOrderByUserId exception,e:{}", e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("首页提示跳转获取群号  getOrderByUserId end rlt={},time={}ms", JsonUtils.toJsonString(rlt), (endTime - beginTime));
		return rlt;
	}

	@ApiOperation("获取转盘数据")
	@Login
	@PostMapping("/getTurnTableData")
	public Object getTurnTableData(@RequestBody GroupPayDistributeReq groupPayDistributeReq) {
		String userId = CommonHelper.getUserIdByRequest();
		LOG.info("获取转盘数据  getTurnTableData begin param={},userId={}", JsonUtils.toJsonString(groupPayDistributeReq),userId);
		Long beginTime = System.currentTimeMillis();
		ResultDto<?> rlt = null;
		try {
			String groupId = groupPayDistributeReq.getGroupId();
			//String userId = groupPayDistributeReq.getUserId();
			if (StringUtils.isBlank(groupId) || StringUtils.isBlank(userId)) {
				return ResultDto.result(ResultCodeEnum.System.PARAM_NULL);
			}
			List<GroupUserInfo> groupUserInfos = groupService.queryGroupInfo(groupId);
			String key = GROUP_PAY_SAVE_MONEY_PREFIX + groupId;
			String obj = redisService.get(key);
			LOG.info("群买单查询参数  groupUserInfos={}, obj={}", JsonUtils.toJsonString(groupUserInfos), obj);
			if (null == obj || null == groupUserInfos || groupUserInfos.isEmpty()) {
				return new ResultDto<>(ResultCodeEnum.GroupPay.GROUP_MEMBER_NOT_EXIST);
			}
			
			//更新群状态
			Map<String, GroupUserInfo> usersMap = groupUserInfos.stream().collect(Collectors.toMap(GroupUserInfo::getUserId, Function.identity()));
			@SuppressWarnings("unchecked")
			List<GroupPayRedisDto> value = GsonUtils.fromJson2Object(obj, List.class, GroupPayRedisDto.class);
			for (GroupPayRedisDto groupPayRedisDto : value) {
				String curUserId = groupPayRedisDto.getUserId();
				String reciveKey = GROUP_PAY_SAVE_MONEY_USER_PREFIX + curUserId + groupId;
				String user = redisService.get(reciveKey);
				LOG.info("判断用户是否真的未领取={}", JsonUtils.toJsonString(user));
				if (null != user) {
					GroupPayRedisDto userRedisInfo = GsonUtils.fromJson2Object(user, GroupPayRedisDto.class);
					groupPayRedisDto.setReceive(userRedisInfo.getReceive());
				} else {
					LOG.info("获取转盘数据未领取用户{}", JsonUtils.toJsonString(groupPayRedisDto));
				}

				if (groupPayRedisDto.getReceive()) {
					GroupUserInfo groupUserInfo = usersMap.get(groupPayRedisDto.getUserId());
					groupPayRedisDto.setPayStatus(groupUserInfo.getPayStatus().getStatus());
				}
			}
			
			Map<String, Object> data = new HashMap<String, Object>();
			GroupStatus groupStatus = groupService.queryGroupStatus(groupId);// 获取群状态
			GroupPayAmount groupPayAmount = groupService.queryGroupPayAmount(groupId);
			data.put("turnTableData", value);
			data.put("totalMoney", groupPayAmount.getTotalAmount());
			data.put("groupStatus", null == groupStatus ? "" : groupStatus.getStatus());
			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS, data);
		} catch (Exception e) {
			LOG.error("获取转盘数据  getTurnTableData exception,e:{}", e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("获取转盘数据  getTurnTableData end rlt={},time={}ms", JsonUtils.toJsonString(rlt), (endTime - beginTime));
		return rlt;
	}

	@ApiOperation("群买单记录")
	@Login
	@GetMapping("/getTurnTableData")
	public Object getGroupPayData(@RequestParam("userId") String userId, @RequestParam("isSource") Boolean isSource, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
		userId = CommonHelper.getUserIdByRequest();
		LOG.info("群买单记录  getGroupPayData begin userId={},isSource={},pageNo={},pageSize={}", userId, isSource, pageNo, pageSize);
		Long beginTime = System.currentTimeMillis();
		ResultDto<?> rlt = null;
		try {
			if (StringUtils.isBlank(userId) || null == isSource || null == pageNo || null == pageSize) {
				return ResultDto.result(ResultCodeEnum.System.PARAM_NULL);
			}
			PageDto<OrderInfoRltResp> data = null;
			if(isSource){
				data = orderService.getByOrderMainMemberId(userId, pageNo, pageSize);
			}else{
				data = orderService.getByOrderSonMemberId(userId, pageNo, pageSize);
			}
			LOG.info("orderService result{}", JsonUtils.toJsonString(data));
			// 组装商家头像名称
			List<OrderInfoRltResp> orderInfoRltList = null;
			if(null != data && null != data.getList() && data.getList().size()>0){
				List<String> merchantNoList = new ArrayList<>();
				orderInfoRltList = data.getList();
				for (OrderInfoRltResp orderInfoRlt : orderInfoRltList) {
					if(StringUtils.isNotBlank(orderInfoRlt.getMerchantNo()) && (!merchantNoList.contains(orderInfoRlt.getMerchantNo()))){
						merchantNoList.add(orderInfoRlt.getMerchantNo());
					}
				}
				List<MerchantInfoDto> merchantInfoList = merchantInfoService.getMerchantByMerchantNoList(merchantNoList);
				LOG.info("merchantInfoService result{}", JsonUtils.toJsonString(merchantInfoList));
				Map<String, MerchantInfoDto> map = merchantInfoList.stream().collect(Collectors.toMap(MerchantInfoDto::getMerchantNo, Function.identity()));
				for (OrderInfoRltResp result : orderInfoRltList) {
					if(map.containsKey(result.getMerchantNo())){
						MerchantInfoDto merchant = map.get(result.getMerchantNo());
						result.setMerchantMainImage(merchant.getMainImage());
						result.setMerchantName(merchant.getMerchantShortname());
						LocalDateTime payTime = result.getCreateTime();
						if(payTime != null){
							String m = String.valueOf(payTime.getMonthValue()).length()>1 ? String.valueOf(payTime.getMonthValue()) : "0"+String.valueOf(payTime.getMonthValue());
							String d = String.valueOf(payTime.getDayOfMonth()).length()>1 ? String.valueOf(payTime.getDayOfMonth()) : "0"+String.valueOf(payTime.getDayOfMonth()); 
							result.setOrderTime(m+"/"+d);
						}else{
							result.setOrderTime("-");
						}
					}else{
						LOG.info("Can't find merchanNo {}", result.getMerchantNo());
					}
				}
			}
			data.setList(orderInfoRltList);
			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS, data);
		} catch (Exception e) {
			LOG.error("群买单记录  getGroupPayData exception,e:{}", e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("群买单记录  getGroupPayData end rlt={},time={}ms", JsonUtils.toJsonString(rlt), (endTime - beginTime));
		return rlt;
	}

	@Login
	@ApiOperation("查询是否全部撤单完成")
	@PostMapping("/getCancelOrderStatus")
	public Object getCancelOrderStatus(@RequestBody GroupPayDistributeReq groupPayDistributeReq) {
		String loginUserId = CommonHelper.getUserIdByRequest();
		LOG.info("查询是否全部撤单完成  getCancelOrderStatus begin loginUserId={} param={}", loginUserId, JsonUtils.toJsonString(groupPayDistributeReq));
		Long beginTime = System.currentTimeMillis();
		Object rlt = null;
		try {
			if (StringUtils.isEmpty(loginUserId)){
				return new ResultDto<>(ResultCodeEnum.System.ILLEGAL_REQUEST);
			}
			String groupId = groupPayDistributeReq.getGroupId();
			if (StringUtils.isBlank(groupId)) {
				return ResultDto.result(ResultCodeEnum.System.PARAM_NULL);
			}
			Map<String,Boolean> rltMap = new HashMap<>();
			rltMap.put("isAllCancelOrder", false);
			String orderStatus = "1,8";//存在订单状态 0和8时表示没有全部撤单
			PageDto<OrderInfoRltResp> data = orderService.getByOrderGroupIdCancelOrder(groupId, orderStatus, 1, 2000);
			LOG.info("getCancelOrderStatus param={},rlt={}", groupId, JsonUtils.toJsonString(data));
			if(null != data && data.getTotalCount() != null && data.getTotalCount() == 0){
				rltMap.put("isAllCancelOrder", true);
			}
			rlt = new ResultDto<>(ResultCodeEnum.System.SUCCESS, rltMap);
		} catch (Exception e) {
			LOG.error("查询是否全部撤单完成  getCancelOrderStatus exception,e:{}", e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("查询是否全部撤单完成  getCancelOrderStatus end rlt={},time={}ms", JsonUtils.toJsonString(rlt), (endTime - beginTime));
		return rlt;
	}
	
	
	@ApiOperation("按用户id查询群买单子订单只查交易成功的,手动传userId")
	@PostMapping("/getByOrderGroupIdAndUserId")
	public Object getByOrderGroupIdAndToken(@RequestBody GroupPayDistributeReq groupPayDistributeReq) {
		return getByOrderGroupId(groupPayDistributeReq);
	}
	
	@Login
	@ApiOperation("按用户id查询群买单子订单只查交易成功的,自动获取userId")
	@PostMapping("/getByOrderGroupId")
	public Object getByOrderGroupIdAndUserId(@RequestBody GroupPayDistributeReq groupPayDistributeReq) {
		groupPayDistributeReq.setUserId(CommonHelper.getUserIdByRequest());
		return getByOrderGroupId(groupPayDistributeReq);
	}

	public Object getByOrderGroupId(GroupPayDistributeReq groupPayDistributeReq) {
		LOG.info("按用户id查询群买单子订单只查交易成功或撤单的  getByOrderGroupId begin param={}", JsonUtils.toJsonString(groupPayDistributeReq));
		Long beginTime = System.currentTimeMillis();
		Object rlt = null;
		try {
			String groupId = groupPayDistributeReq.getGroupId();
			String userId = groupPayDistributeReq.getUserId();
			if (StringUtils.isBlank(groupId) || StringUtils.isBlank(userId)) {
				return ResultDto.result(ResultCodeEnum.System.PARAM_NULL);
			}
			
			//若超过2000人玩群买单 
			//PageDto<OrderInfoRltResp> data = orderService.getByOrderGroupId(groupId, 1, 2000);
			String orderStatus = "1";//订单状态 0未付款，1完成付款，2付款中，3撤销，4冲正,5退货，7超时作废,9付款失败
			if("5".equals(groupPayDistributeReq.getOrderStatus())){
				orderStatus = "5,7";
			}
			PageDto<OrderInfoRltResp> data = orderService.getByOrderGroupIdCancelOrder(groupId, orderStatus, 1, 2000);
			
			rlt = handleOrderData(new ResultDto<>(ResultCodeEnum.System.SUCCESS, data), userId);
		} catch (Exception e) {
			LOG.error("按用户id查询群买单子订单只查交易成功的  getByOrderGroupId exception,e:{}", e);
			rlt = new ResultDto<>(ResultCodeEnum.System.SYSTEM_ERROR);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("按用户id查询群买单子订单只查交易成功的  getByOrderGroupId end rlt={},time={}ms", JsonUtils.toJsonString(rlt), (endTime - beginTime));
		return rlt;
	}

	private Object handleOrderData(ResultDto<PageDto<OrderInfoRltResp>> result, String loginUserId) {
		Map<String, Object> rltMap = new HashMap<>();
		rltMap.put("result", result.getResult());
		rltMap.put("description", result.getDescription());

		PageDto<OrderInfoRltResp> source = result.getData();
		if (source == null || source.getTotalCount() < 1) {
			rltMap.put("data", new ArrayList<>());
			return rltMap;
		}
		List<OrderInfoRltResp> orderInfoRltList = new ArrayList<>();
		orderInfoRltList = source.getList();
		LOG.info("分页列表数据{}", JsonUtils.toJsonString(orderInfoRltList));

		// 组装个人支付总金额,乐豆支付,现金支付,赠送乐豆
		BigDecimal otherMoney = BigDecimal.valueOf(0);
		BigDecimal totalAmount = BigDecimal.valueOf(0);

		// 获取主单信息,并且组装子单金额
		Map<String, OrderInfoRltResp> mapRlt = new HashMap<>();
		//获取头像
		List<String> userIds = new ArrayList<>();// 为获取头像url
		
		for (OrderInfoRltResp orderInfoRlt : orderInfoRltList) {
			if (5 == orderInfoRlt.getPaymentConfig()) {
				totalAmount = orderInfoRlt.getTotalAmount();
				rltMap.put("createBean", orderInfoRlt.getBeanAmount());
				rltMap.put("createCash", orderInfoRlt.getCashAmount());
				rltMap.put("createGift", orderInfoRlt.getGiftAmount());
				rltMap.put("orderSn", orderInfoRlt.getOrderSn());
				rltMap.put("paymentTime", orderInfoRlt.getCreateTime() != null
						? orderInfoRlt.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
						: "-");
				rltMap.put("isCreate", false);
				if (loginUserId.equals(orderInfoRlt.getMemberId())) {
					rltMap.put("isCreate", true);
				}
				rltMap.put("merchantNo", orderInfoRlt.getMerchantNo());
			} else {
				if (null != orderInfoRlt.getTotalAmount()) {
					otherMoney = otherMoney.add(orderInfoRlt.getTotalAmount());
				}
			}
			String userId = orderInfoRlt.getMemberId();
			if (StringUtils.isNotBlank(userId)) {
				if(!userIds.contains(userId)){
					userIds.add(userId);
				}
				mapRlt.put(userId, orderInfoRlt);
			}else{
				LOG.info("订单结果中无payOrganId={}", JsonUtils.toJsonString(orderInfoRlt));
			}
		}
		
		// 组装主单支付信息
		BigDecimal createMoney = totalAmount.subtract(otherMoney);
		rltMap.put("totalAmount", totalAmount);
		rltMap.put("createMoney", createMoney);

		LOG.info("子单金额乐豆之和  otherMoney={}", JsonUtils.toJsonString(otherMoney));
		// 获取头像url,若是查询不匹配数据异常
		List<UserPhotoAndName> userList = memberService.getUserPhotoAndName(userIds);
		LOG.info("userList userIds={}, rlt={}", JsonUtils.toJsonString(userIds), JsonUtils.toJsonString(userList));
		Map<String, UserPhotoAndName> mapUserRlt = new HashMap<>();
		if(!CollectionUtils.isEmpty(userList)){
			 mapUserRlt = userList.stream().collect(Collectors.toMap(UserPhotoAndName::getUserId, Function.identity()));
		}
		// 组装返回页面数据List
		List<OrderInfoRltResp> rlt = new ArrayList<>();
		for (OrderInfoRltResp orderInfoRltResp : orderInfoRltList) {
			String userId = orderInfoRltResp.getMemberId();
			if (StringUtils.isNotBlank(userId)) {
				if (mapUserRlt.containsKey(userId)) {
					UserPhotoAndName userPhotoAndName = mapUserRlt.get(userId);
					String photo = StringUtils.isBlank(userPhotoAndName.getWxPhoto()) ? userPhotoAndName.getAppPhoto() : userPhotoAndName.getWxPhoto();
					String nickName = StringUtils.isBlank(userPhotoAndName.getWxNickName()) ? userPhotoAndName.getAppNickName() : userPhotoAndName.getWxNickName();;
					orderInfoRltResp.setImgUrl(photo);
					orderInfoRltResp.setNickName(nickName);
				}
				rlt.add(orderInfoRltResp);
			}
		}

		source.setList(rlt);
		rltMap.put("data", source);
		LOG.info("rlt={}", JsonUtils.toJsonString(rltMap));
		return rltMap;
	}

}
