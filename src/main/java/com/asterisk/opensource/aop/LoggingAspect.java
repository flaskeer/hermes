package com.asterisk.opensource.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
public class LoggingAspect {

    private  final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(com.asterisk.opensource..*)")
    public void loggingPointcut() {}

    @AfterThrowing(pointcut = "loggingPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        LOGGER.error("Exception in {}.{}() with cause = {} and exception {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), e.getCause(), e);

    }


    @Around("loggingPointcut()")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Enter : {}.{}() with argument[s] = {}",proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                    proceedingJoinPoint.getSignature().getName(), Arrays.toString(proceedingJoinPoint.getArgs()));
        }
        try {
            Object result = proceedingJoinPoint.proceed();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Exit :{}.{} with result = {}",proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                        proceedingJoinPoint.getSignature().getName(),result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            LOGGER.error("Illegal argument : {} in {}.{}()",Arrays.toString(proceedingJoinPoint.getArgs()),
                    proceedingJoinPoint.getSignature().getDeclaringTypeName(),proceedingJoinPoint.getSignature().getName());
            throw e;
        }
    }

}
