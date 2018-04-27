package com.gl365.wxpub.service;

import com.gl365.wxpub.dto.h5.resp.BillPageResponse;
import com.gl365.wxpub.dto.settlement.req.SettlementReq;

public interface SettlementService {

	BillPageResponse<?> queryBillDetail(SettlementReq reqParam);
}
