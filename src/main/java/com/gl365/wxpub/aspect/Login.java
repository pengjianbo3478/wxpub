package com.gl365.wxpub.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 公众号登录拦截器，通过openId获取userId
 * @author dfs_519
 *2017年10月12日下午12:16:04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Order(Ordered.HIGHEST_PRECEDENCE) // 优先级,暂定最高级
public @interface Login {

	boolean needLogin() default true;
}
