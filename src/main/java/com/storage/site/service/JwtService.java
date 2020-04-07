package com.storage.site.service;

import com.storage.site.model.Customer;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

@Service
public class JwtService {

    private long expire = 600000;

    @Value("${jwt.secret}")
    private String secret;


    public String generateToken(Customer customer) {

        Map<String, Object> adminClaim = new HashMap<>();

        adminClaim.put("isAdmin", customer.isAdmin());

        String jwt = Jwts.builder()
                .setSubject(customer.getEmail())
                .setClaims(adminClaim)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        return jwt;
    }

    public boolean validateUser(HttpServletRequest request) {

        String authorization = getAuthorization(request);

        if (authorization != null && isDateValid(authorization)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateAdmin(HttpServletRequest request) {
        String authorization = getAuthorization(request);

        if (authorization == null) {
            return false;
        }

        if (!isDateValid(authorization)) {
            return false;
        }

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization).getBody();
        boolean isAdmin = claims.get("isAdmin", Boolean.class);
        return (validateUser(request) && isAdmin);
    }

    private String getAuthorization(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        String authorization = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization")) {
                    authorization = cookie.getValue();
                }
            }
            if (authorization == null) {
                System.out.println("No authorization");
            }
        } else {
            System.out.println("No cookies found");
        }

        return authorization;
    }

    private boolean isDateValid(String authorization) {

        Date now = new Date(System.currentTimeMillis());

        try {

            Date expiration = Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization).getBody().getExpiration();
            if (expiration.before(now)) {
                System.out.println("Token expired");
                return false;
            }

            Date issuedAt = Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization).getBody().getIssuedAt();
            if (issuedAt.after(expiration) || issuedAt.after(now)) {
                System.out.println("iat claim is after expiration or current timestamp");
                return false;
            }

        } catch (JwtException e) {
            e.getMessage();
            return false;
        }

        return true;
    }
}
