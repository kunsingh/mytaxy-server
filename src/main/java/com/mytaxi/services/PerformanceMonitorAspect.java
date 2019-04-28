package com.mytaxi.services;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Aspect
@Configuration
public class PerformanceMonitorAspect {

	private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

	@Around("@annotation(com.mytaxi.services.TrackTime)")
	public void around(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		logger.info("Method " + joinPoint + " execution started at:" + new Date());
		try {
			joinPoint.proceed();
		}
		finally {
			long end = System.currentTimeMillis();
			long time = end - start;
			logger.info("Method "+joinPoint+" execution lasted:"+time+" ms");
			logger.info("Method "+joinPoint+" execution ended at:"+new Date());

			if (time > 10){
				logger.warn("Method execution longer than 10 ms!");
			}
		}
	}
}