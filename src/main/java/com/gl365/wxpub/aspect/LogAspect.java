package com.gl365.wxpub.aspect;

import com.gl365.wxpub.util.GsonUtils;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
@Aspect
public class LogAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *) && @annotation(Log)")
	public void advice(){}
	
	@Pointcut("@annotation(Log)")
	public void exeAdvice(){}
	
	@Around("exeAdvice()")
	public Object aroundAdvice(ProceedingJoinPoint point) throws Throwable{
		Signature signature = point.getSignature();
		Object[] args = point.getArgs();
		long time1=System.currentTimeMillis();  
        String className=point.getTarget().getClass().getSimpleName();  
        String methodName=signature.getName(); 
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        ApiOperation api = method.getAnnotation(ApiOperation.class);
        Log log = method.getAnnotation(Log.class);
        String apidesc ="";
        if(api != null){
        	apidesc = api.value();
        }else{
        	apidesc = log.value();
        }
        List<Object> params = new ArrayList<>();
		for (Object arg : args) {
			if (arg instanceof ServletRequest){
				continue;
			}else if(arg instanceof ServletResponse){
				continue;
			}
			params.add(arg);
		}
		if(log.before() && !log.duration()){
        	logger.info("=====>类名：[{}]，方法：[{}],描述：[{}]，请求参数req:{}",className,methodName,apidesc,GsonUtils.toJson(params));
        }
		Object obj = point.proceed();
		long time2=System.currentTimeMillis(); 
		if(log.duration()){
			logger.info("=====>类名：[{}]，方法：[{}],描述：[{}]，方法执行时长：{}ms",className,methodName,apidesc,time2-time1);
		}else{
			if(log.after()){
				logger.info("=====>类名：[{}]，方法：[{}],描述：[{}]，方法执行时长：{}ms，返回结果rsp:{}",className,methodName,apidesc,time2-time1,GsonUtils.toJson(obj));
			}
		}
		return obj;
	}
	
	@AfterThrowing(value ="exeAdvice()",throwing ="e")
	public void afterThrowingAdvice(JoinPoint joinPoint, Exception e){
		String className=joinPoint.getTarget().getClass().getSimpleName();  
        String methodName=joinPoint.getSignature().getName(); 
        logger.error("====>方法执行异常，类名：[{}]，方法：[{}]，异常:{}",className,methodName,e);
	}
}
