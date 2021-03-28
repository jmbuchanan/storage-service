package com.storage.site.service;

import com.storage.site.dao.CustomerDao;
import com.storage.site.model.Customer;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;

    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    public Customer getCustomerById(int id) {
        return customerDao.getCustomerById(id);
    }

    public Customer getCustomerByEmail(String email) {
        return customerDao.getCustomerByEmail(email);
    }

    public Customer register(Customer customer) {
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("email", customer.getEmail());
        com.stripe.model.Customer stripeCustomer;
        String stripeCustomerId;
        try {
            stripeCustomer = com.stripe.model.Customer.create(customerParams);
            stripeCustomerId = stripeCustomer.getId();
        } catch (StripeException e) {
            e.getMessage();
            return customer;
        }

        customer.setStripeId(stripeCustomerId);
        customer.setDateJoined(new Date());
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        int customerId = customerDao.insertCustomer(customer);
        customer.setId(customerId);

        return customer;
    }
}
