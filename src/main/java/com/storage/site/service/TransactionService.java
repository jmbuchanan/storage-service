package com.storage.site.service;

import com.storage.site.dao.TransactionDao;
import com.storage.site.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionDao transactionDao;

    public TransactionService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public List<Transaction> getAllTransactions() {
        return transactionDao.fetchAll();
    }

    public void insertTransaction(Transaction transaction) {
        transactionDao.insert(transaction);
    }
}
