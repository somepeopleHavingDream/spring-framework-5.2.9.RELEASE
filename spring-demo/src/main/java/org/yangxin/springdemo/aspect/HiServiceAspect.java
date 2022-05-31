package org.yangxin.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author yangxin
 * 2022/5/31 22:47
 */
@Aspect
@Component
@Order(2)
public class HiServiceAspect {

	@Pointcut("execution(* org.yangxin.springdemo.service.impl.HelloServiceImpl.*(..))")
	public void plugin() {}

	@Before("plugin()")
	public void before(JoinPoint joinPoint) {
		System.out.println("进行before拦截" + joinPoint);
	}

	@After("plugin()")
	public void after(JoinPoint joinPoint) {
		System.out.println("进行after拦截" + joinPoint);
	}

	@AfterReturning(pointcut = "plugin()", returning = "returnValue")
	public void afterReturning(JoinPoint joinPoint, Object returnValue) {
		System.out.println("进行return拦截 " + joinPoint + "，返回值[" + returnValue + "]");
	}
}
