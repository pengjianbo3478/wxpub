package com.gl365.wxpub.util;

import java.security.MessageDigest;

public class MD5Utils {  
    
	private static final String SALT = "GL_WXPUB_OPENID_MD5_KEY";
	
    /** 
     * md5加密方法 
     * @param str 
     * @return 
     */  
	public static String md5(String str) {
		try {
			str = SALT + str;
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();

			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (Exception e) {
			return "";
		}
	}  
}