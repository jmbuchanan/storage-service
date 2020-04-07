package com.storage.site.controller;

import com.storage.site.model.Customer;
import com.storage.site.service.CustomerService;
import com.storage.site.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


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
        // This request will pass through jwt filter and return 403
        // if there is no valid jwt
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<String> createAuthenticationToken(HttpServletRequest request) {

        String email = request.getParameter("email");
        String providedPassword = request.getParameter("password");

        Customer customer = customerService.getCustomerByEmail(email);

        if (!isExistingCustomer(customer)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String storedPassword = customer.getPassword();

        if (passwordEncoder.matches(providedPassword, storedPassword)) {
            String token = jwtService.generateToken(customer);
            System.out.println("Issuing JWT: " + token);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Set-Cookie", "Authorization=" + token);
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    private boolean isExistingCustomer(Customer customer) {
        return customer.getId() != 0L;
    }
}

