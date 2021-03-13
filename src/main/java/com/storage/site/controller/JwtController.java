package com.storage.site.controller;

import com.storage.site.model.Customer;
import com.storage.site.service.CustomerService;
import com.storage.site.service.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@AllArgsConstructor
public class JwtController {

    private final JwtService jwtService;
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/authenticate")
    public ResponseEntity<String> authenticate(HttpServletRequest request) {
        // The jwt filter intercepts the request and evaluates
        // before this method returns
        log.info("Preflight request authorized");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> createAuthenticationToken(@RequestBody Customer loginRequest) {

        String email = loginRequest.getEmail();
        String providedPassword = loginRequest.getPassword();

        log.info(String.format("Client sending request to log in as '%s'", email));

        Customer customer = customerService.getCustomerByEmail(email);

        if (!isExistingCustomer(customer)) {
            log.info(String.format("No customer found with email '%s'. Returning 404", email));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String storedPassword = customer.getPassword();

        if (passwordEncoder.matches(providedPassword, storedPassword)) {
            String token = jwtService.generateToken(customer);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Set-Cookie", "Authorization=" + token + "; Path=/");
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } else {
            log.info("Password does not match");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    private boolean isExistingCustomer(Customer customer) {
        return customer.getId() != 0L;
    }
}
