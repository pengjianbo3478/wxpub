package com.gl365.wxpub.web;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import com.gl365.wxpub.common.Constant;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.WxApiAccessToken;
import com.gl365.wxpub.dto.WxAuthUserInfo;
import com.gl365.wxpub.dto.WxUserInfo;
import com.gl365.wxpub.dto.h5.req.GetOpenIdReq;
import com.gl365.wxpub.dto.h5.resp.WxAuthResp;
import com.gl365.wxpub.dto.member.resp.UserBindInfo;
import com.gl365.wxpub.dto.merchant.resp.MerchantBaseInfo;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.handler.ConfigHandler;
import com.gl365.wxpub.handler.HttpHandlerService;
import com.gl365.wxpub.service.MemberService;
import com.gl365.wxpub.service.MerchantService;
import com.gl365.wxpub.service.PromotionService;
import com.gl365.wxpub.service.RedisService;
import com.gl365.wxpub.util.GsonUtils;
import com.gl365.wxpub.util.MD5Utils;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiOperation;
@CrossOrigin(origins = "*", maxAge = 3600) // 跨域问题，与前端调试时打开
@RestController
@RequestMapping("/wxpubApi")
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	@Autowired
	private HttpHandlerService httpHandlerService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private PromotionService promotionService;

	@PostMapping("/getOpenId")
	@Log("微信扫码获取OpenId")
	@ApiOperation(value = "微信扫码获取OpenId", httpMethod = "POST", response = ResultDto.class)
	public Object getOpenId(@RequestBody GetOpenIdReq req) throws Exception {
		String scope = Constant.wx_openId_scope;
		String state = URLEncoder.encode(GsonUtils.toJson(req), "utf-8");
		String redirectUrl = URLEncoder.encode(ConfigHandler.getInstance().getRedirectUrl(), "utf-8");
		String data = String.format(ConfigHandler.getInstance().getCodeUrl(), ConfigHandler.getInstance().getAppId(), redirectUrl, scope, state);
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, data);
	}

	@GetMapping("/getOpenId")
	@Log("公众号获取OpenId")
	@ApiOperation(value = "公众号获取OpenId", httpMethod = "GET", response = ResultDto.class)
	public void getOpenId(HttpServletResponse response, @RequestParam(value = "redirectUrl", required = false) String redirectUrl, @RequestParam(value = "urlType", required = false) String urlType, @RequestParam(value = "drawId", required = false) String drawId) throws Exception {
		GetOpenIdReq req = new GetOpenIdReq();
		if (StringUtils.isBlank(urlType)) {
			StringBuffer sb = new StringBuffer(redirectUrl);
			sb.append("?token=%s");
			req.setRedirectUrl(sb.toString());
		}
		else {
			req.setUrlType(urlType);
			req.setDrawId(drawId);
		}
		String scope = Constant.wx_openId_scope;
		String state = URLEncoder.encode(GsonUtils.toJson(req), "utf-8");
		String redirectUrl1 = URLEncoder.encode(ConfigHandler.getInstance().getRedirectUrl(), "utf-8");
		String data = String.format(ConfigHandler.getInstance().getCodeUrl(), ConfigHandler.getInstance().getAppId(), redirectUrl1, scope, state);
		logger.info("公众号获取OpenId end,Redirect to:{}", data);
		response.sendRedirect(data);
		return;
	}

	@PostMapping("/getCodeUrl4OpenId")
	@Log("获取Code的请求url_用于获取OpenId")
	@ApiOperation(value = "获取请求wx的url_用于获取OpenId", httpMethod = "POST", response = ResultDto.class)
	public Object getCodeUrl4OpenId(@RequestBody GetOpenIdReq req) throws Exception {
		String scope = Constant.wx_openId_scope;
		String state = URLEncoder.encode(GsonUtils.toJson(req), "utf-8");
		String redirectUrl = URLEncoder.encode(ConfigHandler.getInstance().getRedirectUrl(), "utf-8");
		String data = String.format(ConfigHandler.getInstance().getCodeUrl(), ConfigHandler.getInstance().getAppId(), redirectUrl, scope, state);
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, data);
	}

	@PostMapping("/getCodeUrl4UserInfo")
	@Log("获取Code的请求url_用于获取UserInfo")
	@ApiOperation(value = "获取请求wx的url_用于获取UserInfo", httpMethod = "POST", response = ResultDto.class)
	public Object getCodeUrl4UserInfo(@RequestBody GetOpenIdReq req) throws Exception {
		WxAuthResp resp = new WxAuthResp();
		String token = null;
		// 获取微信头像、姓名
		WxUserInfo info = null;
		if (StringUtils.isNotBlank(req.getOpenId())) {
			info = getWxUserInfo(req.getOpenId());
		}
		if (info != null && 1 == info.getSubscribe()) {
			WxAuthUserInfo userInfo = new WxAuthUserInfo();
			BeanUtils.copyProperties(info, userInfo);
			userInfo.setOpenId(req.getOpenId());
			token = openId2Token(userInfo);
			logger.info("openid:{}生成token:{}", req.getOpenId(), token);
			BeanUtils.copyProperties(info, resp);
			resp.setToken(token);
		}
		else {
			String scope = Constant.wx_userInfo_scope;
			String state = URLEncoder.encode(GsonUtils.toJson(req), "utf-8");
			String redirectUrl = URLEncoder.encode(ConfigHandler.getInstance().getRedirectUrl(), "utf-8");
			resp.setUrl(String.format(ConfigHandler.getInstance().getCodeUrl(), ConfigHandler.getInstance().getAppId(), redirectUrl, scope, state));
		}
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, resp);
	}

	@GetMapping("/auth")
	public void auth(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "code") String code, @RequestParam(value = "state") String state) throws Exception {
		logger.info("auth 获取微信openId begin,req:(code:{},state:{})", code, state);
		if (StringUtils.isBlank(state)) {
			response.sendRedirect(ConfigHandler.getInstance().getH5errorUrl());
			return;
		}
		GetOpenIdReq req = null;
		try {
			req = GsonUtils.fromJson2Object(state, GetOpenIdReq.class);
		}
		catch (Exception e) {
			logger.error("auth fromJson2Object Exception");
			state = addSyh(state);
			logger.info("addSyh state:{}", state);
			try {
				req = GsonUtils.fromJson2Object(state, GetOpenIdReq.class);
			}
			catch (Exception ee) {
				logger.error("auth fromJson2Object second Exception:", ee);
			}
		}
		if (req == null) {
			response.sendRedirect(ConfigHandler.getInstance().getH5errorUrl());
			return;
		}
		// 获取accessToken和openId
		Map<String, String> data = getAccessTokenAndOpenId(code);
		if (null == data) {
			logger.error("获取accessToken和openId 失败");
			response.sendRedirect(ConfigHandler.getInstance().getH5errorUrl());
			return;
		}
		WxAuthUserInfo wxUserInfo = new WxAuthUserInfo();
		wxUserInfo.setOpenId(data.get("openid"));
		String token = openId2Token(wxUserInfo);
		if (StringUtils.isBlank(token)) {
			logger.error("生成token失败，md5 openid");
			response.sendRedirect(ConfigHandler.getInstance().getH5errorUrl());
			return;
		}
		if (StringUtils.isNotBlank(req.getUrlType())) {
			// 根据openId获取会员信息
			UserBindInfo ubi = memberService.getuserBindInfo(data.get("openid"), null, null, null, null, null);
			String activity = null;
			if ("1".equals(req.getUrlType())) {
				activity = String.format(ConfigHandler.getInstance().getDuibaDrawUrl(), req.getDrawId());
			}
			else {
				activity = ConfigHandler.getInstance().getDuibaGoodsUrl();
			}
			String redirectUrl = promotionService.unionLogin(ubi, activity, req.getUrlType());
			if (StringUtils.isNotBlank(redirectUrl)) {
				logger.info("获取对吧活动免登地址成功,redirectUrl:{}", redirectUrl);
				response.sendRedirect(redirectUrl);
				return;
			}
			else {
				logger.error("获取对吧活动免登地址失败");
				response.sendRedirect(ConfigHandler.getInstance().getH5errorUrl());
				return;
			}
		}
		if (StringUtils.isNotBlank(req.getRedirectUrl()) && StringUtils.isBlank(req.getOpenId())) {
			StringBuffer sb = new StringBuffer(ConfigHandler.getInstance().getH5UrlPrefix());
			sb.append(req.getRedirectUrl());
			String rUrl = String.format(sb.toString(), token);
			logger.info("auth end,RedirectUrl:{}", rUrl);
			response.sendRedirect(rUrl);
			return;
		}
		StringBuffer url = new StringBuffer(ConfigHandler.getInstance().getH5perPayUrl());
		String nickname = null;
		String headimgurl = null;
		String recommendAgentType = null;
		String recommendAgentId = null;
		String recommendBy = req.getOperatorId();
		if (StringUtils.isBlank(req.getGroupId()) && StringUtils.isBlank(req.getOpenId())) {
			// 扫码支付鉴权
			url.append("merchantNo=").append(req.getMerchantNo());
			if (StringUtils.isNotBlank(req.getOperatorId())) {
				url.append("&operatorId=").append(req.getOperatorId());
			}
			url.append("&totalAmount=").append(req.getTotalAmount() == null ? new BigDecimal(0) : req.getTotalAmount());
			url.append("&noBenefitAmount=").append(req.getNoBenefitAmount() == null ? new BigDecimal(0) : req.getNoBenefitAmount());
			ResultDto<MerchantBaseInfo> mi = merchantService.getMerchantBaseInfo(req.getMerchantNo());
			if (mi != null && mi.getData() != null) {
				recommendAgentId = mi.getData().getMerchantNo();
				recommendAgentType = Constant.RECOMMENDAGENTTYPE;
				url.append("&noBenefit=").append(mi.getData().getNoBenefit());
			}
			else {
				logger.error("根据商户号获取商户信息失败，merchantNo:{}", req.getMerchantNo());
			}
		}
		else {
			if (StringUtils.isNotBlank(req.getGroupId())) {
				// 群买单参与者
				url = new StringBuffer(ConfigHandler.getInstance().getH5groupPayUrl());
				url.append("groupId=").append(req.getGroupId());
				url.append("&saleRate=").append(req.getSaleRate());
			}
			else {
				// 群买单发起者
				url = new StringBuffer(ConfigHandler.getInstance().getH5UrlPrefix());
				url.append(req.getRedirectUrl());
			}
			// 根据accessToken和openId获取用户信息
			Map<String, String> userInfo = getUserInfo(data.get("access_token"), data.get("openid"));
			if (null == userInfo) {
				logger.error("根据accessToken和openId获取用户信息 失败");
				response.sendRedirect(ConfigHandler.getInstance().getH5errorUrl());
				return;
			}
			nickname = userInfo.get("nickname");
			headimgurl = userInfo.get("headimgurl");
			if (url.toString().endsWith("?")) {
				url.append("nickname=").append(URLEncoder.encode(nickname, "utf-8"));
			}
			else {
				url.append("&nickname=").append(URLEncoder.encode(nickname, "utf-8"));
			}
			url.append("&headimgurl=").append(headimgurl);
			wxUserInfo.setHeadimgurl(headimgurl);
			wxUserInfo.setNickname(nickname);
			token = openId2Token(wxUserInfo);
		}
		url.append("&openId=").append(data.get("openid"));
		url.append("&token=").append(token);
		url.append("&terminal=").append(req.getTerminal());
		// 根据openId获取会员信息
		UserBindInfo ubi = memberService.getuserBindInfo(data.get("openid"), nickname, headimgurl, recommendAgentId, recommendAgentType, recommendBy);
		if (null != ubi) {
			url.append("&userId=").append(ubi.getUserId());
			url.append("&bindFlag=").append(ubi.getBindFlag());
			url.append("&beanBalance=").append(ubi.getAmount());
		}
		else {
			response.sendRedirect(ConfigHandler.getInstance().getH5errorUrl());
			return;
		}
		logger.info("auth end,RedirectUrl:{}", url.toString());
		response.sendRedirect(url.toString());
		return;
	}

	/**
	 * 获取accessToken和openId
	 * @param code
	 * @return
	 */
	private Map<String, String> getAccessTokenAndOpenId(String code) {
		String url = String.format(ConfigHandler.getInstance().getWxAuthUrl(), ConfigHandler.getInstance().getAppId(), ConfigHandler.getInstance().getAppSecret(), code);
		JsonObject object = null;
		Map<String, String> data = new HashMap<>();
		try {
			object = httpHandlerService.get(url);
			if (null == object || null == object.get("openid") || null == object.get("access_token")) {
				logger.error("request accessToken fail. [result={}]", object);
				return null;
			}
			logger.info("request accessToken success. [result={}]", object);
			data.put("openid", object.get("openid").toString().replaceAll("\"", ""));
			data.put("access_token", object.get("access_token").toString().replaceAll("\"", ""));
			return data;
		}
		catch (Exception ex) {
			logger.error("fail to request wechat access token. error:", ex);
			return null;
		}
	}

	/**
	 * 获取userInfo
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	private Map<String, String> getUserInfo(String accessToken, String openId) {
		String url = String.format(ConfigHandler.getInstance().getUserInfoUrl(), accessToken, openId);
		JsonObject userInfo = null;
		Map<String, String> data = new HashMap<>();
		try {
			userInfo = httpHandlerService.get(url);
			if (null == userInfo || null != userInfo.get("errcode")) {
				logger.error("request userInfo fail. [result={}]", userInfo);
				return null;
			}
			logger.info("request userInfo success. [result={}]", userInfo);
			data.put("openid", userInfo.get("openid").toString().replaceAll("\"", ""));
			data.put("nickname", userInfo.get("nickname").toString().replaceAll("\"", ""));
			data.put("city", userInfo.get("city").toString().replaceAll("\"", ""));
			data.put("province", userInfo.get("province").toString().replaceAll("\"", ""));
			data.put("country", userInfo.get("country").toString().replaceAll("\"", ""));
			data.put("headimgurl", userInfo.get("headimgurl").toString().replaceAll("\"", ""));
			return data;
		}
		catch (Exception ex) {
			logger.error("fail to request wechat userInfo. error:", ex);
			return null;
		}
	}

	@GetMapping("/querySubscribe")
	@Log("查询是否关注公众号")
	@ApiOperation(value = "查询是否关注公众号", httpMethod = "GET", response = ResultDto.class)
	public Object querySubscribe(@RequestParam("openId") String openId) throws Exception {
		String token = getWxApiAccessToken();
		if (StringUtils.isBlank(token)) {
			logger.info("查询是否关注公众号 querySubscribe redis's WxApiAccessToken is null,默认返回未关注");
			return new ResultDto<>(ResultCodeEnum.System.SUCCESS, 0);
		}
		String url = String.format(ConfigHandler.getInstance().getSubscribeUrl(), token, openId);
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, isSubscribe(url));
	}

	private String getWxApiAccessToken() {
		String redisToken = redisService.get(Constant.WXAPI_ACCESSTOKEN_KEY);
		if (StringUtils.isBlank(redisService.get(Constant.WXAPI_ACCESSTOKEN_KEY))) { return null; }
		WxApiAccessToken token = GsonUtils.fromJson2Object(redisToken, WxApiAccessToken.class);
		return token.getToken();
	}

	private int isSubscribe(String url) {
		JsonObject rlt = null;
		try {
			rlt = httpHandlerService.get(url);
			if (null == rlt || null != rlt.get("errcode")) {
				logger.error("request isSubscribe fail. [result={}]", GsonUtils.toJson(rlt));
				return 0;
			}
			logger.info("request isSubscribe rlt:{}", GsonUtils.toJson(rlt));
			return rlt.get("subscribe").getAsInt();
		}
		catch (Exception ex) {
			logger.error("fail to request wechat userInfo. error:", ex);
			return 0;
		}
	}

	private String openId2Token(WxAuthUserInfo userInfo) {
		String token = MD5Utils.md5(userInfo.getOpenId());
		if (StringUtils.isBlank(token)) { return null; }
		redisService.set(token, GsonUtils.toJson(userInfo), ConfigHandler.getInstance().getOpenIdTokenExpiredTime());
		return token;
	}

	private WxUserInfo getWxUserInfo(String openId) {
		String token = getWxApiAccessToken();
		if (StringUtils.isBlank(token)) {
			logger.error("[openId:{}]getWxUserInfo redis's WxApiAccessToken is null", openId);
			return null;
		}
		String url = String.format(ConfigHandler.getInstance().getSubscribeUrl(), token, openId);
		JsonObject rlt = null;
		try {
			rlt = httpHandlerService.get(url);
		}
		catch (Exception e) {
			logger.error("[openId:{}]getWxUserInfo isSubscribe exception:", openId, e);
		}
		if (null == rlt || null != rlt.get("errcode")) {
			logger.error("[openId:{}]getWxUserInfo isSubscribe fail. [result={}]", openId, GsonUtils.toJson(rlt));
			return null;
		}
		logger.info("[openId:{}]getWxUserInfo isSubscribe success,[result={}]", openId, GsonUtils.toJson(rlt));
		return GsonUtils.fromJson2Object(GsonUtils.toJson(rlt), WxUserInfo.class);
	}

	@Login
	@GetMapping("/groupOwnerGetUserInfo")
	@Log("群主获取微信名称头像")
	@ApiOperation(value = "群主获取微信名称头像", httpMethod = "GET", response = ResultDto.class)
	public Object groupOwnerGetUserInfo(HttpServletRequest request) {
		String token = request.getHeader(Constant.GL_WXPUB_USER_TOKEN);
		WxUserInfo data = getWxUserInfo((String) request.getSession(false).getAttribute(Constant.GL_WXPUB_OPENID));
		String jsonStr = redisService.get(token);
		WxAuthUserInfo waui = GsonUtils.fromJson2Object(jsonStr, WxAuthUserInfo.class);
		if (null != data) {
			waui.setHeadimgurl(data.getHeadimgurl());
			waui.setNickname(data.getNickname());
		}
		redisService.set(token, GsonUtils.toJson(waui), ConfigHandler.getInstance().getOpenIdTokenExpiredTime());
		return new ResultDto<>(ResultCodeEnum.System.SUCCESS, data);
	}

	private String addSyh(String jsonStr) {
		String result = Arrays.stream(jsonStr.substring(1, jsonStr.length() - 1).split(",")).map(v -> "\"" + v.replaceFirst(":", "\":\"") + "\"").reduce("{", (a, b) -> a + b + ",").toString();
		result = result.substring(0, result.length() - 1) + "}";
		return result;
	}

	private String unicode(String source) {
		StringBuffer sb = new StringBuffer();
		char[] source_char = source.toCharArray();
		String unicode = null;
		for (int i = 0; i < source_char.length; i++) {
			unicode = Integer.toHexString(source_char[i]);
			if (unicode.length() <= 2) {
				unicode = "00" + unicode;
			}
			sb.append("\\u" + unicode);
		}
		return sb.toString();
	}
}
