package com.storage.site.controller;

import com.storage.site.model.Customer;
import com.storage.site.service.CustomerService;
import com.storage.site.service.ExcelService;
import com.storage.site.service.JwtService;
import com.stripe.exception.StripeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    ExcelService excelService;

    @Autowired
    JwtService jwtService;


    @GetMapping("/getAllCustomers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/getAllCustomers/export")
    public ResponseEntity<byte[]> exportExcelWorkbook() {
        return excelService.generateCustomerWorkbook();
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<String> addCustomer(HttpServletRequest request) {

        String email = request.getParameter("email");
        log.info(String.format("Register account request for e-mail \'%s\'", email));

        Customer customer = customerService.getCustomerByEmail(email);

        HttpHeaders headers = new HttpHeaders();

        if (isExistingCustomer(customer)) {
            log.info("Account exists.");
            return new ResponseEntity<>("Account Exists", headers, HttpStatus.CONFLICT);

        } else {

            log.info("Verified this is not an existing customer. Creating record in database");
            customer = customerService.createCustomerFromRequest(request);

            log.info("Querying record to return database generated customer ID");
            customerService.save(customer);

            //query the saved record because it includes the db-generated customer Id
            customer = customerService.getCustomerByEmail(customer.getEmail());

            String token = jwtService.generateToken(customer);

            headers.add("Set-Cookie", "Authorization=" + token + "; Path=/");

            return new ResponseEntity<>(headers, HttpStatus.OK);
        }
    }

    private boolean isExistingCustomer(Customer customer) {
        return customer.getId() != 0L;
    }

}

