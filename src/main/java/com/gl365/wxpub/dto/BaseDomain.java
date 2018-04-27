package com.gl365.wxpub.dto;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.commons.lang.StringUtils;

import com.gl365.wxpub.util.GsonUtils;


public class BaseDomain implements Serializable{

	private static final long serialVersionUID = 8993191088154863702L;

	@Override
	public String toString() {
		return GsonUtils.toJson(this);
	}

	/**
	 * 参数非空校验，目前只写了基本类型的校验，其他需要自己添加
	 * @param fieldNames
	 * @return
	 */
	public boolean validateParamIsNull(String... fieldNames) {
		for (String fieldName : fieldNames) {
			try {
				String firstLetter = getMethodName(fieldName);
				String getter = "get" + firstLetter;
				Method m = (Method) this.getClass().getMethod(getter);

				Field field = this.getClass().getDeclaredField(fieldName);
				if (field != null) {
					if ("class java.lang.String".equals(field.getGenericType().toString())) {
						String val = (String) m.invoke(this);
						if (StringUtils.isEmpty(val)) {
							System.out.println("参数"+fieldName+"is null");
							return false;
						}
					} else if ("class java.lang.Integer".equals(field.getGenericType().toString())) {
						Integer val = (Integer) m.invoke(this);
						if(val == null){
							System.out.println("参数"+fieldName+"is null");
							return false;
						}
					} else if ("class java.lang.Double".equals(field.getGenericType().toString())) {
						Double val = (Double) m.invoke(this);
						if(val == null){
							System.out.println("参数"+fieldName+"is null");
							return false;
						}
					} else if ("class java.lang.Boolean".equals(field.getGenericType().toString())) {
						Boolean val = (Boolean) m.invoke(this);
						if(val == null){
							System.out.println("参数"+fieldName+"is null");
							return false;
						}
					} else if ("class java.time.LocalDateTime".equals(field.getGenericType().toString())) {
						LocalDateTime val = (LocalDateTime) m.invoke(this);
						if(val == null){
							System.out.println("参数"+fieldName+"is null");
							return false;
						}
					} else if ("class java.time.LocalDate".equals(field.getGenericType().toString())) {
						LocalDate val = (LocalDate) m.invoke(this);
						if(val == null){
							System.out.println("参数"+fieldName+"is null");
							return false;
						}
					} else if ("class java.lang.Short".equals(field.getGenericType().toString())) {
						Short val = (Short) m.invoke(this);
						if(val == null){
							System.out.println("参数"+fieldName+"is null");
							return false;
						}
					} else if("class java.math.BigDecimal".equals(field.getGenericType().toString())){
						BigDecimal val = (BigDecimal) m.invoke(this);
						if(val == null){
							System.out.println("参数"+fieldName+"is null");
							return false;
						}
					}
					//如果是boolean类型的话自己添加

				} else {
					System.out.println(this.getClass().getName()+"参数"+fieldName+"不存在");
					return false;
				}

			} catch (Exception e) {
				System.out.println("validateParamIsNull exception");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 把一个字符串的第一个字母大写
	 * @param fildeName
	 * @return
	 * @throws Exception
	 */
    private static String getMethodName(String fildeName) throws Exception{  
        byte[] items = fildeName.getBytes();
        if(items[0] < 'a'){
        	return fildeName;
        }else{
        	items[0] = (byte) ((char) items[0] - 'a' + 'A');  
        	return new String(items);  
        }
    }  
}
