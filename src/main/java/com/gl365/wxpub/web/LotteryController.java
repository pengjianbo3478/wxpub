package com.gl365.wxpub.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl365.wxpub.aspect.Log;
import com.gl365.wxpub.aspect.Login;
import com.gl365.wxpub.common.CommonHelper;
import com.gl365.wxpub.common.Constant;
import com.gl365.wxpub.dto.LotteryDto;
import com.gl365.wxpub.dto.DrowPrizeDto;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.promotion.req.DrawLotteryReq;
import com.gl365.wxpub.enums.LotteryStatus;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.service.LotteryService;
import com.gl365.wxpub.service.PromotionService;
import com.gl365.wxpub.util.GsonUtils;

@CrossOrigin(origins = "*", maxAge = 3600) // 跨域问题，与前端调试时打开
@RestController
@RequestMapping("/wxpubApi/lottery")
public class LotteryController {
	
	private static final Logger logger = LoggerFactory.getLogger(LotteryController.class);
	
	@Autowired
	private LotteryService lotteryService;
	
	@Autowired
	private PromotionService promotionService;

	@Login
	@Log("查询抽奖状态")
	@GetMapping("/queryStatus")
	public Object queryStatus(@RequestParam("activityId") String activityId) {
		String openId = (String) CommonHelper.getRequest().getSession().getAttribute(Constant.GL_WXPUB_OPENID);
		int bindFlag = (int)CommonHelper.getRequest().getSession().getAttribute(Constant.GL_WXPUB_BINDFLAG);
		String source = "1"; 
		//20171206新增判断用户微信号是否已绑定
		if(0 == bindFlag){
			logger.info("queryStatus 用户微信号未绑定，openId:{}",openId);
			return ResultDto.result(ResultCodeEnum.Lottery.USER_HAS_NOT_BIND);
		}
		
		LotteryDto ldto = lotteryService.getLotteryFromRedis(openId, activityId, source);
		if(null == ldto){
			source = "2";
			ldto = lotteryService.getLotteryFromRedis(openId, activityId, source);
		}
		logger.info("queryStatus source:{},ldto:{}",source,ldto);
		LotteryStatus status = vertifyPermis(ldto);
		ResultDto<?> resp = null;
		switch (status) {
		case NO_PERMIS:
			// 没有抽奖权限
			resp = ResultDto.result(ResultCodeEnum.Lottery.NO_PERMIS);
			break;
		case WEI_CHOU_JIANG:
			// 未抽奖
//			resp = promotionService.getPrizeList(activityId);
			resp = ResultDto.result(ResultCodeEnum.System.SUCCESS, source);
			break;
		case YI_CHOU_JIANG:
			// 已抽过奖
			DrowPrizeDto prize = new DrowPrizeDto();
			BeanUtils.copyProperties(ldto, prize);
			resp = ResultDto.result(ResultCodeEnum.Lottery.HAS_DRAW_PRIZE, prize);
			break;
		default:
			break;
		}
		return resp;
	}
	
	@Login
	@Log("查询中奖列表")
	@GetMapping("/flowsList")
	public Object flowsList(@RequestParam("activityId") String activityId) {
		return promotionService.getFlowsList(activityId);
	}
	
	@Login
	@Log("活动抽奖")
	@GetMapping("/drawPrize")
	public Object drawPrize(@RequestParam("activityId") String activityId, @RequestParam("source") String source) {
		String openId = (String) CommonHelper.getRequest().getSession().getAttribute(Constant.GL_WXPUB_OPENID);
		LotteryDto ldto = lotteryService.getLotteryFromRedis(openId, activityId, source);
		LotteryStatus status = vertifyPermis(ldto);
		ResultDto<?> resp = null;
		logger.info("drawPrize ========= 1、判断用户抽奖状态 ============>");
		switch (status) {
		case NO_PERMIS:
			logger.info("该用户没有抽奖权限");
			resp = ResultDto.result(ResultCodeEnum.Lottery.NO_PERMIS);
			break;
		case WEI_CHOU_JIANG:
			logger.info("drawPrize ========= 2、开始抽奖 ============>");
			DrawLotteryReq req = buildDrawLotteryReq(ldto);
			resp = promotionService.drawLottery(req);
			if(ResultCodeEnum.System.SUCCESS.getCode().equals(resp.getResult())){
				logger.info("drawPrize ========= 2、抽奖成功，更新redis抽奖状态和奖品 ============>");
				updateLotteryDto(buildLotteryDto(ldto, resp),source);
			}
			break;
		case YI_CHOU_JIANG:
			logger.info("该用户已抽过奖");
			DrowPrizeDto prize = new DrowPrizeDto();
			BeanUtils.copyProperties(ldto, prize);
			resp = ResultDto.result(ResultCodeEnum.Lottery.HAS_DRAW_PRIZE, prize);
			break;
		default:
			break;
		}
		return resp;
	}
	
	private LotteryStatus vertifyPermis(LotteryDto ldto){
		logger.info("vertifyPermis LotteryDto:{}",GsonUtils.toJson(ldto));
		if(ldto == null){
			return LotteryStatus.NO_PERMIS;
		}
		if(ldto.isDraw()){
			return LotteryStatus.YI_CHOU_JIANG;
		}else{
			return LotteryStatus.WEI_CHOU_JIANG;
		}
	}
	
	private DrawLotteryReq buildDrawLotteryReq(LotteryDto ldto){
		DrawLotteryReq req = new DrawLotteryReq();
		BeanUtils.copyProperties(ldto, req);
		req.setPhone(ldto.getMobileNo());
		return req;
	}
	
	private LotteryDto buildLotteryDto(LotteryDto source,ResultDto<?> resp){
		LotteryDto ldto = new LotteryDto();
		BeanUtils.copyProperties(source, ldto);
		DrowPrizeDto dp = (DrowPrizeDto)resp.getData();
		ldto.setDraw(true);
		ldto.setPrizeId(dp.getPrizeId());
		ldto.setPrizeName(dp.getPrizeName());
		ldto.setPrizeTypeDesc(dp.getPrizeTypeDesc());
		ldto.setWinText(dp.getWinText());
		ldto.setFlowId(dp.getFlowId());
		return ldto;
	}
	
	/**
	 * 抽奖成功后更新redis抽奖状态和奖品
	 * @param ldto
	 */
	private void updateLotteryDto(LotteryDto ldto,String source){
		logger.info("updateLotteryDto begin,ldto:{}",ldto.toString());
		lotteryService.savaLottery2Redis(ldto,source);
	}

}
