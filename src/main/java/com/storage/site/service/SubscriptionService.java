package com.storage.site.service;

import com.storage.site.dao.SubscriptionDao;
import com.storage.site.model.Subscription;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class SubscriptionService {

    final private SubscriptionDao subscriptionDao;

    public int insertSubscription(int customerId, int unitId, int cardId) {
        return subscriptionDao.insertSubscription(customerId, unitId, cardId);
    }

    public void setSubscriptionToInactive(Subscription subscription) {
        subscriptionDao.setSubscriptionToInactive(subscription.getId());
    }

    public Subscription getSubscriptionById(int id) {
        return subscriptionDao.fetchSubscriptionById(id);
    }

    public void updateSubscriptionStripeId(int id, String stripeId) {
        subscriptionDao.updateSubscriptionStripeId(id, stripeId);
    }

    public Subscription getSubscriptionByCustomerAndUnit(int customerId, int unitId) {
        return subscriptionDao.getSubscriptionByCustomerAndUnit(customerId, unitId);
    }
}
