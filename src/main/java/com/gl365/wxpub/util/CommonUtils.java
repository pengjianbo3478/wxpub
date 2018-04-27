package com.gl365.wxpub.util;

import java.time.Instant;

import org.apache.commons.lang.StringUtils;

public class CommonUtils {

	public static String h5UrlAddTimeStamp(String url){
		if(StringUtils.isBlank(url)){
			return null;
		}
		String a[] = url.split("#");
		return String.format(a[0] + "?new=%d", Instant.now().toEpochMilli())+ "#" +( a.length > 1 ? a[1] : "");
	}
}
