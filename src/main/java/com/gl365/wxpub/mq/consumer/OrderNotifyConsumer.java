package com.gl365.wxpub.mq.consumer;

import java.time.format.DateTimeFormatter;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gl365.aliyun.ons.OnsListener;
import com.gl365.wxpub.common.Constant;
import com.gl365.wxpub.dto.PayMsgTemp;
import com.gl365.wxpub.dto.RefundMsgTemp;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.merchant.resp.MerchantBaseInfo;
import com.gl365.wxpub.dto.mq.consumer.OrderNotifyPOJO;
import com.gl365.wxpub.service.MerchantService;
import com.gl365.wxpub.service.WxMsgService;
import com.gl365.wxpub.util.GsonUtils;

@Component("order-return-notify-consumer")
public class OrderNotifyConsumer implements OnsListener {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderNotifyConsumer.class);
	
	@Resource
	private WxMsgService wxMsgService;
	
	@Resource
	private MerchantService merchantService;

	@Override
	public void receive(byte[] req) {
		String msg = new String(req);
		logger.info("OrderNotifyConsumer begin,receive msg:{}",msg);
		OrderNotifyPOJO orderNotify = GsonUtils.fromJson2Object(msg, OrderNotifyPOJO.class);
		if(null == orderNotify){
			logger.error("OrderNotifyConsumer fromJson2Object error,orderNotify is null,msg:{}",msg);
			return;
		}
		
		if(Constant.CONFIRM.equalsIgnoreCase(orderNotify.getTranType())){
			buildWxPayMsg(orderNotify);
		}else if(Constant.REFUND.equalsIgnoreCase(orderNotify.getTranType()) || Constant.REFUNDING.equalsIgnoreCase(orderNotify.getTranType())){
			buildWxRefundMsg(orderNotify);
		}else{
			logger.error("OrderNotifyConsumer tranType({}) error,orderNotify:{}",orderNotify.getTranType(),orderNotify);
		}
		return;
	}

	private void buildWxPayMsg(OrderNotifyPOJO orderNotify){
		logger.info("buildWxPayMsg begin,orderNotify:{}",orderNotify);
		if(null == orderNotify.getConfirm() || !"wx_pub".equalsIgnoreCase(orderNotify.getConfirm().getChannel())){
			return;
		}
		ResultDto<MerchantBaseInfo> merchantInfo = merchantService.getMerchantBaseInfo(orderNotify.getConfirm().getMerchantNo());
		
		PayMsgTemp temp = new PayMsgTemp();
		temp.getKeyword1().setValue(orderNotify.getConfirm().getOrderSn());
		temp.getKeyword2().setValue(String.valueOf(orderNotify.getConfirm().getTotalAmount()));
		if(null == merchantInfo || null == merchantInfo.getData() || StringUtils.isBlank(merchantInfo.getData().getMerchantShortName())){
			String orderTitle = orderNotify.getConfirm().getOrderTitle();
			if(StringUtils.isNotBlank(orderTitle) && orderTitle.contains("_")){
				temp.getKeyword3().setValue(orderTitle.substring(0, orderTitle.indexOf("_")));
			}else{
				temp.getKeyword3().setValue(orderNotify.getConfirm().getOrderTitle());
			}
		}else{
			temp.getKeyword3().setValue(merchantInfo.getData().getMerchantShortName());
		}
		temp.getKeyword4().setValue(orderNotify.getConfirm().getPaymentTime());
		
		boolean rlt = wxMsgService.sendWxPayMsg(orderNotify.getConfirm().getMemberId(), temp);
		logger.info("buildWxPayMsg end,orderNo:{},rlt:{}",orderNotify.getConfirm().getOrderSn(),rlt);
	}
	
	private void buildWxRefundMsg(OrderNotifyPOJO orderNotify){
		logger.info("buildWxRefundMsg begin,orderNotify:{}",orderNotify);
		if(null == orderNotify.getRefund() || !"wx_pub".equalsIgnoreCase(orderNotify.getRefund().getChannel())){
			return;
		}
		
		RefundMsgTemp temp = new RefundMsgTemp();
		String status = null;
		if(Constant.REFUND.equalsIgnoreCase(orderNotify.getTranType())){
			temp.getFirst().setValue("您好，您的支付金额已退回原支付账户，请查收");
			status = Constant.REFUND;
		}else{
			temp.getFirst().setValue("您好，您的退款申请已受理");
			temp.getRemark().setValue("我们将在1-3个工作日内处理完成您的退款申请，感谢您的惠顾");
			status = Constant.REFUNDING;
		}
		temp.getKeyword1().setValue(orderNotify.getRefund().getOrderSn());
		temp.getKeyword2().setValue(String.valueOf(orderNotify.getRefund().getTotalAmount()));
		temp.getKeyword3().setValue(String.valueOf(orderNotify.getRefund().getBeanAmount())+"乐豆");
		temp.getKeyword4().setValue(String.valueOf(orderNotify.getRefund().getGiftAmount())+"乐豆");
		
		
		boolean rlt = wxMsgService.sendWxRefundMsg(orderNotify.getRefund().getMemberId(), temp, status);
		logger.info("buildWxRefundMsg end,orderNo:{},rlt:{}",orderNotify.getRefund().getOrderSn(),rlt);
	}
	
}
