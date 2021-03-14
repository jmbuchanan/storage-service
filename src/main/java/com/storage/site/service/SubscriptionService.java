package com.storage.site.service;

import com.storage.site.dao.SubscriptionDao;
import com.storage.site.dao.SubscriptionParamsDao;
import com.storage.site.dto.BookRequest;
import com.storage.site.model.*;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class SubscriptionService {

    final private SubscriptionDao subscriptionDao;
    final private SubscriptionParamsDao subscriptionParamsDao;

    public void processSubscriptionExecution() throws StripeException {
        List<SubscriptionParams> subscriptionParamsList = subscriptionParamsDao.fetchSubscriptionParamsForExecutionToday();
        log.info(String.format("%s subscriptions to be executed today", subscriptionParamsList.size()));
        for (SubscriptionParams subscriptionParams: subscriptionParamsList) {
            process(subscriptionParams);
        }
    }

    private void process(SubscriptionParams params) throws StripeException {
        if (params.getTransactionType().equals(Transaction.Type.BOOK)) {
            log.info("transaction book unit");
            com.stripe.model.Subscription stripeSubscription = makeSubscription(params);
            subscriptionDao.updateSubscriptionStripeId(stripeSubscription.getId(), params.getId());
        } else if (params.getTransactionType().equals(Transaction.Type.CANCEL)) {
            log.info("transaction cancel sub");
            cancelSubscription(params);
        }
    }

    private com.stripe.model.Subscription makeSubscription(SubscriptionParams subParams) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("customer", subParams.getStripeCustomerId());
        params.put("default_payment_method", subParams.getStripePaymentMethodId());
        params.put("items", makeItemFromPrice(subParams));
        params.put("billing_cycle_anchor", firstDayOfNextMonth());
        return com.stripe.model.Subscription.create(params);
    }

    private List<Object> makeItemFromPrice(SubscriptionParams subParams) {
        List<Object> items = new ArrayList<>();
        Map<String, Object> price = new HashMap<>();
        price.put("price", subParams.getStripePriceId());
        items.add(price);
        return items;
    }

    private Date firstDayOfNextMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    private void cancelSubscription(SubscriptionParams subParams) {
        System.out.println("Cancel subscription to be implemented");
    }

    public void insertSubscription(BookRequest bookRequest, int unitNumber) {
        subscriptionDao.insertSubscription(bookRequest, unitNumber);
    }

    public int getSubscriptionId(BookRequest bookRequest, int unitId) {
        return subscriptionDao.getSubscriptionId(bookRequest, unitId);
    }

}
