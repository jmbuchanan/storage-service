package com.storage.site.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class LoggingConfig {

    @Before("execution(* com.storage.site.controller.*.*(..))")
    public void logRequestStart() throws Throwable {
        beforeLog();
    }
    private void beforeLog() {
        log.info("");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info(request.getMethod() + " " + request.getRequestURI());
    }
}
