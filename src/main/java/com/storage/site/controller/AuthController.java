package com.storage.site.controller;

import com.storage.site.domain.Customer;
import com.storage.site.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/authenticate")
    public void authenticate(HttpServletRequest request) {
        // The jwt filter intercepts the request and evaluates
    }

    @PostMapping("/login")
    public void login(@RequestBody Customer loginRequest, HttpServletResponse response) {
        authService.login(loginRequest, response);
    }
}
