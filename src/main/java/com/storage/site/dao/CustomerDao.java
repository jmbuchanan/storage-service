package com.storage.site.dao;

import com.storage.site.model.Customer;
import com.storage.site.model.rowmapper.CustomerRowMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    private static final String SELECT_ALL_CUSTOMERS =
            "SELECT * FROM customers";

    private static final String SELECT_CUSTOMER_BY_ID =
            "SELECT * FROM customers WHERE id = ?";

    private static final String SELECT_CUSTOMER_BY_EMAIL =
            "SELECT * FROM customers WHERE email LIKE ?";

    private static final String INSERT_CUSTOMER =
            "INSERT INTO "
                    + "customers(stripe_id, email, password, phone_number, first_name, last_name, "
                    + "  street_address, second_street_address, city, state, zip, date_joined, is_admin) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?::state, ?, ?, ?) ";

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

    public Customer getCustomerByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(SELECT_CUSTOMER_BY_EMAIL, new Object[] {email}, customerRowMapper);
        } catch (EmptyResultDataAccessException e) {
            e.getMessage();
            return new Customer();
        }
    }

    public void insertCustomer(Customer customer) {
        jdbcTemplate.update(
                INSERT_CUSTOMER,
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
}
