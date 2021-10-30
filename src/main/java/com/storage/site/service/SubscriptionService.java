package com.storage.site.service;

import com.storage.site.dao.SubscriptionDao;
import com.storage.site.dto.BookRequest;
import com.storage.site.exception.ConflictException;
import com.storage.site.exception.ForbiddenException;
import com.storage.site.model.*;
import com.storage.site.util.DateUtil;
import com.stripe.exception.StripeException;
import com.stripe.param.SubscriptionCreateParams;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
@Slf4j
public class SubscriptionService {

    final private SubscriptionDao subscriptionDao;
    final private UnitService unitService;
    final private PaymentMethodService paymentMethodService;
    final private JwtService jwtService;
    final private CustomerService customerService;
    final private PriceService priceService;

    public void create(BookRequest bookRequest, HttpServletRequest request) {
        Unit unit = unitService.getOneUnitForPrice(bookRequest.getUnitSize());
        if (unit == null) {
            throw new ConflictException();
        }

        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(bookRequest.getCardId());
        int customerId = jwtService.parseCustomerId(request);
        if (paymentMethod.getCustomerId() != customerId) {
            throw new ForbiddenException();
        }

        Customer customer = customerService.getCustomerById(customerId);

        SubscriptionCreateParams params = SubscriptionCreateParams.builder()
                .setCustomer(customer.getStripeId())
                .setDefaultPaymentMethod(paymentMethod.getStripeId())
                .addItem(makeItemFromPrice(unit.getPriceId()))
                .setBillingCycleAnchor(DateUtil.stringToDate(bookRequest.getStartDate()).getTime() / 1000)
                .setProrationBehavior(SubscriptionCreateParams.ProrationBehavior.NONE)
                .build();

        try {
            com.stripe.model.Subscription stripeSubscription = com.stripe.model.Subscription.create(params);
            insertSubscription(customerId, unit.getId(), paymentMethod.getId(), stripeSubscription.getId());
        } catch (StripeException se) {
            log.info(se.getMessage());
        }

        unitService.updateCustomerOfUnit(customerId, unit.getId());
    }

    public int insertSubscription(int customerId, int unitId, int cardId, String stripeSubscriptionId) {
        return subscriptionDao.insertSubscription(customerId, unitId, cardId, stripeSubscriptionId);
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

    private SubscriptionCreateParams.Item makeItemFromPrice(int priceId) {
        Price price = priceService.getPriceById(priceId);
        return SubscriptionCreateParams.Item.builder().setPrice(price.getStripeId()).build();
    }
}
