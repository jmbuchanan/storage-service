package com.storage.site.service;

import com.storage.site.model.Customer;
import com.storage.site.model.rowmapper.CustomerRowMapper;
import com.storage.site.util.DateUtil;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        try {
            Customer customer = jdbcTemplate.queryForObject("SELECT * FROM customers WHERE email LIKE ?", sqlParam, customerRowMapper);
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
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
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
            DateUtil.dateToString(customer.getDateJoined()),
            false
        );
    }

    public Customer createCustomerFromRequest(HttpServletRequest request) {

        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("email", request.getParameter("email"));
        com.stripe.model.Customer stripeCustomer = null;
        String stripeCustomerId = null;
        try {
            stripeCustomer = com.stripe.model.Customer.create(customerParams);
            stripeCustomerId = stripeCustomer.getId();
        } catch (StripeException e) {
            e.getMessage();
        }

        Customer customer = new Customer();

        customer.setEmail(request.getParameter("email"));
        customer.setStripeId(stripeCustomerId);
        customer.setPassword(passwordEncoder.encode(request.getParameter("password")));
        customer.setFirstName(request.getParameter("firstName"));
        customer.setLastName(request.getParameter("lastName"));
        customer.setPhoneNumber(request.getParameter("phoneNumber"));
        customer.setStreetAddress(request.getParameter("streetAddress"));
        customer.setSecondStreetAddress(request.getParameter("secondStreetAddress"));
        customer.setCity(request.getParameter("city"));
        customer.setState(Customer.State.valueOf(request.getParameter("state")));
        customer.setZip(request.getParameter("zip"));
        customer.setDateJoined(new Date());

        return customer;
    }
}
