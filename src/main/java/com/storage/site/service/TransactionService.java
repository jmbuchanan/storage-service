package com.storage.site.service;

import com.storage.site.model.Transaction;
import com.storage.site.model.rowmapper.TransactionRowMapper;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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

    public void insertTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions " +
                "(transaction_type, request_date, execution_date, customer_id, unit_id, payment_method_id) " +
                "VALUES (?::transaction_type, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, transaction.getType().toString().toUpperCase(), transaction.getRequestDate(), transaction.getExecutionDate(),
                transaction.getCustomerId(), transaction.getUnitId(), transaction.getPaymentMethodId());
    }
}
