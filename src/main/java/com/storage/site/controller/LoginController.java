package com.storage.site.controller;

import com.storage.site.model.Customer;
import com.storage.site.repository.CustomerRepository;
import com.storage.site.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping
public class LoginController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    ResponseEntity<String> authenticate(HttpServletRequest request) {

        //Refactor to only query once

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (!customerService.customerExists(email)) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

        Customer customer = customerRepository.findByEmail(email);
        String storedPassword = customer.getPassword();

        if (passwordEncoder.matches(password, storedPassword)) {
            System.out.println("Passwords match");
            UserDetails user = customerService.loadUserByUsername(email);
            Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(x -> System.out.println("Has Role: " + x));
            return new ResponseEntity<String>(HttpStatus.OK);
        }

        return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

    }
}
