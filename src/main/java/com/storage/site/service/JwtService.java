package com.storage.site.service;

import com.storage.site.model.Customer;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@Service
public class JwtService {

    private static Map<String, Integer> cachedUserTokens = new HashMap<>();
    private static Map<String, Integer> cachedAdminTokens = new HashMap<>();

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.secret}")
    private String secret;

    @Scheduled(cron="0 0 0 * * *", zone="America/New_York")
    private void clearCaches() {

        log.info(String.format("Clearing %d cached user tokens", cachedUserTokens.size()));
        cachedUserTokens.clear();
        log.info(String.format("Clearing %d cached admin tokens", cachedAdminTokens.size()));
        cachedAdminTokens.clear();
    }


    public String generateToken(Customer customer) {

        Map<String, Object> claims = makeClaimsFrom(customer);

        log.info("Issuing JWT");
        String jwt = Jwts.builder()
                .setSubject(customer.getEmail())
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        log.info("Adding token to user cache");
        cachedUserTokens.put(jwt, customer.getId());

        if (customer.isAdmin()) {
            log.info("Adding token to admin cache");
            cachedAdminTokens.put(jwt, customer.getId());
        }

        return jwt;
    }

    public int parseCustomerId(HttpServletRequest request) {
        String authorization = getAuthorization(request);
        if (cachedUserTokens.containsKey(authorization)) {
            return cachedUserTokens.get(authorization);
        }
        int customerId;

        if (authorization == null) {
            return 0;
        } else {
            Claims claims = parseClaims(authorization);
            try {
                customerId = (int) claims.get("customerId");
            } catch (Exception e) {
                log.info("Issue parsing customerId from jwt provided");
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
        if (cachedUserTokens.containsKey(authorization)) {
            log.info("User token found in cache");
            return true;
        }

        Claims claims = parseClaims(authorization);
        if (isDateValid(claims)) {
            log.info("Adding user token to cache");
            cachedUserTokens.put(authorization, claims.get("customerId", Integer.class));
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

        if (cachedAdminTokens.containsKey(authorization)) {
            log.info("Admin token found in cache");
            return true;
        }

        Claims claims = parseClaims(authorization);
        boolean isAdmin = claims.get("isAdmin", Boolean.class);
        if (isDateValid(claims) && isAdmin) {
            log.info("Adding admin token to cache");
            cachedAdminTokens.put(authorization, claims.get("customerId", Integer.class));
            return true;
        } else {
            return false;
        }
    }

    private String getAuthorization(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        String authorization = null;

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Authorization")) {
                authorization = cookie.getValue();
            }
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
                log.info("Token expired");
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
