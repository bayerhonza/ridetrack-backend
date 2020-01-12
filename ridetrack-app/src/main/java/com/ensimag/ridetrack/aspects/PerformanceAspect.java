package com.ensimag.ridetrack.aspects;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * AOP class for performance measuring
 */
@Aspect
@Component
public class PerformanceAspect {
	
	private final Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);
	
	/**
	 * Pointcut for all the REST controllers methods
	 */
	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void restControllers() {
		// pointcut
	}
	
	/**
	 *
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("restControllers()")
	public Object measureMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.nanoTime();
		Object returnValue = joinPoint.proceed();
		long end = System.nanoTime();
		String methodName = joinPoint.getSignature().getName();
		logger.info("Execution of {}.{} took {} ms",joinPoint.getTarget().getClass().getSimpleName(), methodName, TimeUnit.NANOSECONDS.toMillis(end - start));
		return returnValue;
	}
}
