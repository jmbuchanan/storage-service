package com.storage.site.service;

import com.storage.site.dao.TransactionDao;
import com.storage.site.dto.BookRequest;
import com.storage.site.dto.CancelRequest;
import com.storage.site.model.*;
import com.storage.site.util.DateUtil;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionDao transactionDao;
    final private JwtService jwtService;
    final private SubscriptionService subscriptionService;
    final private CustomerService customerService;
    final private PaymentMethodService paymentMethodService;
    final private UnitService unitService;
    final private PriceService priceService;

    private static final String EVERY_MIDNIGHT_CRON = "0 0 0 * * ?";

    private static final int CANCEL_IMMEDIATELY_STATUS = 0;
    private static final int CANCEL_IN_FUTURE_STATUS = 1;
    private static final int NOT_ELIGIBLE_FOR_CANCEL_STATUS = 2;

    private static final String JUNK_DATE_STRING = "1970-01-01";

    @Scheduled(cron = EVERY_MIDNIGHT_CRON)
    public void processPendingTransactions() throws StripeException {
        List<Transaction> transactions = transactionDao.fetchPendingTransactions();
        log.info(String.format("%s transactions to be executed today", transactions.size()));
        for (Transaction transaction : transactions) {
            process(transaction);
        }
    }

    public boolean insertPendingTransaction(BookRequest bookRequest, HttpServletRequest request) {
        Unit unit = unitService.getOneUnitForPrice(Integer.parseInt(bookRequest.getUnitSize()));
        if (unit != null) {
            int customerId = jwtService.parseCustomerId(request);
            int subscriptionId = subscriptionService.insertSubscription(customerId, unit.getUnitNumber(), bookRequest.getCardId());
            //0 id gets overwritten when added to db
            Transaction transaction = new Transaction(
                    0,
                    Transaction.Type.BOOK,
                    new Date(),
                    DateUtil.stringToDate(bookRequest.getStartDate()),
                    subscriptionId
            );
            transactionDao.insert(transaction);
            unitService.updateCustomerOfUnit(customerId, unit.getUnitNumber());
            return true;
        } else {
            return false;
        }
    }

    public void handleCancelRequest(CancelRequest cancelRequest, HttpServletRequest httpRequest) {
        int customerId = jwtService.parseCustomerId(httpRequest);
        cancelRequest.setCustomerId(customerId);
        if (cancelRequest.isCancelImmediately()) {
            cancelImmediately(cancelRequest);
        } else {
            insertCancelTransaction(cancelRequest);
        }
    }

    public void insertCancelTransaction(CancelRequest cancelRequest) {
        Subscription subscription = subscriptionService.getSubscriptionByCustomerAndUnit(cancelRequest.getCustomerId(),
                cancelRequest.getUnitId());
        Transaction transaction = new Transaction(
                0,
                Transaction.Type.CANCEL,
                new Date(),
                DateUtil.stringToDate(cancelRequest.getExecutionDate()),
                subscription.getId()
        );
        transactionDao.insert(transaction);
    }

    private void cancelImmediately(CancelRequest cancelRequest) {
        Subscription subscription = subscriptionService.getSubscriptionByCustomerAndUnit(cancelRequest.getCustomerId(),
                cancelRequest.getUnitId());
        //gets overridden in transactionDao update statement
        cancelRequest.setExecutionDate(JUNK_DATE_STRING);
        insertCancelTransaction(cancelRequest);
        transactionDao.updateExecutionDateToTodayBySubscriptionId(subscription.getId());
        unitService.setUnitCustomerToNull(cancelRequest.getUnitId());
    }

    public List<Transaction> getAllTransactions() {
        return transactionDao.fetchAll();
    }

    public int getCancelEligibilityForUnit(int id) {
        Transaction transaction = transactionDao.fetchLatestTransactionForUnit(id);
        if (isFutureBookRequest(transaction)) {
            return CANCEL_IMMEDIATELY_STATUS;
        } else if (isPastBookRequest(transaction)) {
            return CANCEL_IN_FUTURE_STATUS;
        } else {
            return NOT_ELIGIBLE_FOR_CANCEL_STATUS;
        }
    }

    private void process(Transaction transaction) throws StripeException {
        if (transaction.getType().equals(Transaction.Type.BOOK)) {
            processBookTransaction(transaction);
        } else if (transaction.getType().equals(Transaction.Type.CANCEL)) {
            processCancelTransaction(transaction);
        }
    }

    private void processBookTransaction(Transaction transaction) {
        Subscription subscription = subscriptionService.getSubscriptionById(transaction.getSubscriptionId());
        Customer customer = customerService.getCustomerById(subscription.getCustomerId());
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(subscription.getPaymentMethodId());
        Unit unit = unitService.getUnitById(subscription.getUnitId());

        Map<String, Object> params = new HashMap<>();
        params.put("customer", customer.getStripeId());
        params.put("default_payment_method", paymentMethod.getStripeId());
        params.put("items", makeItemFromPrice(unit.getPriceId()));
        params.put("billing_cycle_anchor", firstDayOfNextMonth());
        try {
            com.stripe.model.Subscription stripeSubscription =
                com.stripe.model.Subscription.create(params);
            subscriptionService.updateSubscriptionStripeId(subscription.getId(), stripeSubscription.getId());
        } catch (StripeException se) {
            se.getMessage();
        }
    }

    private List<Object> makeItemFromPrice(int priceId) {
        Price price = priceService.getPriceById(priceId);
        List<Object> items = new ArrayList<>();
        Map<String, Object> priceMap = new HashMap<>();
        priceMap.put("price", price.getStripeId());
        items.add(priceMap);
        return items;
    }

    private Date firstDayOfNextMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    private void processCancelTransaction(Transaction transaction) throws StripeException {
        Subscription subscription = subscriptionService.getSubscriptionById(transaction.getSubscriptionId());
        cancelStripeSubscription(subscription);
        subscriptionService.setSubscriptionToInactive(subscription);
        unitService.setUnitCustomerToNull(subscription.getUnitId());
    }

    private void cancelStripeSubscription(Subscription subscription) throws StripeException {
        com.stripe.model.Subscription stripeSubscription =
                com.stripe.model.Subscription.retrieve(subscription.getStripeId());
        stripeSubscription.cancel();
    }

    private boolean isFutureBookRequest(Transaction transaction) {
        return transaction.getType().equals(Transaction.Type.BOOK)
                && transaction.getExecutionDate().after(new Date());
    }

    private boolean isPastBookRequest(Transaction transaction) {
        return transaction.getType().equals(Transaction.Type.BOOK)
                && transaction.getExecutionDate().before(new Date());
    }
}

 