package com.gl365.wxpub.service.FeignCilent;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.h5.PrizeDto;
import com.gl365.wxpub.dto.member.resp.ActiveMerchantInfoResp;
import com.gl365.wxpub.dto.promotion.req.DrawLotteryReq;
import com.gl365.wxpub.dto.promotion.req.PromotionUnionLoginReq;
import com.gl365.wxpub.dto.promotion.resp.DrawLotteryResp;
import com.gl365.wxpub.dto.promotion.resp.FlowsDto;
import com.gl365.wxpub.dto.promotion.resp.PromotionUnionLoginResp;

@FeignClient(name="promotion",url="${${env:}.url.promotion:}")
public interface PromotionClient {

	@RequestMapping(value = "/prize/activityInfo", method = RequestMethod.GET)
	public ResultDto<ActiveMerchantInfoResp> getActivityInfoByMerchantNo(@RequestParam("merchantNo") String merchantNo);
	
	@RequestMapping(value = "/prize/listPrize", method = RequestMethod.GET)
	public ResultDto<List<PrizeDto>> queryPrizeList(@RequestParam("activityId") String activityId);
	
	@RequestMapping(value = "/prize/lastFlows", method = RequestMethod.GET)
	public ResultDto<List<FlowsDto>> queryLastFlows(@RequestParam("activityId") String activityId,@RequestParam("num") int num);

	@RequestMapping(value = "/prize/drawLottery", method = RequestMethod.POST) 
	public ResultDto<DrawLotteryResp> drawLottery(@RequestBody DrawLotteryReq req);
	
	@RequestMapping(value = "/nologin/convertUrl", method = RequestMethod.POST) 
	public ResultDto<PromotionUnionLoginResp> unionLogin(@RequestBody PromotionUnionLoginReq req);
}
