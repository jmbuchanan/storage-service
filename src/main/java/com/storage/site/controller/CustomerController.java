package com.storage.site.controller;

import com.storage.site.model.Customer;
import com.storage.site.service.CustomerService;
import com.storage.site.service.ExcelService;
import com.storage.site.service.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final ExcelService excelService;
    private final JwtService jwtService;

    @GetMapping("/getAllCustomers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/getAllCustomers/export")
    public ResponseEntity<byte[]> exportExcelWorkbook() {
        return excelService.generateCustomerWorkbook();
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customerRequest) {

        log.info(String.format("Register account request for e-mail '%s'", customerRequest.getEmail()));
        customerRequest.setEmail(customerRequest.getEmail().toLowerCase());
        Customer customer = customerService.getCustomerByEmail(customerRequest.getEmail());
        HttpHeaders headers = new HttpHeaders();

        if (isExistingCustomer(customer)) {
            log.info("Account exists.");
            return new ResponseEntity<>("Account Exists", headers, HttpStatus.CONFLICT);
        } else {
            log.info("Verified this is not an existing customer. Creating record in database");
            customer = customerService.register(customerRequest);
            if (customer.getStripeId() != null) {
                String token = jwtService.generateToken(customer);
                headers.add("Set-Cookie", "Authorization=" + token + "; Path=/");
                return new ResponseEntity<>(headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(headers, HttpStatus.REQUEST_TIMEOUT);
            }
        }
    }

    private boolean isExistingCustomer(Customer customer) {
        return customer.getId() != 0L;
    }
}

