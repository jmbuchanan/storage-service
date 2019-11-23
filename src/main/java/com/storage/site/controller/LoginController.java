package com.storage.site.controller;

import com.storage.site.config.JwtTokenUtil;
import com.storage.site.repository.CustomerRepository;
import com.storage.site.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class LoginController {

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
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

        String storedPassword = user.getPassword();

        if (passwordEncoder.matches(password, storedPassword)) {
            Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
            final String token = jwtTokenUtil.generateToken(user);
            System.out.println(token);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Set-Cookie", "Authorization=Bearer " + token);
            return new ResponseEntity<String>(headers, HttpStatus.OK);
        }

        return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

    }
}
