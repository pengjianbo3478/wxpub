package com.gl365.wxpub.service.FeignCilent;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gl365.wxpub.dto.settlement.req.SettlementReq;
import com.gl365.wxpub.dto.settlement.resp.PayMain;
import com.gl365.wxpub.dto.settlement.resp.PayMainDetailRlt;


@FeignClient(name="settlement",url="${${env:}.url.settlement:}")
public interface SettlementClient {

	@RequestMapping(value = "/paymain/queryUserPayMainDetail", method = RequestMethod.POST)
	PayMainDetailRlt<PayMain> queryUserPayMainDetail(@RequestBody SettlementReq req);
}
