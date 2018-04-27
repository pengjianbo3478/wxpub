package com.gl365.wxpub.service;

import com.gl365.wxpub.dto.ResultDto;
import com.gl365.wxpub.dto.merchant.req.GetCommentStatusReq;

public interface CommentService {

	/**
	 * 查询评论状态
	 * @param req
	 * @return
	 */
	ResultDto<?> getCommentStatus(GetCommentStatusReq req);
	
	ResultDto<?> getTipCommentStatus(String req);
}
