package com.gl365.wxpub.job;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gl365.job.core.biz.model.ReturnT;
import com.gl365.job.core.handler.IJobHandler;
import com.gl365.job.core.handler.annotation.JobHander;
import com.gl365.job.core.log.JobLogger;
import com.gl365.wxpub.common.Constant;
import com.gl365.wxpub.dto.WxApiAccessToken;
import com.gl365.wxpub.service.RedisService;
import com.gl365.wxpub.service.WeixinService;
import com.gl365.wxpub.util.GsonUtils;

@JobHander(value = "refreshAccessTokenHandler")
@Service
public class RefreshAccessTokenHandler extends IJobHandler{
	
	@Resource
	private WeixinService weixinService;

	@Resource
	private RedisService redisService;
	
	@Override
	public ReturnT<String> execute(String... arg0) throws Exception {
		JobLogger.log("=======>JOB, refreshAccessTokenHandler.>>>>>>>>>>>>>>begin>>>>>>>>>>>>>>");
		WxApiAccessToken accessToken = weixinService.getAccessToken();
		if(null == accessToken){
			JobLogger.log("refreshAccessTokenHandler ===> weixinService.getAccessToken fail,accessToken is null");
			return ReturnT.FAIL;
		}
		
		JobLogger.log("refreshAccessTokenHandler ===> weixinService.getAccessToken success,accessToken:{0}",accessToken.toString());
		redisService.set(Constant.WXAPI_ACCESSTOKEN_KEY, GsonUtils.toJson(accessToken), 7200L);
		
		JobLogger.log("refreshAccessTokenHandler ===> redisService.getAccessToken rlt:{0}",redisService.get(Constant.WXAPI_ACCESSTOKEN_KEY));
		
		JobLogger.log("=======>JOB, refreshAccessTokenHandler.>>>>>>>>>>>>>>end>>>>>>>>>>>>>>");
		return ReturnT.SUCCESS;
	}

}
