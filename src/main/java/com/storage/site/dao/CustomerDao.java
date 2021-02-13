package com.storage.site.dao;

import com.storage.site.model.Customer;
import com.storage.site.model.rowmapper.CustomerRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    private static final String SELECT_ALL_CUSTOMERS =
            "SELECT * FROM customers";

    private static final String SELECT_CUSTOMER_BY_ID =
            "SELECT * FROM customers WHERE id = ?";

    public CustomerDao(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    public List<Customer> getAllCustomers() {
        try {
            return jdbcTemplate.query(SELECT_ALL_CUSTOMERS, customerRowMapper);
        } catch (EmptyResultDataAccessException e) {
            e.getMessage();
            return new ArrayList<>();
        }
    }

    public Customer getCustomerById(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_CUSTOMER_BY_ID, new Object[] {id}, customerRowMapper);
        } catch (EmptyResultDataAccessException e) {
            e.getMessage();
            return new Customer();
        }
    }
}
