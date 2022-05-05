package com.ssafy.ws.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component	// Aspect 있어도 이거까지 달아줘야 componentScan 대상이 된다.
@EnableAspectJAutoProxy		// applicationContext.xml에서 autoproxy를 달아주던 것이 이걸로 대체된다.
public class LoggingAspect {

	Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	@Before(value = "execution(* com.ssafy.ws.model..*.*(..))")
	public void beforeTest(JoinPoint joinPoint) {
		logger.info("beforeTest: 메서드 선언부 {}, 전달 파라미터 {}", joinPoint.getSignature(),
				Arrays.toString(joinPoint.getArgs()));
	}

	@AfterReturning(value = "execution(* com.ssafy.ws.model..*.*(..))")
	public void afterReturningTest(JoinPoint joinPoint) {
		logger.info("afterReturningTest: 메서드 선언부 {}, 전달 파라미터 {}", joinPoint.getSignature(),
				Arrays.toString(joinPoint.getArgs()));
	}
}
