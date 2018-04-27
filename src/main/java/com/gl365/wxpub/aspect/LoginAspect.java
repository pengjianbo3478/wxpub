package com.gl365.wxpub.aspect;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gl365.wxpub.common.CommonHelper;
import com.gl365.wxpub.common.Constant;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.WxAuthUserInfo;
import com.gl365.wxpub.dto.member.resp.UserBindInfo;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.handler.ConfigHandler;
import com.gl365.wxpub.service.MemberService;
import com.gl365.wxpub.service.RedisService;
import com.gl365.wxpub.util.GsonUtils;
@Aspect
@Component
public class LoginAspect {
	private static final Logger logger = LoggerFactory.getLogger(LoginAspect.class);
	@Autowired
	private RedisService redisService;
	@Autowired
	private MemberService memberService;

	@Around("within(@org.springframework.web.bind.annotation.RestController *) && @annotation(login)")
	public Object requestLogin(ProceedingJoinPoint proceedingJoinPoint, Login login) throws Throwable {
		HttpServletRequest request = null;
		if (login.needLogin()) {
			try {
				request = CommonHelper.getRequest();
				if (null == request) {
					logger.error("requestLogin handler error,no param : HttpServletRequest");
					return ResultDto.result(ResultCodeEnum.System.REQUEST_IS_NULL);
				}
				// 请求路径
				String path = request.getServletPath();
				String token = request.getHeader(Constant.GL_WXPUB_USER_TOKEN);
				if (StringUtils.isBlank(token)) {
					logger.error("requestPath:{},requestLogin error,token is null", path);
					return ResultDto.result(ResultCodeEnum.System.ILLEGAL_REQUEST);
				}
				String jsonStr = redisService.get(token);
				WxAuthUserInfo waui = GsonUtils.fromJson2Object(jsonStr, WxAuthUserInfo.class);
				if (waui == null || StringUtils.isBlank(waui.getOpenId())) {
					logger.error("requestPath:{},requestLogin error,redis's openId is null,token:{}", path, token);
					return ResultDto.result(ResultCodeEnum.System.ILLEGAL_REQUEST);
				}
				redisService.set(token, jsonStr, ConfigHandler.getInstance().getOpenIdTokenExpiredTime());
				UserBindInfo ubi = memberService.getuserBindInfo(waui.getOpenId(), waui.getNickname(), waui.getHeadimgurl(), null, null, null);
				if (ubi == null) {
					logger.error("requestPath:{},requestLogin ===> memberService.getuserBindInfo error,openId:{}", path, waui.getOpenId());
					return ResultDto.result(ResultCodeEnum.System.SYSTEM_ERROR);
				}
				request.getSession(true).setAttribute(Constant.GL_USER_ID, ubi.getUserId());
				request.getSession(true).setAttribute(Constant.GL_WXPUB_OPENID, waui.getOpenId());
				request.getSession(true).setAttribute(Constant.GL_WXPUB_BINDFLAG, ubi.getBindFlag());
				request.getSession(true).setAttribute(Constant.GL_WXPUB_BEANAMOUNT, ubi.getAmount());
				if (1 == ubi.getBindFlag()) {
					request.getSession(true).setAttribute(Constant.GL_WXPUB_MOBLIEPHONE, ubi.getMobilePhone());
				}
			}
			catch (Exception e) {
				logger.error("requestPermis handler error,exception:{}", e);
				return ResultDto.errorResult();
			}
			return proceedingJoinPoint.proceed();
		}
		else {
			return proceedingJoinPoint.proceed();
		}
	}
}
