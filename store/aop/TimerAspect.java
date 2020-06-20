package cn.tedu.store.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimerAspect {
	
	@Around("execution(* cn.tedu.store.service.impl.*.*(..))")
	public Object aaaaa(ProceedingJoinPoint pjp) throws Throwable {
		// 记录起始时间
		long start = System.currentTimeMillis();
		
		// 相当于执行了业务方法，例如注册、登录等
		Object object = pjp.proceed();
		
		// 记录结束时间
		long end = System.currentTimeMillis();
		// 计算耗时
		System.err.println("执行耗时：" + (end - start) + "ms.");
		// 返回
		return object;
	}

}
