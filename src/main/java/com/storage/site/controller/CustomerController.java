package com.storage.site.controller;

import com.storage.site.model.Customer;
import com.storage.site.repository.CustomerRepository;
import com.storage.site.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/getAllCustomers")
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(
                customer -> customers.add(customer));
        return customers;
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<String> addCustomer(HttpServletRequest formData) {
        String email = formData.getParameter("email");
        String password = passwordEncoder.encode(formData.getParameter("password"));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/html; charset=UTF-8");

        if (customerService.customerExists(email)) {
            return new ResponseEntity<String>("Account Exists", headers, HttpStatus.CONFLICT);
        }

        else {
            Customer customer = new Customer(email, password, "314-442-5781", "Bjorg",
                    "Dahl", "855 Hollywood Blvd", "Apt 1315", "SC",
                    "43301", "USA", false);
            customerRepository.save(customer);

            return new ResponseEntity<String>("Saved Successfully!",  headers, HttpStatus.SEE_OTHER);
        }


    }
}

