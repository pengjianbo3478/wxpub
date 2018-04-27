package com.gl365.wxpub.web;

import com.gl365.wxpub.aspect.Log;
import com.gl365.wxpub.aspect.Login;
import com.gl365.wxpub.common.CommonHelper;
import com.gl365.wxpub.common.Constant;
import com.gl365.wxpub.common.JsonUtils;
import com.gl365.wxpub.dto.PageDto;
import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.merchant.req.GetCommentStatusReq;
import com.gl365.wxpub.dto.merchant.req.GetCommentTempleteCommand;
import com.gl365.wxpub.dto.merchant.req.GetMerchantComments4MemberCommand;
import com.gl365.wxpub.dto.merchant.req.GetUserBillsCommand;
import com.gl365.wxpub.dto.merchant.resp.CommentDto;
import com.gl365.wxpub.dto.merchant.resp.PageResultDto;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.service.CommentService;
import com.gl365.wxpub.service.FeignCilent.CommentClient;
import com.gl365.wxpub.service.FeignCilent.OrderClient;
import com.gl365.wxpub.util.AmountTransferUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HttpMethod;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2017/10/13.
 */
@CrossOrigin(origins = "*", maxAge = 3600) // 跨域问题，与前端调试时打开
@RestController
@Api(description = "商户相关",tags = "merchant")
@RequestMapping("/wxpubApi/comment")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Resource
    private CommentClient commentClient;

    @Resource
    private OrderClient orderClient;
    
    @Resource
    private CommentService commentService;

    /**
     * 获取商家评论模板
     *
     * @param industry  行业
     * @return
     */
    @GetMapping("/label/template/{industry}")
    @ApiOperation(value = "查询商家评论模板",httpMethod = HttpMethod.GET)
    @ApiImplicitParam(paramType = "path",dataType = "String",name ="industry",value = "行业",required = true)
    @Log
    public ResultDto templete(@PathVariable("industry") String industry) {
        logger.info("获取商家评论模板 > > > 行业ID：{}", JsonUtils.toJsonString(industry));
        GetCommentTempleteCommand command = new GetCommentTempleteCommand();
        command.setIndustry(industry);
        return commentClient.templete(command);
    }

    /**
     * 获取用户未评论账单
     *
     * @param command
     * @return
     */
    @Login
    @PostMapping("/bills")
    @Log("获取用户未评论账单")
    public Object getUserNoCommentBills(@RequestBody GetUserBillsCommand command, HttpServletRequest request) {
        logger.info("获取用户未评论账单 > > > 入参：{}", JsonUtils.toJsonString(command));
        String userId = CommonHelper.getUserIdByRequest();
        if (null == userId || "null".equals(userId)){
            return ResultDto.result(ResultCodeEnum.System.ILLEGAL_REQUEST);
        }
        GetMerchantComments4MemberCommand command4member = new GetMerchantComments4MemberCommand();
        command4member.setUserId(userId.toString());
        command4member.setMerchantNo(command.getMerchantNo());
        command4member.setComment(false);
        command4member.setCurPage(command.getCurPage());
        command4member.setPageSize(command.getPageSize());
        ResultDto<PageDto<CommentDto>> result = orderClient.getCommentList(command4member);
        List<Map<String, Object>> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(result.getData().getList())) {
            for (CommentDto dto : result.getData().getList()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", dto.getId());
                map.put("userId", dto.getUserId());
                map.put("merchantNo", dto.getMerchantNo());
                map.put("grade", dto.getGrade()); // 评分
                map.put("content", dto.getContent());// 内容
                map.put("labels", dto.getLabels());// 标签
                map.put("merchantName", dto.getRealName());// 显示用户名
                map.put("imageUrl", dto.getUserImage()); // 用户头像
                // map.put("money", new
                // DecimalFormat("#.00").format(dto.getAccount()));//
                // 消费金额始终两位小数
                map.put("money", String.valueOf(AmountTransferUtils.formatComma2BigDecimal(dto.getAccount())));// 消费金额始终两位小数
                map.put("createTime", dto.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));// 支付时间
                map.put("paymentDate", dto.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));// 支付时间
                map.put("paymentNo", dto.getPaymentNo()); // 支付订单编号
                list.add(map);
            }
        }
        PageDto<CommentDto> page = result.getData();
        return new PageResultDto<>(result.getResult(), result.getDescription(), new PageDto<Map<String, Object>>(page.getTotalCount(), page.getCurPage(), page.getPageSize(), list));
    }

    /**
     * 获取用户群买单未评论账单
     *
     * @param command
     * @return
     */
    @Login
    @PostMapping("/bills/groupPay")
    @Log("获取用户群买单未评论账单")
    public Object getUserNoCommentBillsForGroupPay(@RequestBody GetUserBillsCommand command, HttpServletRequest request) {
        logger.info("获取用户群买单未评论账单 > > > 入参：{}", JsonUtils.toJsonString(command));
        String userId = CommonHelper.getUserIdByRequest();
        if (null == userId || "null".equals(userId)){
            return ResultDto.result(ResultCodeEnum.System.ILLEGAL_REQUEST);
        }
        GetMerchantComments4MemberCommand command4member = new GetMerchantComments4MemberCommand();
        command4member.setUserId(userId.toString());
        command4member.setMerchantNo(command.getMerchantNo());
        command4member.setComment(false);
        command4member.setOrderSn(command.getOrderSn());
        command4member.setGroupId(command.getGroupId());
        Map<String, Object> map = new HashMap<String, Object>();
        ResultDto<CommentDto> result = orderClient.getCommentListForGroupPay(command4member);
        if(!ResultCodeEnum.System.SUCCESS.getCode().equals(result.getResult())){
            return result;
        }
        if (result.getData() != null) {
            CommentDto dto = result.getData();
            map.put("id", dto.getId());
            map.put("userId", dto.getUserId());
            map.put("merchantNo", dto.getMerchantNo());
            map.put("grade", dto.getGrade()); // 评分
            map.put("content", dto.getContent());// 内容
            map.put("labels", dto.getLabels());// 标签
            map.put("merchantName", dto.getRealName());// 显示用户名
            map.put("imageUrl", dto.getUserImage()); // 用户头像
            // map.put("money", new
            // DecimalFormat("#.00").format(dto.getAccount()));//
            // 消费金额始终两位小数
            map.put("money", String.valueOf(AmountTransferUtils.formatComma2BigDecimal(dto.getAccount())));// 消费金额始终两位小数
            map.put("createTime", dto.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));// 支付时间
            map.put("paymentDate", dto.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));// 支付时间
            map.put("paymentNo", dto.getPaymentNo()); // 支付订单编号
        }
        return new ResultDto<>(result.getResult(), result.getDescription(), map);
    }
    
    @Login
    @Log("获取订单评论状态")
    @PostMapping("/OrderCommentStatus")
	public Object getOrderCommentStatus(HttpServletRequest request,@RequestBody GetCommentStatusReq req) {
    	if (null == req || !req.validateParamIsNull("merchantNo", "paymentNo")) {
			return ResultDto.result(ResultCodeEnum.System.PARAM_NULL);
		}
    	req.setUserId((String)request.getSession().getAttribute(Constant.GL_USER_ID));
    	return commentService.getCommentStatus(req);
    }
    
    @Login
    @PostMapping("/commentPersonal/status")
	public Object getCommentPersonalStatus(HttpServletRequest request, @RequestBody Map<String, String> paymentNo) {
    	logger.info("getCommentPersonalStatus begin, userId={},paymentNo={}", (String)request.getSession().getAttribute(Constant.GL_USER_ID), paymentNo.get("paymentNo"));
    	return commentService.getTipCommentStatus(paymentNo.get("paymentNo"));
    }
}
