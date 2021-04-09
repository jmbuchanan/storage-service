package com.storage.site.dao;

import com.storage.site.model.Transaction;
import com.storage.site.model.rowmapper.TransactionRowMapper;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TransactionDao {

    private final JdbcTemplate jdbcTemplate;
    private final TransactionRowMapper transactionRowMapper;

    private static final String SELECT_ALL_TRANSACTIONS =
            "SELECT * FROM transactions";

    private static final String INSERT_TRANSACTION =
            "INSERT INTO transactions " +
                    "(transaction_type, request_date, execution_date, subscription_id) " +
                    "VALUES (?::transaction_type, ?, ?, ?)";

    private static final String SELECT_PENDING_TRANSACTIONS =
            "SELECT * " +
                    "FROM transactions " +
                    "WHERE execution_date = CURRENT_DATE " +
                    "ORDER BY id"
            ;

    private static final String UPDATE_EXECUTION_DATE_TODAY_BY_SUBSCRIPTION_ID =
            "UPDATE transactions " +
                    "SET execution_date = CURRENT_DATE " +
                    "WHERE subscription_id = ? "
            ;

    private static final String SELECT_LATEST_TRANSACTION_BY_UNIT_ID =
            "SELECT t.id, transaction_type, request_date, execution_date, subscription_id " +
                    "FROM transactions t " +
                    "LEFT JOIN subscriptions s " +
                    "ON t.subscription_id = s.id " +
                    "WHERE s.unit_id = ? " +
                    "ORDER BY t.id DESC " +
                    "LIMIT 1 "
            ;

    public List<Transaction> fetchAll() {
        return jdbcTemplate.query(SELECT_ALL_TRANSACTIONS, transactionRowMapper);
    }

    public void insert(Transaction transaction) {
        jdbcTemplate.update(INSERT_TRANSACTION,
                transaction.getType().toString().toUpperCase(),
                transaction.getRequestDate(),
                transaction.getExecutionDate(),
                transaction.getSubscriptionId()
        );
    }

    public List<Transaction> fetchPendingTransactions() {
        return jdbcTemplate.query(SELECT_PENDING_TRANSACTIONS, transactionRowMapper);
    }

    public Transaction fetchLatestTransactionForUnit(int id) {
        List<Transaction> transactions = jdbcTemplate.query(SELECT_LATEST_TRANSACTION_BY_UNIT_ID, new Object[] {id},
                transactionRowMapper);
        return transactions.get(0);
    }

    public void updateExecutionDateToTodayBySubscriptionId(int subscriptionId) {
        jdbcTemplate.update(UPDATE_EXECUTION_DATE_TODAY_BY_SUBSCRIPTION_ID, subscriptionId);
    }
}
