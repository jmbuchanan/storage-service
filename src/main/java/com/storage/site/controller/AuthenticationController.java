package com.storage.site.controller;

import com.storage.site.config.JwtTokenUtil;
import com.storage.site.repository.CustomerRepository;
import com.storage.site.service.CustomerService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    ResponseEntity<String> createAuthenticationToken(HttpServletRequest request) {

        //Refactor to only query once

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserDetails user = customerService.loadUserByUsername(email);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String storedPassword = user.getPassword();

        if (passwordEncoder.matches(password, storedPassword)) {
            Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
            final String token = jwtTokenUtil.generateToken(user);
            System.out.println("Login controller token generated: " + token);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Set-Cookie", "Authorization=" + token);
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    // doesn't do anything right now
    @GetMapping("/getUserDetails")
    ResponseEntity<String> getUserRole(HttpServletRequest request) {

        String authorization = null;

        Cookie[] cookies = request.getCookies();

        if (cookies.length > 0) {
            for (Cookie c : cookies) {
                if (c.getName() == "Authorization") {
                    authorization = c.getValue();
                }
            }
        } return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
