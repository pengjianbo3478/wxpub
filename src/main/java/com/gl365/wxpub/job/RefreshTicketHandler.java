package com.gl365.wxpub.job;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gl365.job.core.biz.model.ReturnT;
import com.gl365.job.core.handler.IJobHandler;
import com.gl365.job.core.handler.annotation.JobHander;
import com.gl365.job.core.log.JobLogger;
import com.gl365.wxpub.common.Constant;
import com.gl365.wxpub.dto.WxApiAccessToken;
import com.gl365.wxpub.dto.WxJsApiTicket;
import com.gl365.wxpub.service.RedisService;
import com.gl365.wxpub.service.WeixinService;
import com.gl365.wxpub.util.GsonUtils;

@JobHander(value = "refreshTicketHandler")
@Service
public class RefreshTicketHandler extends IJobHandler{

	@Resource
	private WeixinService weixinService;

	@Resource
	private RedisService redisService;
	
	@Override
	public ReturnT<String> execute(String... arg0) throws Exception {
		JobLogger.log("=======>JOB, refreshTicketHandler.>>>>>>>>>>>>>>begin>>>>>>>>>>>>>>");
		String redisToken = redisService.get(Constant.WXAPI_ACCESSTOKEN_KEY);
		if(StringUtils.isBlank(redisToken)){
			JobLogger.log("refreshTicketHandler ===> redisService.getAccessToken fail,accessToken is null");
			return ReturnT.FAIL;
		}
		
		WxApiAccessToken token = GsonUtils.fromJson2Object(redisToken, WxApiAccessToken.class);
		WxJsApiTicket ticket = weixinService.getJsTicket(token.getToken());
		if(null == ticket){
			JobLogger.log("refreshTicketHandler ===> weixinService.getJsTicket fail,ticket is null");
			return ReturnT.FAIL;
		}
		
		JobLogger.log("refreshTicketHandler ===> weixinService.getJsTicket success,ticket:{0}",ticket.toString());
		redisService.set(Constant.WXJSAPI_TICKET_KEY, GsonUtils.toJson(ticket), 7200L);
		
		JobLogger.log("refreshTicketHandler ===> redisService.getJsTicket rlt:{0}",redisService.get(Constant.WXJSAPI_TICKET_KEY));
		
		JobLogger.log("=======>JOB, refreshTicketHandler.>>>>>>>>>>>>>>end>>>>>>>>>>>>>>");
		return ReturnT.SUCCESS;
	}

}
