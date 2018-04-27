package com.gl365.wxpub.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl365.wxpub.dto.DrowPrizeDto;
import com.gl365.wxpub.dto.LotteryDto;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.h5.resp.FlowsResp;
import com.gl365.wxpub.dto.member.resp.ActiveMerchantInfoResp;
import com.gl365.wxpub.dto.member.resp.ActiveMerchantResp;
import com.gl365.wxpub.dto.member.resp.UserBindInfo;
import com.gl365.wxpub.dto.merchant.req.MerchantActivityReq;
import com.gl365.wxpub.dto.promotion.req.DrawLotteryReq;
import com.gl365.wxpub.dto.promotion.req.PromotionUnionLoginReq;
import com.gl365.wxpub.dto.promotion.resp.DrawLotteryResp;
import com.gl365.wxpub.dto.promotion.resp.FlowsDto;
import com.gl365.wxpub.dto.promotion.resp.PromotionUnionLoginResp;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.service.LotteryService;
import com.gl365.wxpub.service.PromotionService;
import com.gl365.wxpub.service.FeignCilent.PromotionClient;
import com.gl365.wxpub.util.AmountTransferUtils;
import com.gl365.wxpub.util.GsonUtils;

@Service
public class PromotionServiceImpl implements PromotionService{
	
	private static final Logger logger = LoggerFactory.getLogger(PromotionServiceImpl.class);
	
	@Autowired
	private PromotionClient promotionClient;
	
	@Autowired
	private LotteryService lotteryService;

	@Override
	public ResultDto<?> getPrizeList(String activityId) {
		logger.info("getPrizeList begin,activityId:{}",activityId);
		ResultDto<?> resp = null;
		try{
			resp = promotionClient.queryPrizeList(activityId);
			logger.info("getPrizeList ===> promotionClient.queryPrizeList rlt:{}",GsonUtils.toJson(resp));
			//后续前端如果需要从后台取奖品列表再优化  --20171105
			if(!ResultCodeEnum.System.SUCCESS.getCode().equals(resp.getResult())){
				resp.setResult(ResultCodeEnum.System.SUCCESS.getCode());
			}
		}catch(Exception e){
			logger.error("getPrizeList ===> promotionClient.queryPrizeList exception:",e);
			resp = ResultDto.result(ResultCodeEnum.System.SUCCESS,new ArrayList<>());
		}
		logger.info("getPrizeList end,resp:{}",GsonUtils.toJson(resp));
		return resp;
	}

	@Override
	public ResultDto<?> getFlowsList(String activityId) {
		logger.info("getFlowsList begin,activityId:{}",activityId);
		ResultDto<List<FlowsDto>> rlt = null;
		try{
			rlt = promotionClient.queryLastFlows(activityId, 10);
			logger.info("getFlowsList ===> promotionClient.queryLastFlows rlt:{}",GsonUtils.toJson(rlt));
		}catch(Exception e){
			logger.error("getFlowsList ===> promotionClient.queryLastFlows exception:",e);
		}
		if(rlt ==null || !ResultCodeEnum.System.SUCCESS.getCode().equals(rlt.getResult()) && rlt.getData() == null){
			return ResultDto.result(ResultCodeEnum.System.SUCCESS,new ArrayList<>());
		}
		List<FlowsResp> data = bulidFlowsResp(rlt.getData());
		logger.info("getFlowsList end,FlowsList:{}",GsonUtils.toJson(data));
		return ResultDto.result(ResultCodeEnum.System.SUCCESS,data);
	}
	
	private List<FlowsResp> bulidFlowsResp(List<FlowsDto> fdList){
		List<FlowsResp> data = new ArrayList<>();
		for (FlowsDto fd : fdList) {
			FlowsResp fr = new FlowsResp();
			fr.setMobileNo(fd.getPhone());
			fr.setPrizeName(fd.getPrizeName());
			fr.setPrizeTypeDesc(fd.getPrizeTypeDesc());
			data.add(fr);
		}
		return data;
	}

	@Override
	public ResultDto<?> drawLottery(DrawLotteryReq req) {
		logger.info("drawLottery begin,req:{}",req.toString());
		ResultDto<DrawLotteryResp> rlt = null;
		try{
			rlt = promotionClient.drawLottery(req);
			logger.info("drawLottery ===> promotionClient.drawLottery rlt:{}",GsonUtils.toJson(rlt));
		}catch(Exception e){
			logger.error("drawLottery ===> promotionClient.drawLottery exception:",e);
		}
		if(null == rlt || !ResultCodeEnum.System.SUCCESS.getCode().equals(rlt.getResult()) || rlt.getData() == null){
			return ResultDto.drawLotteryRltConvert(rlt);
		}
		DrowPrizeDto data = new DrowPrizeDto();
		BeanUtils.copyProperties(rlt.getData(), data);
		data.setMobileNo(req.getPhone());
		logger.info("drawLottery end,data:{}",data.toString());
		return ResultDto.result(ResultCodeEnum.System.SUCCESS,data);
	}

	@Override
	public ActiveMerchantResp isActivity(MerchantActivityReq req, int bindFlag,String source) {
		logger.info("isActivity begin,req:{},bindFlag:{},source:{}",req,bindFlag,source);
		ResultDto<ActiveMerchantInfoResp> resp = null;
		ActiveMerchantResp data = new ActiveMerchantResp();
		try{
			resp = promotionClient.getActivityInfoByMerchantNo(req.getMerchantNo());
			logger.info("isActivity ===> promotionClient.getActivityInfoByMerchantNo success,resp:{}",resp.toString());
		}catch(Exception e){
			logger.error("isActivity ===> promotionClient.getActivityInfoByMerchantNo exception,e:",e);
		}
		
		if(null == resp || (!ResultCodeEnum.System.SUCCESS.getCode().equals(resp.getResult())) || null == resp.getData()){
			data.setActiveMerchant(false);
			return data;
		}
		if(isPermisActivity(req, resp.getData(), bindFlag, source)){
			data.setActiveBeginTime(handleTime(resp.getData().getBeginTime()));
			data.setActiveEndTime(handleTime(resp.getData().getEndTime()));
			data.setActiveMerchant(true);
			data.setActiveId(resp.getData().getActivityId());
		}else{
			data.setActiveMerchant(false);
		}
		return data;
	}
	
	/**
	 * 判断用户是否有权限参与活动
	 * @param req
	 * @param ami
	 * @param bindFlag 0:未绑定，1：已绑定
	 * @return
	 */
	private boolean isPermisActivity(MerchantActivityReq req, ActiveMerchantInfoResp ami, int bindFlag, String source){
		//20171121 增加判断支付金额是否大于等于参与活动的最低金额
		if(AmountTransferUtils.compareTo(req.getTotalAmout(), ami.getLeastPrice()) >= 0){
			//20171206 增加判断用户微信号是否已绑定 
			if(1 == bindFlag){
				LotteryDto ldto = lotteryService.getLotteryFromRedis(req.getOpenId(), ami.getActivityId(), source);
				if(null != ldto && ldto.isDraw()){
					logger.info("isPermisActivity 该用户已参与过该抽奖活动,ldto:{}",ldto.toString());
					return false;
				}
				if(null == ldto){
					//初始化抽奖实体到redis
					saveLottery2Redis(req, ami, source);
				}
			}
			return true;
		}else{
			logger.info("isPermisActivity 用户支付金额小于参与活动的最低金额，无法参与活动");
			return false;
		}		
	}
	
	private void saveLottery2Redis(MerchantActivityReq req,ActiveMerchantInfoResp ami ,String source){
		LotteryDto dto = new LotteryDto();
		dto.setOpenId(req.getOpenId());
		dto.setMobileNo(req.getMobileNo());
		dto.setActivityId(ami.getActivityId());
		dto.setMerchantNo(req.getMerchantNo());
		dto.setUserId(req.getUserId());
		dto.setDraw(false);
		dto.setBeginTime(ami.getBeginTime());
		dto.setEndTime(ami.getEndTime());
		lotteryService.savaLottery2Redis(dto,source);
	}
	
	private String handleTime(String time){
		if(StringUtils.isBlank(time)){
			return time;
		}
		if(time.length() > 9){
			time = time.substring(0, 10).replaceAll("-", "/");
		}
		return time;
	}

	@Override
	public String unionLogin(UserBindInfo ubi, String redirectUrl, String urlType) {
		PromotionUnionLoginReq req = buildPromotionUnionLoginReq(ubi, redirectUrl, urlType);
		logger.info("unionLogin begin,req:{}",req);
		ResultDto<PromotionUnionLoginResp> rlt = null;
		try{
			rlt = promotionClient.unionLogin(req);
			logger.info("unionLogin ===> promotionClient.unionLogin rlt:{}",GsonUtils.toJson(rlt));
			
		}catch(Exception e){
			logger.error("promotionClient.unionLogin exception,e:",e);
			return null;
		}
		if(null != rlt && null != rlt.getData()){
			return rlt.getData().getRedirectUrl();
		}else{
			return null;
		}
	}
	
	private PromotionUnionLoginReq buildPromotionUnionLoginReq(UserBindInfo ubi, String redirectUrl, String urlType){
		PromotionUnionLoginReq req = new PromotionUnionLoginReq();
		req.setCredits(ubi.getAmount());
		req.setRedirectUrl(redirectUrl);
		req.setUid(ubi.getUserId());
		req.setUrlType(urlType);
		return req;
	}

}
