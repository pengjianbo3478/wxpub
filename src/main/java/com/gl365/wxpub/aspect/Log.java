package com.gl365.wxpub.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Log {
	
	String value() default "";
	
	/**
	 *执行前是否输出参数
	 *@param
	 *@author:summer
	 *@date:2017年6月13日 下午2:23:07
	 */
	boolean before() default true;
	
	/**
	 *执行后是否输出结果
	 *@param
	 *@author:summer
	 *@date:2017年6月13日 下午2:23:32
	 */
	boolean after() default true;
	
	/**
	 *只输出接口耗时，不输出参数，结果
	 *@param
	 *@author:summer
	 *@date:2017年6月13日 下午2:18:16
	 */
	boolean duration() default false;
}
