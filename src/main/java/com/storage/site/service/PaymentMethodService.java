package com.storage.site.service;

import com.storage.site.model.PaymentMethod;
import com.storage.site.model.rowmapper.PaymentMethodRowMapper;
import com.storage.site.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentMethodService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PaymentMethodRowMapper paymentMethodRowMapper;

    public List<PaymentMethod> getPaymentMethodsByCustomerId(int customerId) {

        Object [] sqlParam = {customerId};

        try {
            List<PaymentMethod> paymentMethod = jdbcTemplate.query("SELECT * FROM payment_methods WHERE customer_id = ?", sqlParam, paymentMethodRowMapper);
            return paymentMethod;

        } catch (EmptyResultDataAccessException e) {
            e.getMessage();
            return new ArrayList<>();
        }
    }

    public void save(PaymentMethod paymentMethod) {
        jdbcTemplate.update(
                "INSERT INTO payment_methods(stripe_id, card_brand, date_added, last_four, customer_id) "
                + "  VALUES(?, ?, ?, ?, ?)",
                paymentMethod.getStripeId(),
                paymentMethod.getCardBrand(),
                DateUtil.dateToString(paymentMethod.getDateAdded()),
                paymentMethod.getLastFour(),
                paymentMethod.getCustomerId()
        );
    }

}
