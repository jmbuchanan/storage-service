package com.storage.site.service;

import com.storage.site.model.Customer;
import com.storage.site.model.rowmapper.CustomerRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CustomerRowMapper customerRowMapper;

    public List<Customer> getAllCustomers() {

        List<Customer> customers = jdbcTemplate.query("SELECT * FROM customers", customerRowMapper);

        return customers;
    }

    public Customer getCustomerByEmail(String email) {

        Object [] sqlParam = {email.toLowerCase()};

        try {
            Customer customer = jdbcTemplate.queryForObject("SELECT * FROM customers WHERE email LIKE ?", sqlParam, customerRowMapper);
            return customer;

        } catch (EmptyResultDataAccessException e) {
            e.getMessage();
        }

        return new Customer();
    }

    public boolean save(Customer customer) {

        jdbcTemplate.update(
            "INSERT INTO "
            + "customers(email, password, phone_number, first_name, last_name, "
            + "  street_address, second_street_address, state, zip, country, is_admin) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            customer.getEmail(),
            customer.getPassword(),
            customer.getPhoneNumber(),
            customer.getFirstName(),
            customer.getLastName(),
            customer.getStreetAddress(),
            customer.getSecondStreetAddress(),
            customer.getState(),
            customer.getZip(),
            customer.getCountry(),
            false
        );

        return true;
    }
}
