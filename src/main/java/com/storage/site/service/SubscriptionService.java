package com.storage.site.service;

import com.storage.site.dao.*;
import com.storage.site.dto.input.BookRequestDTO;
import com.storage.site.dto.input.mapper.BookRequestDTOMapper;
import com.storage.site.dto.output.SubscriptionDTO;
import com.storage.site.dto.output.mapper.SubscriptionDTOMapper;
import com.storage.site.exception.BadRequestException;
import com.storage.site.exception.ConflictException;
import com.storage.site.exception.ForbiddenException;
import com.storage.site.exception.StripeGenericException;
import com.storage.site.domain.*;
import com.stripe.exception.StripeException;
import com.stripe.param.SubscriptionCreateParams;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubscriptionService {

    final private AuthService authService;
    final private CustomerDao customerDao;
    final private PaymentMethodDao paymentMethodDao;
    final private PriceDao priceDao;
    final private SubscriptionDao subscriptionDao;
    final private UnitDao unitDao;

    public void create(BookRequestDTO bookRequestDTO, HttpServletRequest request) {
        BookRequest bookRequest = BookRequestDTOMapper.map(bookRequestDTO);
        List<Unit> units = unitDao.getAvailableUnitsByPrice(bookRequestDTO.getUnitSize());
        if (units.size() == 0) {
            throw new ConflictException();
        }
        Unit unit = units.get(0);
        PaymentMethod paymentMethod = paymentMethodDao.getPaymentMethodById(bookRequest.getCardId());
        int customerId = authService.parseCustomerId(request);
        if (paymentMethod.getCustomerId() != customerId) {
            throw new ForbiddenException();
        }

        Customer customer = customerDao.getCustomerById(customerId);

        SubscriptionCreateParams params = SubscriptionCreateParams.builder()
                .setCustomer(customer.getStripeId())
                .setDefaultPaymentMethod(paymentMethod.getStripeId())
                .addItem(makeItemFromPrice(unit.getPriceId()))
                .setBillingCycleAnchor(bookRequest.getBillingAnchorCycle())
                .setProrationBehavior(SubscriptionCreateParams.ProrationBehavior.NONE)
                .build();

        try {
            com.stripe.model.Subscription stripeSubscription = com.stripe.model.Subscription.create(params);
            Date startDate = new Date(stripeSubscription.getBillingCycleAnchor() * 1000);
            subscriptionDao.insertSubscription(customerId, unit.getId(), paymentMethod.getId(), stripeSubscription.getId(), startDate);
        } catch (StripeException se) {
            log.info(se.getMessage());
            throw new StripeGenericException();
        }
    }

    public List<SubscriptionDTO> getActiveSubscriptionsByCustomerId(HttpServletRequest request) {
        int id = authService.parseCustomerId(request);
        return getActiveSubscriptionsByCustomerId(id);
    }

    public List<SubscriptionDTO> getActiveSubscriptionsByCustomerId(int id) {
        List<Subscription> subscriptions = subscriptionDao.getSubscriptionsByCustomer(id);
        List<Subscription> activeSubscriptions = filterActiveSubscriptions(subscriptions);
        List<Unit> units = unitDao.getAll();
        return makeSubscriptionDTOs(activeSubscriptions, units);
    }

    private List<Subscription> filterActiveSubscriptions(List<Subscription> subscriptions) {
        return subscriptions.stream()
                .filter(Subscription::isActive)
                .collect(Collectors.toList());
    }

    private List<SubscriptionDTO> makeSubscriptionDTOs(List<Subscription> subscriptions, List<Unit> units) {
        List<SubscriptionDTO> subscriptionDTOs = new ArrayList<>();
        for (Subscription subscription: subscriptions) {
            SubscriptionDTO subscriptionDTO = units.stream()
                    .filter(unit -> unit.getId() == subscription.getUnitId())
                    .map(unit -> SubscriptionDTOMapper.map(unit, subscription))
                    .findFirst()
                    .orElse(null);

            subscriptionDTOs.add(subscriptionDTO);
        }
        return subscriptionDTOs;
    }

    public void cancel(CancelRequest cancelRequest, HttpServletRequest request) {
        int customerId = authService.parseCustomerId(request);
       List<Subscription> subscriptions = subscriptionDao.getSubscriptionsByCustomer(customerId);
        List<Subscription> activeSubscriptions = filterActiveSubscriptions(subscriptions);
        Subscription subscriptionForUnit = activeSubscriptions.stream()
                .filter(subscription -> subscription.getUnitId() == cancelRequest.getUnitId())
                .findFirst()
                .orElse(null);

        if (!cancelRequest.isValidDate()) {
            throw new BadRequestException();
        }
        try {
            if (subscriptionForUnit != null) {
                Long stripeCancelDate = cancelRequest.getCancelAt();
                if (subscriptionForUnit.canBeCancelledImmediately()) {
                    com.stripe.model.Subscription.retrieve(subscriptionForUnit.getStripeId()).cancel();
                } else {
                    com.stripe.model.Subscription.retrieve(subscriptionForUnit.getStripeId()).setCancelAt(stripeCancelDate);
                }
                Date cancelDate = new Date(stripeCancelDate * 1000L);
                subscriptionDao.updateSubscriptionEndDate(subscriptionForUnit.getId(), cancelDate);
            }
        } catch (StripeException se) {
            throw new StripeGenericException();
        }
    }

    private SubscriptionCreateParams.Item makeItemFromPrice(int priceId) {
        Price price = priceDao.getPriceById(priceId);
        return SubscriptionCreateParams.Item.builder().setPrice(price.getStripeId()).build();
    }
}
