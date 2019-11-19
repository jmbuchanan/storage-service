package com.storage.site.config;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private String secret = "secretkey";

    private long expire = 100000000;



}
