package com.storage.site.service;

import com.storage.site.model.Customer;
import com.storage.site.model.rowmapper.CustomerRowMapper;
import com.stripe.exception.StripeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CustomerService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CustomerRowMapper customerRowMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Customer> getAllCustomers() {

        List<Customer> customers = jdbcTemplate.query("SELECT * FROM customers", customerRowMapper);

        return customers;
    }

    public Customer getCustomerByEmail(String email) {

        Object [] sqlParam = {email.toLowerCase()};
        String sql = "SELECT * FROM customers WHERE email LIKE ?;";

        try {
            Customer customer = jdbcTemplate.queryForObject(sql, sqlParam, customerRowMapper);
            return customer;

        } catch (EmptyResultDataAccessException e) {
            e.getMessage();
        }

        return new Customer();
    }

    public void save(Customer customer) {

        jdbcTemplate.update(
            "INSERT INTO "
            + "customers(stripe_id, email, password, phone_number, first_name, last_name, "
            + "  street_address, second_street_address, city, state, zip, date_joined, is_admin) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?::state, ?, ?, ?)",
            customer.getStripeId(),
            customer.getEmail(),
            customer.getPassword(),
            customer.getPhoneNumber(),
            customer.getFirstName(),
            customer.getLastName(),
            customer.getStreetAddress(),
            customer.getSecondStreetAddress(),
            customer.getCity(),
            customer.getState().toString(),
            customer.getZip(),
            customer.getDateJoined(),
            false
        );
    }

    public Customer register(Customer customer) {

        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("email", customer.getEmail());
        com.stripe.model.Customer stripeCustomer = null;
        String stripeCustomerId = null;
        try {
            stripeCustomer = com.stripe.model.Customer.create(customerParams);
            stripeCustomerId = stripeCustomer.getId();
        } catch (StripeException e) {
            e.getMessage();
        }

        customer.setStripeId(stripeCustomerId);
        //call setter method to sanitize email
        customer.setEmail(customer.getEmail());
        //call setter method to hash password
        customer.setPassword(customer.getPassword());
        customer.setDateJoined(new Date());

        save(customer);

        log.info("Querying record to return database generated customer ID");
        return getCustomerByEmail(customer.getEmail());
    }
}
