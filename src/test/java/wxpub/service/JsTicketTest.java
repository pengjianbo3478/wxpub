package wxpub.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gl365.Application;
import com.gl365.wxpub.common.Constant;
import com.gl365.wxpub.dto.PayMsgTemp;
import com.gl365.wxpub.dto.RefundMsgTemp;
import com.gl365.wxpub.dto.WxApiAccessToken;
import com.gl365.wxpub.handler.HttpHandlerService;
import com.gl365.wxpub.service.RedisService;
import com.gl365.wxpub.service.WeixinService;
import com.gl365.wxpub.service.WxMsgService;
import com.gl365.wxpub.util.GsonUtils;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class JsTicketTest {

	@Resource
	private WeixinService weixinService;
	
	@Resource
	private RedisService redisService;
	
	@Resource
	private HttpHandlerService httpHandlerService;
	
	@Resource
	private WxMsgService wxMsgService;
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	@Test
	public void getTicketTest(){
//		String redisToken = redisService.get(Constant.WXAPI_ACCESSTOKEN_KEY);
//		WxApiAccessToken token = GsonUtils.fromJson2Object(redisToken, WxApiAccessToken.class);
//		WxJsApiTicket ticket = weixinService.getJsTicket(token.getToken());
//		System.out.println("ticket"+ticket);
//		redisService.set(Constant.WXJSAPI_TICKET_KEY, GsonUtils.toJson(ticket), 7200L);
	}
	
	@Test
	public void sendWxMsgTest(){
		//o1T_EwQ_glZ08mq6sn52vgxG-zl4
		PayMsgTemp temp = new PayMsgTemp();
		temp.getKeyword1().setValue("01794090408555544417002931");
		temp.getKeyword2().setValue("103.3元");
		temp.getKeyword3().setValue("测试门店");
		temp.getKeyword4().setValue(LocalDateTime.now().format(formatter));
		boolean rlt = wxMsgService.sendWxPayMsg("5bbb9eef994b465e8cd62c20a41d58db", temp);
		System.out.println(rlt);
	}
	
	@Test
	public void sendRefundTest(){
		RefundMsgTemp temp = new RefundMsgTemp();
		temp.getFirst().setValue("你好，您的退款申请已受理");;
		temp.getKeyword1().setValue("51794090408555544417002931");
		temp.getKeyword2().setValue("103.3元");
		temp.getRemark().setValue("我们将在3个工作日内处理完您的退款申请，感谢您的惠顾");
		boolean rlt = wxMsgService.sendWxRefundMsg("5bbb9eef994b465e8cd62c20a41d58db", temp, "refund");
		System.out.println(rlt);
	}
	
	@Test
	public void bingfaTest(){
		Thread t1 = new Thread(new SendRefundMsgThread());
		Thread t2 = new Thread(new SendRefundMsgThread());
		Thread t3 = new Thread(new SendRefundMsgThread());
		t1.start();
		t2.start();
		t3.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class SendPayMsgThread implements Runnable{
		@Override
		public void run() {
			sendPayMsg();
		}
		
	}
	
	class SendRefundMsgThread implements Runnable{
		@Override
		public void run() {
			sendRefundMsg();
		}
		
	}
	
	private void sendPayMsg(){
		PayMsgTemp temp = new PayMsgTemp();
		temp.getKeyword1().setValue("01794090408555544417002931");
		temp.getKeyword2().setValue("103.3元");
		temp.getKeyword3().setValue("测试门店");
		temp.getKeyword4().setValue(LocalDateTime.now().format(formatter));
		boolean rlt = wxMsgService.sendWxPayMsg("5bbb9eef994b465e8cd62c20a41d58db", temp);
		System.out.println(rlt);
	}
	
	private void sendRefundMsg(){
		RefundMsgTemp temp = new RefundMsgTemp();
		temp.getFirst().setValue("你好，您的退款申请已受理");;
		temp.getKeyword1().setValue("51794090408555544417002931");
		temp.getKeyword2().setValue("103.3元");
		temp.getRemark().setValue("我们将在3个工作日内处理完您的退款申请，感谢您的惠顾");
		boolean rlt = wxMsgService.sendWxRefundMsg("5bbb9eef994b465e8cd62c20a41d58db", temp, "refund");
		System.out.println(rlt);
	}
	
	private String getAccessToken(){
		String redisToken = redisService.get(Constant.WXAPI_ACCESSTOKEN_KEY);
		if(StringUtils.isBlank(redisToken)){
			return null;
		}
		WxApiAccessToken token = GsonUtils.fromJson2Object(redisToken, WxApiAccessToken.class);
		return token.getToken();
	}
	
}
