package com.gl365.wxpub.util;

import org.apache.commons.lang.StringUtils;

public class MaskUtils {

	/**
	 * 手机号掩码（前3后4）
	 * @param source
	 * @return
	 */
	public static String moblieNoMask(String source){
		if(StringUtils.isBlank(source) || source.length() != 11){
			return source;
		}
		StringBuffer rlt = new StringBuffer(source.substring(0, 3));
		for(int i = 0; i < 4 ; i++){
			rlt.append("*");
		}
		rlt.append(source.substring(source.length()-4,source.length()));
		return rlt.toString();
	}
	
}
