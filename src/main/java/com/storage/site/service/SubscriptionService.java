package com.storage.site.service;

import com.storage.site.dao.SubscriptionDao;
import com.storage.site.dto.BookRequest;
import com.storage.site.model.*;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class SubscriptionService {

    final private SubscriptionDao subscriptionDao;

    private static final String EVERY_MIDNIGHT_CRON = "0 0 0 * * ?";

    @Scheduled(cron = EVERY_MIDNIGHT_CRON)
    public void batchProcessTransactions() throws StripeException {
        List<Subscription> subscriptions = subscriptionDao.fetchSubscriptionsForExecutionToday();
        log.info(String.format("%s subscriptions to be executed today", subscriptions.size()));
        for (Subscription subscription: subscriptions) {
            process(subscription);
        }
    }

    private void process(Subscription subscription) throws StripeException {
        if (subscription.getTransactionType().equals(Transaction.Type.BOOK)) {
            log.info("transaction book unit");
            com.stripe.model.Subscription stripeSubscription = makeSubscription(subscription);
            subscriptionDao.updateSubscriptionStripeId(stripeSubscription.getId(), subscription.getId());
        } else if (subscription.getTransactionType().equals(Transaction.Type.CANCEL)) {
            log.info("transaction cancel sub");
            cancelSubscription(subscription);
        }
    }

    private com.stripe.model.Subscription makeSubscription(Subscription subscription) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("customer", subscription.getStripeCustomerId());
        params.put("default_payment_method", subscription.getStripePaymentMethodId());
        params.put("items", makeItemForPrice(subscription));
        params.put("billing_cycle_anchor", firstDayOfNextMonth());
        return com.stripe.model.Subscription.create(params);
    }

    private List<Object> makeItemForPrice(Subscription subscription) {
        List<Object> items = new ArrayList<>();
        Map<String, Object> price = new HashMap<>();
        price.put("price", subscription.getStripePriceId());
        items.add(price);
        return items;
    }

    private Date firstDayOfNextMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    private void cancelSubscription(Subscription subscription) {
        System.out.println("Cancel subscription to be implemented");
    }

    public void insertSubscription(BookRequest bookRequest, int unitNumber) {
        subscriptionDao.insertSubscription(bookRequest, unitNumber);
    }

    public int getSubscriptionId(BookRequest bookRequest, int unitId) {
        return subscriptionDao.getSubscriptionId(bookRequest, unitId);
    }

}
