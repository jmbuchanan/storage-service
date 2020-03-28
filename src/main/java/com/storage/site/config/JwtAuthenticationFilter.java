package com.storage.site.config;

import com.storage.site.service.CustomerService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        List<String> whiteList = new ArrayList<>();

        whiteList.add("/login");
        whiteList.add("/customers/addCustomer");

        if (whiteList.contains(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();

        /*minimal passing of security chain happens like this:
            customer implements userdetails ->  ? - > authentication object -> set authentication in applicationContextHolder.getContext.setAuthorization?
         */

        String authorization = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization")) {
                    authorization = cookie.getValue();
                }
            } if (authorization == null) {
                System.out.println("No authorization");
            }
        }

    }

}
