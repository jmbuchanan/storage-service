package com.storage.site.controller;

import com.storage.site.model.Customer;
import com.storage.site.service.CustomerService;
import com.storage.site.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


@RestController
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/authenticate")
    public ResponseEntity<String> authenticate(HttpServletRequest request) {
        // The jwt filter intercepts the request and evaluates
        // before this method returns

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<String> createAuthenticationToken(HttpServletRequest request) {

        String email = request.getParameter("email");
        String providedPassword = request.getParameter("password");

        System.out.println(email);
        System.out.println(providedPassword);

        Customer customer = customerService.getCustomerByEmail(email);

        if (!isExistingCustomer(customer)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String storedPassword = customer.getPassword();

        if (passwordEncoder.matches(providedPassword, storedPassword)) {
            String token = jwtService.generateToken(customer);
            System.out.println("Issuing JWT: " + token);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Set-Cookie", "Authorization=" + token + "; Path=/");
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    private boolean isExistingCustomer(Customer customer) {
        return customer.getId() != 0L;
    }
}

