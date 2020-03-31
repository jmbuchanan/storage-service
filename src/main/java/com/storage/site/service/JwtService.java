package com.storage.site.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

@Service
public class JwtService {

    private long expire = 600000;

    @Value("${jwt.secret")
    private String secret;


    public String generateToken(UserDetails user) {

        Map<String, Object> adminClaim = new HashMap<>();

        boolean isAdmin = false;

        for (GrantedAuthority authority : user.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
            }
        }

        adminClaim.put("isAdmin", isAdmin);

        String jwt = Jwts.builder()
                .setSubject(user.getUsername())
                .addClaims(adminClaim)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        return jwt;

    }

    public boolean isTokenValid(HttpServletRequest request) {

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
                return false;
            }
        } else {
            System.out.println("No cookies found");
            return false;
        }

        return true;
    }
}
