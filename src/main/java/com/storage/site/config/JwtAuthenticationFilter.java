package com.storage.site.config;

import com.storage.site.service.CustomerService;
import com.storage.site.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
/*
    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtService jwtService;
*/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        chain.doFilter(request, response);
        /*
        List<String> whiteList = new ArrayList<>();

        whiteList.add("/login");
        whiteList.add("/customers/addCustomer");

        if (whiteList.contains(request.getRequestURI())) {
            chain.doFilter(request, response);
        }

        if (jwtService.isTokenValid(request)) {
            chain.doFilter(request, response);
        }

        response.setStatus(401);
        chain.doFilter(request, response);
*/
    }
}
