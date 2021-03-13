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

    private final CustomerService customerService;
    private final UnitService unitService;
    private final PaymentMethodService paymentMethodService;
    private final PriceService priceService;
    private final TransactionDao transactionDao;

    @Scheduled(cron = "0 0 0 * * ?")
    public void transactionBatch() throws StripeException {
        List<Transaction> transactions = transactionDao.fetchTransactionsExecutedToday();
        log.info(String.format("%s transactions to be executed today", transactions.size()));
        for (Transaction transaction: transactions) {
            log.info("executing transaction " + transaction.getId());
            executeTransaction(transaction);
        }
    }

    private void executeTransaction(Transaction transaction) throws StripeException {
        if (transaction.getType().equals(Transaction.Type.BOOK)) {
            makeSubscription(transaction);
        } else if (transaction.getType().equals(Transaction.Type.CANCEL)) {
            cancelSubscription(transaction);
        }
    }

    //future: maybe refactor so only 1 db call is made
    private void makeSubscription(Transaction transaction) throws StripeException {
        Map<String, Object> params = new HashMap<>();

        Customer customer = customerService.getCustomerById(transaction.getCustomerId());
        String customerId = customer.getStripeId();
        params.put("customer", customerId);

        List<Object> items = new ArrayList<>();
        Map<String, Object> price = new HashMap<>();

        Unit unit = unitService.fetchUnitById(transaction.getUnitId());
        Price priceEntity = priceService.getPriceById(unit.getPriceId());
        String priceId = priceEntity.getStripeId();
        price.put("price", priceId);

        items.add(price);
        params.put("items", items);

        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(transaction.getPaymentMethodId());
        String paymentId = paymentMethod.getStripeId();
        params.put("default_payment_method", paymentId);

        Date billingAnchor = firstDayOfNextMonth();
        params.put("billing_cycle_anchor", billingAnchor);
        Subscription.create(params);
    }

    private Date firstDayOfNextMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    private void cancelSubscription(Transaction transaction) {
    }

    public void insertPendingTransaction(BookRequest bookRequest, int unitNumber) throws StripeException {
        //0 id gets overwritten when added to db
        Transaction transaction = new Transaction(
                0,
                Transaction.Type.BOOK,
                new Date(),
                DateUtil.stringToDate(bookRequest.getStartDate()),
                bookRequest.getCustomerId(),
                unitNumber,
                bookRequest.getCardId()
        );
        transactionDao.insert(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionDao.fetchAll();
    }
}
