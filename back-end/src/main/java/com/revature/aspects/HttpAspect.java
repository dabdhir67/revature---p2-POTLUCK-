package com.revature.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Aspect
@Component
public class HttpAspect {

    @AfterReturning(pointcut = "within(com.revature.controllers.*)", returning = "response")
    public ResponseEntity<?> allowCustomHeaders(ResponseEntity<?> response) throws Throwable {

        if (response != null && !response.getHeaders().keySet().isEmpty()) {
            HttpHeaders headers = HttpHeaders.writableHttpHeaders(response.getHeaders());
            headers.setAccessControlExposeHeaders(new ArrayList<>(response.getHeaders().keySet()));
            return new ResponseEntity<>(response.getBody(), headers, response.getStatusCode());
        }
        return response;
    }

}
