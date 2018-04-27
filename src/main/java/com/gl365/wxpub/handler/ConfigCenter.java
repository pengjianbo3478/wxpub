package com.gl365.wxpub.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.gl365.wxpub.util.CommonUtils;

/**
 * 
 * @author dfs_518
 *
 *         2017年9月12日上午11:29:38
 */
@Component
@Configuration
@RefreshScope
public class ConfigCenter {

	@Value("${wxpub.getCode.url}")
	private String codeUrl;
	
	@Value("${wxpub.gl.app-id}")
	private String appId;
	
	@Value("${wxpub.gl.app-secret}")
	private String appSecret;
	
	@Value("${wxpub.getCode.redirect-url}")
	private String redirectUrl;
	
	@Value("${wxpub.getAccessToken.url}")
	private String wxAuthUrl;
	
	@Value("${wxpub.wxApi.accessToken.url}")
	private String accessTokenUrl;

	@Value("${wxpub.jsApi.ticket.url}")
	private String ticketUrl;
	
	@Value("${wxpub.getUserInfo.url}")
	private String userInfoUrl;
	
	@Value("${wxpub.jspay.callbackurl}")
	private String callBackUrl;
	
	@Value("${wxpub.jspay.channel}")
	private String wxpubChannel;
	
	@Value("${wxpub.jspay.url}")
	private String jsPayUrl;
	
	@Value("${wxpub.h5.perPay.url}")
	private String h5perPayUrl;
	
	@Value("${wxpub.h5.groupPay.url}")
	private String h5groupPayUrl;
	
	@Value("${wxpub.h5.error.url}")
	private String h5errorUrl;

	@Value("${groupPay.group.size.max}")
	private int groupMaxSize;
	
	@Value("${wx.glMerchantNo}")
	private String glMerchantNo;

	@Value("${groupPay.rlAmount.proportion.min}")
	private String minProportion;
	
	@Value("${groupPay.rlAmount.proportion.max}")
	private String maxProportion;
	
	@Value("${groupPay.totalAmount.min}")
	private String totalAmountMin;
	
	@Value("${groupPay.turnTable.countDown.second}")
	private String countDownSec;

	@Value("${wxpub.groupPay.callbackurl}")
	private String groupPayCallbackUrl;

	@Value("${wxpub.h5.url.prefix}")
	private String h5UrlPrefix;

	@Value("${wxpub.h5.active.url}")
	private String h5ActiveUrl;
	
	@Value("${wxpub.querySubscribe.url}")
	private String subscribeUrl;

	@Value("${groupPay.QRCode.url}")
	private String groupPayQRCodeUrl;

	@Value("${wxpub.redis.openId.token.expiredTime}")
	private long openIdTokenExpiredTime;

	@Value("${wxpub.redis.lottery.expiredTime}")
	private long lotteryDtoExpiredTime;
	
	@Value("${wxpub.sendMsg.url}")
	private String sendMsgUrl;
	
	@Value("${wxpub.msg.templateID.pay}")
	private String payMsgTemplateID;
	
	@Value("${wxpub.msg.templateID.refund}")
	private String refundMsgTemplateID;
	
	@Value("${wxpub.h5Url.pay.detail}")
	private String payDetailUrl;
	
	@Value("${wxpub.h5Url.refund.detail}")
	private String refundDetailUrl;
	
	@Value("${wxpub.duiba.url.draw}")
	private String duibaDrawUrl;

	@Value("${wxpub.duiba.url.goods}")
	private String duibaGoodsUrl;
	
	public String getPayDetailUrl() {
		return payDetailUrl;
	}

	public void setPayDetailUrl(String payDetailUrl) {
		this.payDetailUrl = payDetailUrl;
	}

	public String getRefundDetailUrl() {
		return refundDetailUrl;
	}

	public void setRefundDetailUrl(String refundDetailUrl) {
		this.refundDetailUrl = refundDetailUrl;
	}

	public String getSendMsgUrl() {
		return sendMsgUrl;
	}

	public void setSendMsgUrl(String sendMsgUrl) {
		this.sendMsgUrl = sendMsgUrl;
	}

	public String getPayMsgTemplateID() {
		return payMsgTemplateID;
	}

	public void setPayMsgTemplateID(String payMsgTemplateID) {
		this.payMsgTemplateID = payMsgTemplateID;
	}

	public String getRefundMsgTemplateID() {
		return refundMsgTemplateID;
	}

	public void setRefundMsgTemplateID(String refundMsgTemplateID) {
		this.refundMsgTemplateID = refundMsgTemplateID;
	}

	public long getOpenIdTokenExpiredTime() {
		return openIdTokenExpiredTime;
	}

	public void setOpenIdTokenExpiredTime(long openIdTokenExpiredTime) {
		this.openIdTokenExpiredTime = openIdTokenExpiredTime;
	}

	public long getLotteryDtoExpiredTime() {
		return lotteryDtoExpiredTime;
	}

	public void setLotteryDtoExpiredTime(long lotteryDtoExpiredTime) {
		this.lotteryDtoExpiredTime = lotteryDtoExpiredTime;
	}

	public String getH5ActiveUrl() {
		return CommonUtils.h5UrlAddTimeStamp(h5ActiveUrl);
	}

	public void setH5ActiveUrl(String h5ActiveUrl) {
		this.h5ActiveUrl = h5ActiveUrl;
	}

	public String getGroupPayQRCodeUrl() {
		return CommonUtils.h5UrlAddTimeStamp(groupPayQRCodeUrl);
	}

	public void setGroupPayQRCodeUrl(String groupPayQRCodeUrl) {
		this.groupPayQRCodeUrl = groupPayQRCodeUrl;
	}

	public String getGlMerchantNo() {
		return glMerchantNo;
	}

	public void setGlMerchantNo(String glMerchantNo) {
		this.glMerchantNo = glMerchantNo;
	}

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getAccessTokenUrl() {
		return accessTokenUrl;
	}

	public void setAccessTokenUrl(String accessTokenUrl) {
		this.accessTokenUrl = accessTokenUrl;
	}

	public String getUserInfoUrl() {
		return userInfoUrl;
	}

	public void setUserInfoUrl(String userInfoUrl) {
		this.userInfoUrl = userInfoUrl;
	}

	public String getCallBackUrl() {
//		return CommonUtils.h5UrlAddTimeStamp(callBackUrl);
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

	public String getWxpubChannel() {
		return wxpubChannel;
	}

	public void setWxpubChannel(String wxpubChannel) {
		this.wxpubChannel = wxpubChannel;
	}

	public String getJsPayUrl() {
		return jsPayUrl;
	}

	public void setJsPayUrl(String jsPayUrl) {
		this.jsPayUrl = jsPayUrl;
	}

	public String getH5perPayUrl() {
		return CommonUtils.h5UrlAddTimeStamp(h5perPayUrl);
	}

	public void setH5perPayUrl(String h5perPayUrl) {
		this.h5perPayUrl = h5perPayUrl;
	}

	public String getH5errorUrl() {
		return CommonUtils.h5UrlAddTimeStamp(h5errorUrl);
	}

	public void setH5errorUrl(String h5errorUrl) {
		this.h5errorUrl = h5errorUrl;
	}

	public String getH5groupPayUrl() {
		return CommonUtils.h5UrlAddTimeStamp(h5groupPayUrl);
	}

	public void setH5groupPayUrl(String h5groupPayUrl) {
		this.h5groupPayUrl = h5groupPayUrl;
	}

	public int getGroupMaxSize() {
		return groupMaxSize;
	}

	public void setGroupMaxSize(int groupMaxSize) {
		this.groupMaxSize = groupMaxSize;
	}

	public String getMinProportion() {
		return minProportion;
	}

	public void setMinProportion(String minProportion) {
		this.minProportion = minProportion;
	}

	public String getMaxProportion() {
		return maxProportion;
	}

	public void setMaxProportion(String maxProportion) {
		this.maxProportion = maxProportion;
	}

	public String getTotalAmountMin() {
		return totalAmountMin;
	}

	public void setTotalAmountMin(String totalAmountMin) {
		this.totalAmountMin = totalAmountMin;
	}

	public String getCountDownSec() {
		return countDownSec;
	}

	public void setCountDownSec(String countDownSec) {
		this.countDownSec = countDownSec;
	}

	public String getGroupPayCallbackUrl() {
//		return CommonUtils.h5UrlAddTimeStamp(groupPayCallbackUrl);
		return groupPayCallbackUrl;
	}

	public void setGroupPayCallbackUrl(String groupPayCallbackUrl) {
		this.groupPayCallbackUrl = groupPayCallbackUrl;
	}

	public String getH5UrlPrefix() {
		return CommonUtils.h5UrlAddTimeStamp(h5UrlPrefix);
	}

	public void setH5UrlPrefix(String h5UrlPrefix) {
		this.h5UrlPrefix = h5UrlPrefix;
	}

	public String getSubscribeUrl() {
		return subscribeUrl;
	}

	public void setSubscribeUrl(String subscribeUrl) {
		this.subscribeUrl = subscribeUrl;
	}

	public String getWxAuthUrl() {
		return wxAuthUrl;
	}

	public void setWxAuthUrl(String wxAuthUrl) {
		this.wxAuthUrl = wxAuthUrl;
	}

	public String getTicketUrl() {
		return ticketUrl;
	}

	public void setTicketUrl(String ticketUrl) {
		this.ticketUrl = ticketUrl;
	}

	public String getDuibaDrawUrl() {
		return duibaDrawUrl;
	}

	public void setDuibaDrawUrl(String duibaDrawUrl) {
		this.duibaDrawUrl = duibaDrawUrl;
	}

	public String getDuibaGoodsUrl() {
		return duibaGoodsUrl;
	}

	public void setDuibaGoodsUrl(String duibaGoodsUrl) {
		this.duibaGoodsUrl = duibaGoodsUrl;
	}

}
