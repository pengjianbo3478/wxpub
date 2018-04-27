package com.gl365.wxpub.service.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.order.req.CommentPersonal;
import com.gl365.wxpub.service.FeignCilent.CommentClient;
import com.gl365.wxpub.util.GsonUtils;

@Component
public class AsyncServiceImpl {
	
	private static final Logger LOG = LoggerFactory.getLogger(AsyncServiceImpl.class);

	@Autowired
	private CommentClient commentClient;
	
	@Async
	public void updatePersonCommentStatus(CommentPersonal req){
		LOG.info("Async updatePersonCommentStatus begin,req:{}",req.toString());
		ResultDto<?> resp = null;
		try{
			resp = commentClient.updateCommentPersonalStatus(req);
		}catch(Exception e){
			LOG.error("Async updatePersonCommentStatus ===> commentService.updateCommentPersonalStatus exception,e:"+e);
		}
		LOG.info("Async updatePersonCommentStatus end,resp:{}",GsonUtils.toJson(resp));
		return ;
	}
}
