package com.storage.site.dao;

import com.storage.site.model.Transaction;
import com.storage.site.model.rowmapper.TransactionRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionDao {

    private final JdbcTemplate jdbcTemplate;
    private final TransactionRowMapper transactionRowMapper;

    private static final String SELECT_ALL_TRANSACTIONS =
            "SELECT * FROM transactions";

    private static final String INSERT_TRANSACTION =
            "INSERT INTO transactions " +
                    "(transaction_type, request_date, execution_date, customer_id, unit_id, payment_method_id) " +
                    "VALUES (?::transaction_type, ?, ?, ?, ?, ?)";

    private static final String SELECT_TRANSACTIONS_WHERE_EXECUTION_DATE_IS_TODAY =
            "SELECT * FROM transactions WHERE execution_date = CURRENT_DATE";

    public TransactionDao(JdbcTemplate jdbcTemplate, TransactionRowMapper transactionRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionRowMapper = transactionRowMapper;
    }

    public List<Transaction> fetchAll() {
        return jdbcTemplate.query(SELECT_ALL_TRANSACTIONS, transactionRowMapper);
    }

    public void insert(Transaction transaction) {
        jdbcTemplate.update(INSERT_TRANSACTION,
                transaction.getType().toString().toUpperCase(),
                transaction.getRequestDate(),
                transaction.getExecutionDate(),
                transaction.getCustomerId(),
                transaction.getUnitId(),
                transaction.getPaymentMethodId());
    }

    public List<Transaction> fetchTransactionsExecutedToday() {
        return jdbcTemplate.query(SELECT_TRANSACTIONS_WHERE_EXECUTION_DATE_IS_TODAY, transactionRowMapper);
    }

}
