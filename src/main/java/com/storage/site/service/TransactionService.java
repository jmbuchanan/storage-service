package com.storage.site.service;

import com.storage.site.dao.TransactionDao;
import com.storage.site.dto.BookRequest;
import com.storage.site.model.*;
import com.storage.site.util.DateUtil;
import com.stripe.exception.StripeException;
import com.stripe.model.Subscription;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionDao transactionDao;
    final private SubscriptionService subscriptionService;

    public void insertPendingTransaction(BookRequest bookRequest, int unitNumber) throws StripeException {
        subscriptionService.insertSubscription(bookRequest, unitNumber);
        int subscriptionId = subscriptionService.getSubscriptionId(bookRequest, unitNumber);
        //0 id gets overwritten when added to db
        Transaction transaction = new Transaction(
                0,
                Transaction.Type.BOOK,
                new Date(),
                DateUtil.stringToDate(bookRequest.getStartDate()),
                subscriptionId
        );
        transactionDao.insert(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionDao.fetchAll();
    }
}
