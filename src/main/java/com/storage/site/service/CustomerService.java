package com.storage.site.service;

import com.storage.site.dao.CustomerDao;
import com.storage.site.model.Customer;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerDao customerDao;
    private final StripeService stripeService;
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
        String stripeId = stripeService.createCustomer(customer.getEmail());
        customer.setStripeId(stripeId);
        customer.setDateJoined(new Date());
        customer.encodePassword(passwordEncoder);
        int id = customerDao.insertCustomer(customer);
        customer.setId(id);
        return customer;
    }
}
