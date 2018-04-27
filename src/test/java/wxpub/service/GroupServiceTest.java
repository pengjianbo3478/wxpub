package wxpub.service;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gl365.Application;
import com.gl365.wxpub.common.Constant;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.WxApiAccessToken;
import com.gl365.wxpub.dto.h5.resp.GroupPayAmount;
import com.gl365.wxpub.dto.h5.resp.GroupUserInfo;
import com.gl365.wxpub.handler.ConfigHandler;
import com.gl365.wxpub.service.GroupService;
import com.gl365.wxpub.service.RedisService;
import com.gl365.wxpub.service.WeixinService;
import com.gl365.wxpub.util.GsonUtils;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class GroupServiceTest {

	private static final String IMG_URL = "imgUrl";

	private static final String USER_NAME = "userName";

	private static final String USER_ID = "userId";

	@Autowired
	private GroupService groupService;
	
	@Autowired
	private WeixinService weixinService;
	
	@Resource
	private RedisService redisService;
	
	private static final String mainOrderNo = "201709251843";
	
//	@Test
//	public void groupTest(){
//		groupService.buildGroup(mainOrderNo);
//		System.out.println("建群成功，群状态："+groupService.queryGroupStatus(mainOrderNo).getStatus()+",群人数："+groupService.queryGroupSize(mainOrderNo));
//		for(int i = 0; i < 11; i++){
//			if(ResultCodeEnum.System.SUCCESS.getCode().equals(groupService.InGroup(mainOrderNo, USER_ID+i, USER_NAME+i, IMG_URL+i))){
//				System.out.println("进群成功,群人数："+groupService.queryGroupSize(mainOrderNo));
//			}else{
//				System.out.println("进群失败"+USER_ID+i);
//			}
//		}
//		System.out.println("目前群人数为："+groupService.queryGroupSize(mainOrderNo));
//		System.out.println("所有群成员信息："+groupService.queryGroupInfo(mainOrderNo));
//		
//		groupService.outGroup(mainOrderNo, USER_ID+"5");
//		System.out.println("退群后人数为："+groupService.queryGroupSize(mainOrderNo));
//		System.out.println("退群后所有群成员信息："+groupService.queryGroupInfo(mainOrderNo));
//		
//		GroupUserInfo info6 = groupService.queryGroupUserInfo(mainOrderNo, USER_ID+"6");
//		System.out.println("查询群成员USER_ID6信息："+info6);
//		
//		info6.setTotalAmount(new BigDecimal(5));
//		groupService.updateGroupUserInfo(mainOrderNo, info6);
//		System.out.println("更新后查询群成员USER_ID6信息："+groupService.queryGroupUserInfo(mainOrderNo, USER_ID+"6"));
//		
//	}
//	
//	private GroupUserInfo buildInfo(){
//		GroupUserInfo info = new GroupUserInfo();
//		return info;
//	}
	
	@Test
	public void buildGroupTest(){
		System.out.println("========");
//		int size = groupService.queryGroupSize(mainOrderNo);
//		List<GroupUserInfo> list = groupService.queryGroupInfo(mainOrderNo);
//		GroupUserInfo info = groupService.queryGroupUserInfo(mainOrderNo, USER_ID+1);
//		ResultDto<?>  rlt = groupService.InGroup(mainOrderNo, USER_ID+2, USER_NAME+2, IMG_URL+2);
		GroupPayAmount gpa = groupService.queryGroupPayAmount(mainOrderNo);
	}
	
	@Test
	public void getAccessTokenTest(){
		WxApiAccessToken accessToken = weixinService.getAccessToken();
		redisService.set(Constant.WXAPI_ACCESSTOKEN_KEY, GsonUtils.toJson(accessToken), 7200L);
		System.out.println(redisService.get(Constant.WXAPI_ACCESSTOKEN_KEY));
	}
	
	@Test
	public void urlTest(){
		System.out.println(ConfigHandler.getInstance().getCallBackUrl());
		System.out.println(ConfigHandler.getInstance().getGroupPayCallbackUrl());
		System.out.println(ConfigHandler.getInstance().getH5errorUrl());
		System.out.println(ConfigHandler.getInstance().getH5groupPayUrl());
		System.out.println(ConfigHandler.getInstance().getH5perPayUrl());
		System.out.println(ConfigHandler.getInstance().getH5UrlPrefix());
	}
}
