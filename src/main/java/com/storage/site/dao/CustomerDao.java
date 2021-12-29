package com.storage.site.dao;

import com.storage.site.domain.Customer;
import com.storage.site.domain.rowmapper.CustomerRowMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;


@AllArgsConstructor
@Component
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    private static final String SELECT_ALL_CUSTOMERS =
            "SELECT * FROM customers";

    private static final String SELECT_CUSTOMER_BY_ID =
            "SELECT * FROM customers WHERE id = ?";

    private static final String SELECT_CUSTOMER_BY_EMAIL =
            "SELECT * FROM customers WHERE LOWER(email) LIKE LOWER(?)";

    private static final String INSERT_CUSTOMER =
            "INSERT INTO "
                    + "customers(stripe_id, email, password, phone_number, first_name, last_name, "
                    + "  street_address, second_street_address, city, state, zip, date_joined, is_admin) "
                    + "VALUES (:stripe_id, :email, :password, :phone_number, :first_name, :last_name, "
                    + "  :street_address, :second_street_address, :city, :state, :zip, :date_joined, :is_admin) ";

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

    public int insertCustomer(Customer customer) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> params = new HashMap<>();
        params.put("stripe_id", customer.getStripeId());
        params.put("email", customer.getEmail());
        params.put("password", customer.getPassword());
        params.put("phone_number", customer.getPhoneNumber());
        params.put("first_name", customer.getFirstName());
        params.put("last_name", customer.getLastName());
        params.put("street_address", customer.getStreetAddress());
        params.put("second_street_address", customer.getSecondStreetAddress());
        params.put("city", customer.getCity());
        params.put("state", customer.getState());
        params.put("zip", customer.getZip());
        params.put("date_joined", customer.getDateJoined());
        params.put("is_admin", false);
        SqlParameterSource paramSource = new MapSqlParameterSource(params);
        namedParameterJdbcTemplate.update(INSERT_CUSTOMER, paramSource, keyHolder, new String[] {"id"});
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }
}
