package com.gl365.wxpub.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl365.wxpub.dto.h5.resp.BillPageDto;
import com.gl365.wxpub.dto.h5.resp.BillPageResponse;
import com.gl365.wxpub.dto.settlement.req.SettlementReq;
import com.gl365.wxpub.dto.settlement.resp.BillDto;
import com.gl365.wxpub.dto.settlement.resp.PayMain;
import com.gl365.wxpub.dto.settlement.resp.PayMainDetailRlt;
import com.gl365.wxpub.enums.ResultCodeEnum;
import com.gl365.wxpub.service.SettlementService;
import com.gl365.wxpub.service.FeignCilent.SettlementClient;

@Service
public class SettlementServiceImpl implements SettlementService{
	
	private static final Logger logger = LoggerFactory.getLogger(SettlementServiceImpl.class);
	
	private static final String _0 = "0";
	private static final String _1 = "1";
	private static final String _2 = "2";
	private static final String _3 = "3";
	private static final String _4 = "4";
	private static final String _5 = "5";
	private static final String _7 = "7";

	@Autowired
	private SettlementClient settlementClient;
	
	@Override
	public BillPageResponse<?> queryBillDetail(SettlementReq reqParam) {
		logger.info("queryBillDetail begin,reqParam:{}",reqParam.toString());
		BillPageResponse<?> resp = null;
		PayMainDetailRlt<PayMain> rlt = null;
		try {
			rlt = settlementClient.queryUserPayMainDetail(reqParam);
		} catch (Exception e) {
			logger.error("queryBillDetail ===> settlementClient.queryUserPayMainDetail exception,e:{}", e);
		}
		if (null != rlt) {
			if (ResultCodeEnum.System.SUCCESS.getCode().equals(rlt.getResultCode())) {
				logger.info("getBill ===> settlementService.queryUserPayMainDetail success rlt:{}", rlt.toString());
				List<BillDto> dataList = buildBillResp(rlt.getData());
				BillPageDto<BillDto> data = new BillPageDto<BillDto>(rlt.getTotalNum(), reqParam.getPageNum(), reqParam.getNumPerPage(), dataList, rlt.getTotalMoney(), rlt.getTotalBeanAmount(), rlt.getTotalGiftAmount());
				resp = new BillPageResponse<>(ResultCodeEnum.System.SUCCESS, data);
			} else {
				logger.error("getBill ===> settlementService.queryUserPayMainDetail error,rlt:{}", rlt);
				resp = BillPageResponse.settlementRltConvert(rlt.getResultCode(), rlt.getResultDesc());
			}
		} else {
			resp = BillPageResponse.settlementError(ResultCodeEnum.Settlement.GET_BILL_FAIL);
		}
		return resp;
	}
	
	private List<BillDto> buildBillResp(List<PayMain> list) {
		List<BillDto> dataList = new ArrayList<>();
		if (list == null) {
			return dataList;
		}
		for (PayMain pm : list) {
			BillDto bd = new BillDto();
			if(_7.equals(pm.getOrderType())){
				bd.setReturnHappyCoin(pm.getGroupGiftAmount());
				if("0".equals(pm.getSplitFlag())){
					bd.setMoney(pm.getGroupMainuserPay());
					bd.setHappyCoin(pm.getGroupMainuserPayBean());
				}else{
					bd.setMoney(pm.getTotalAmount());
					bd.setHappyCoin(pm.getBeanAmount());
				}
			}else{
				bd.setHappyCoin(pm.getBeanAmount());
				bd.setMoney(pm.getTotalAmount());
				bd.setReturnHappyCoin(pm.getGiftAmount());
			}
			bd.setOrderType(pm.getOrderType());
			bd.setMerchantName(pm.getMerchantName());
			bd.setMerchantNo(pm.getMerchantNo());
			bd.setOperation(pm.getPayStatus());
			if ("1000".equals(pm.getTransType()) || "1100".equals(pm.getTransType()) || "4100".equals(pm.getTransType())) {
				if(_2.equals(pm.getOrderType())){
					bd.setOperationName("打赏");
				}else if(_7.equals(pm.getOrderType())){
					bd.setOperationName("乐抢单");
				}else{
					bd.setOperationName("消费");
				}
			} else if ("3000".equals(pm.getTransType()) || "3100".equals(pm.getTransType()) || "3200".equals(pm.getTransType()) || "3300".equals(pm.getTransType())) {
				bd.setOperationName("退款");
				bd.setPerOrderNo(pm.getOrigMerchantOrderNo());
			}
			bd.setPaymentDate(pm.getPayTime());
			bd.setRealMoney(pm.getCashAmount());
			bd.setOrderNo(pm.getMerchantOrderNo());
			bd.setReturnNo(pm.getPayId());
			if(StringUtils.isNotBlank(pm.getOrigPayId())){
				bd.setPaymentNo(pm.getOrigPayId());
			}else{
				bd.setPaymentNo(pm.getPayId());
			}
			if ("00".equals(pm.getScene()) || _0.equals(pm.getScene())) {
				bd.setScene(0); 
				bd.setSceneName("快捷支付");
			}else if ("01".equals(pm.getScene()) || "1".equals(pm.getScene())) {
				bd.setScene(1); 
				bd.setSceneName("付款码支付");
			} else if ("02".equals(pm.getScene()) || _2.equals(pm.getScene())) {
				bd.setScene(2); 
				bd.setSceneName("C扫B支付");
			} else if ("03".equals(pm.getScene()) || _3.equals(pm.getScene())) {
				bd.setScene(3); 
				bd.setSceneName("POS支付");
			} else if ("04".equals(pm.getScene()) || _4.equals(pm.getScene()) || "05".equals(pm.getScene()) || _5.equals(pm.getScene())) {
				bd.setScene(4); 
				bd.setSceneName("微信支付");
			}
			bd.setNoBenefitAmount(pm.getNoBenefitAmount());
			dataList.add(bd);
		}
		return dataList;
	}

}
