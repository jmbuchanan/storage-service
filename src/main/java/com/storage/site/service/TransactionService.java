package com.storage.site.service;

import com.storage.site.model.Transaction;
import com.storage.site.model.rowmapper.TransactionRowMapper;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TransactionRowMapper transactionRowMapper;

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = jdbcTemplate.query("SELECT * FROM transactions", transactionRowMapper);
        return transactions;
    }

    public PaymentIntent collectPayment() throws StripeException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setCurrency("usd")
                        .setAmount(51L)
                        .putMetadata("integration_check", "accept_a_payment")
                        .build();

        return PaymentIntent.create(params);
    }
}
