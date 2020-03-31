package com.storage.site.service;


import com.storage.site.model.Customer;
import com.storage.site.model.rowmapper.CustomerRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CustomerRowMapper customerRowMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        String [] sqlParam = {email};

        List<Customer> customers = jdbcTemplate.query("SELECT * FROM customers WHERE email LIKE ?", sqlParam, customerRowMapper);

        if (customers == null) {
            throw new UsernameNotFoundException(email);
        }

        Customer customer = customers.get(0);

        customer.setAuthorities();
        return customer;

    }

    public List<Customer> getAllCustomers() {

        List<Customer> customers = jdbcTemplate.query("SELECT * FROM customers", customerRowMapper);

        return customers;
    }
}
