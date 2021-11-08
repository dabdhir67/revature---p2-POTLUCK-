package com.revature.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    @AfterReturning(pointcut = "within(com.revature.controllers.*)", returning = "response")
    public void log(JoinPoint jp, ResponseEntity<?> response){
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        if (response.getStatusCodeValue() >= 400) {
            logger.warn(jp.getSignature().getDeclaringTypeName().split("\\.")[3] +
                    " resolved " + request.getMethod() +
                    " returning status code " + response.getStatusCode());
        } else {
            logger.info(jp.getSignature().getDeclaringTypeName().split("\\.")[3] +
                    " successfully resolved " + request.getMethod() +
                    " with status code " + response.getStatusCode());
        }
    }
}
