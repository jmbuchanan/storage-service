package com.storage.site.service;

import com.storage.site.model.Customer;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class JwtService {

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Customer customer) {

        Map<String, Object> claims = makeClaimsFrom(customer);

        String jwt = Jwts.builder()
                .setSubject(customer.getEmail())
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        return jwt;
    }

    public int parseCustomerId(HttpServletRequest request) {
        String authorization = getAuthorization(request);
        int customerId;

        if (authorization == null) {
            return 0;
        } else {
            Claims claims = parseClaims(authorization);
            try {
                customerId = (int) claims.get("customerId");
            } catch (Exception e) {
                System.out.println("Issue parsing customerId from jwt provided");
                return 0;
            }
                return customerId;
        }
    }

    private Map<String, Object> makeClaimsFrom(Customer customer) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("customerId", customer.getId());
        if (customer.getStripeId() != null) {
            claims.put("customerStripeId", customer.getStripeId());
        }
        claims.put("firstName", customer.getFirstName());
        claims.put("isAdmin", customer.isAdmin());

        return claims;
    }

    public boolean validateUser(HttpServletRequest request) {
        String authorization = getAuthorization(request);
        if (authorization == null) {
            return false;
        }
        Claims claims = parseClaims(authorization);
        return isDateValid(claims);
    }

    public boolean validateAdmin(HttpServletRequest request) {
        String authorization = getAuthorization(request);
        if (authorization == null) {
            return false;
        }
        Claims claims = parseClaims(authorization);
        boolean isAdmin = claims.get("isAdmin", Boolean.class);
        return isDateValid(claims) && isAdmin;
    }

    private String getAuthorization(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        String authorization = null;

        if (cookies == null) {
            System.out.println("No Cookies Found");
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Authorization")) {
                authorization = cookie.getValue();
            }
        }
        if (authorization == null) {
            System.out.println("No authorization");
        }

        return authorization;
    }

    private boolean isDateValid(Claims claims) {
        if (claims == null) {
            return false;
        }

        Date now = new Date(System.currentTimeMillis());

        try {

            Date expiration = claims.getExpiration();
            if (expiration.before(now)) {
                System.out.println("Token expired");
                return false;
            }

        } catch (JwtException e) {
            e.getMessage();
            return false;
        }

        return true;
    }

    private Claims parseClaims(String authorization) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization).getBody();
        } catch (Exception e) {
            e.getMessage();
        }
        return claims;
    }
}
