package com.storage.site.service;

import com.storage.site.model.Transaction;
import com.storage.site.model.rowmapper.TransactionRowMapper;
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
}
