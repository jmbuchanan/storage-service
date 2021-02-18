package com.storage.site.dao;

import com.storage.site.model.PaymentMethod;
import com.storage.site.model.rowmapper.PaymentMethodRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentMethodDao {

    private final JdbcTemplate jdbcTemplate;
    private final PaymentMethodRowMapper paymentMethodRowMapper;

    private static final String SELECT_PAYMENT_METHOD_BY_ID =
            "SELECT * FROM payment_methods WHERE id = ?";

    public PaymentMethodDao(JdbcTemplate jdbcTemplate, PaymentMethodRowMapper paymentMethodRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.paymentMethodRowMapper = paymentMethodRowMapper;
    }

    public PaymentMethod getPaymentMethodById(int id) {
        try {
            List<PaymentMethod> paymentMethods = jdbcTemplate.query(
                    SELECT_PAYMENT_METHOD_BY_ID, new Object[] {id}, paymentMethodRowMapper);
            return paymentMethods.get(0);
        } catch (EmptyResultDataAccessException e) {
            e.getMessage();
            return null;
        }
    }

}
