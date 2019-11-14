package com.storage.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping
public class AuthenticateController {

   @Autowired
   PasswordEncoder passwordEncoder;

   @GetMapping("/authenticate")
   ResponseEntity<String> getHash() {
       return new ResponseEntity<>(passwordEncoder.encode("password"), HttpStatus.ACCEPTED);
    }
}
