package org.yangxin.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.yangxin.springdemo.introduction.LittleUniverse;
import org.yangxin.springdemo.introduction.impl.LittleUniverseImpl;

/**
 * @author yangxin
 * 2022/5/23 22:28
 */
@Aspect
@Component
@Order(1)
public class ServiceAspect {

	@Pointcut("execution(* org.yangxin.springdemo.service..*.*(..))")
	public void embed() {}

	@Before("embed()")
	public void before(JoinPoint joinPoint) {
		System.out.println("开始调用" + joinPoint);
	}

	@After("embed()")
	public void after(JoinPoint joinPoint) {
		System.out.println("调用完成" +joinPoint);
	}

	@Around("embed()")
	public Object aroundMe(ProceedingJoinPoint joinPoint) {
		long startTime = System.currentTimeMillis();

		Object returnValue = null;
		System.out.println("开始计时" + joinPoint);

		try {
			returnValue = joinPoint.proceed();
			System.out.println("执行成功，结束计时" + joinPoint);
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("执行失败，结束计时" + joinPoint);
		} finally {
			long endTime = System.currentTimeMillis();
			System.out.println("总耗时" + joinPoint + "[" + (endTime - startTime) + "]ms");
		}

		return returnValue;
	}

	@AfterReturning(pointcut = "embed()", returning = "returnValue")
	public void afterReturning(JoinPoint joinPoint, Object returnValue)  {
		System.out.println("无论是空还是有值都返回" + joinPoint + "，返回值[" + returnValue + "]");
	}

	@AfterThrowing(pointcut = "embed()", throwing = "exception")
	public void afterThrowing(JoinPoint joinPoint, Exception exception) {
		System.out.println("抛出异常通知" + joinPoint + " " + exception.getMessage());
	}

	@DeclareParents(value = "org.yangxin.springdemo.controller..*", defaultImpl = LittleUniverseImpl.class)
	public LittleUniverse littleUniverse;
}