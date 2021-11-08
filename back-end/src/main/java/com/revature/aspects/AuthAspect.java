package com.revature.aspects;

import com.revature.models.Chef;
import com.revature.utilities.SecurityUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthAspect {
    private final Logger logger = LogManager.getLogger(AuthAspect.class);

    @Around("execution(* com.revature.controllers.*.*(..))" +
            "&& !@annotation(com.revature.aspects.annotations.NoAuthIn)" +
            "&& !@target(com.revature.aspects.annotations.NoAuthIn)")
    public ResponseEntity<?> authenticateToken(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        if (token == null) {
            logger.warn("No Authorization Token Received");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            Chef chef = SecurityUtil.validateToken(token);
            if (chef == null) {
                logger.warn("Received Invalid Token");
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            } else {
                logger.info("Received Valid Token");
                request.setAttribute("chef", chef);
                request.setAttribute("id", chef.getC_id());
                request.setAttribute("passkey", chef.getPasskey());
                return (ResponseEntity<?>) pjp.proceed();
            }
        }
    }
}
