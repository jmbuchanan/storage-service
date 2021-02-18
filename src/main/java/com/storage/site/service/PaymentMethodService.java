package com.storage.site.service;

import com.storage.site.dao.PaymentMethodDao;
import com.storage.site.model.PaymentMethod;
import com.storage.site.model.rowmapper.PaymentMethodRowMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentMethodService {

    private JdbcTemplate jdbcTemplate;
    private PaymentMethodRowMapper paymentMethodRowMapper;
    private PaymentMethodDao paymentMethodDao;

    public PaymentMethod getPaymentMethodById(int id) {
        return paymentMethodDao.getPaymentMethodById(id);
    }

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
                paymentMethod.getDateAdded(),
                paymentMethod.getLastFour(),
                paymentMethod.getCustomerId()
        );
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM payment_methods WHERE id = ?";
        Object[] args = new Object[] {id};

        return jdbcTemplate.update(sql, args) == 1;
    }

}
