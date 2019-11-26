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
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if (request.getRequestURI().equals("/login")) {
            chain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();

        for (Cookie c: cookies) {
            System.out.println("Name: " + c.getName());
            System.out.println("Value: " + c.getValue());
        }

        String requestToken = null;

        try {
            final Cookie authCookie = WebUtils.getCookie(request, "Authorization");
            requestToken = authCookie.getValue();
        } catch (Exception e) {
            e.getMessage();
        }

        String username = null;
        String jwtToken = requestToken;

        if (requestToken != null) {

            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("Token expired");
            }
        } else {
            System.out.println(requestToken);
            logger.warn("Does not start with Bearer");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.customerService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(auth);

                System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            }
        }

        chain.doFilter(request, response);

    }

}
