package com.storage.site.dao;

import com.storage.site.model.PaymentMethod;
import com.storage.site.model.rowmapper.PaymentMethodRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentMethodDao {

    private final JdbcTemplate jdbcTemplate;
    private final PaymentMethodRowMapper paymentMethodRowMapper;

    private static final String SELECT_PAYMENT_METHOD_BY_ID =
            "SELECT * FROM payment_methods WHERE id = ?";

    private static final String SELECT_PAYMENT_METHOD_BY_CUSTOMER_ID =
            "SELECT * FROM payment_methods WHERE is_active AND customer_id = ?";

    private static final String INSERT_PAYMENT_METHOD =
            "INSERT INTO payment_methods(stripe_id, card_brand, date_added, last_four, customer_id, is_active) "
                    + "  VALUES(?, ?, ?, ?, ?, true)";

    private static final String SET_INACTIVE_BY_ID =
            "UPDATE payment_methods SET is_active = false WHERE id = ?";

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

    public List<PaymentMethod> getPaymentMethodsByCustomerId(int id) {
        try {
            return jdbcTemplate.query(SELECT_PAYMENT_METHOD_BY_CUSTOMER_ID, new Integer[] {id}, paymentMethodRowMapper);
        } catch (EmptyResultDataAccessException e) {
            e.getMessage();
            return new ArrayList<>();
        }
    }

    public void insert(PaymentMethod paymentMethod) {
        jdbcTemplate.update(
                INSERT_PAYMENT_METHOD,
                paymentMethod.getStripeId(),
                paymentMethod.getCardBrand(),
                paymentMethod.getDateAdded(),
                paymentMethod.getLastFour(),
                paymentMethod.getCustomerId()
        );
    }

    public void setInactiveById(Long id) {
        jdbcTemplate.update(SET_INACTIVE_BY_ID, id);
    }
}
