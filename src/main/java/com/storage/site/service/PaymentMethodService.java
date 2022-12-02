package com.storage.site.service;

import com.storage.site.dao.CustomerDao;
import com.storage.site.dao.PaymentMethodDao;
import com.storage.site.dao.SubscriptionDao;
import com.storage.site.domain.Customer;
import com.storage.site.domain.PaymentMethod;
import com.storage.site.domain.Subscription;
import com.storage.site.dto.output.PaymentMethodDTO;
import com.storage.site.dto.output.mapper.PaymentMethodDTOMapper;
import com.storage.site.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentMethodService {

    private final StripeService stripeService;
    private final PaymentMethodDao paymentMethodDao;
    private final CustomerDao customerDao;
    private final SubscriptionDao subscriptionDao;

    public List<PaymentMethodDTO> getPaymentMethodsByCustomerId(int customerId) {
        List<PaymentMethod> paymentMethods = paymentMethodDao.getPaymentMethodsByCustomerId(customerId);
        List<Subscription> subscriptions = subscriptionDao.getSubscriptionsByCustomer(customerId);
        List<Integer> paymentMethodsWithActiveSubscription =
                subscriptions.stream()
                        .filter(Subscription::isActive)
                        .map(Subscription::getPaymentMethodId)
                        .collect(Collectors.toList());
        for (PaymentMethod paymentMethod: paymentMethods) {
            if (!paymentMethodsWithActiveSubscription.contains(paymentMethod.getId())) {
                paymentMethod.setCanBeDeleted(true);
            }
        }
        return paymentMethods.stream().map(PaymentMethodDTOMapper::map).collect(Collectors.toList());
    }

    public void addPaymentMethod(PaymentMethod paymentMethod) {
        Customer customer = customerDao.getCustomerById(paymentMethod.getCustomerId());
        stripeService.addCustomerToPaymentMethod(customer.getStripeId(), paymentMethod.getStripeId());
        paymentMethod.setDateAdded(new Date());
        paymentMethodDao.insert(paymentMethod);
    }

    public void setInactive(int id, int customerId) {
        PaymentMethod paymentMethod = paymentMethodDao.getPaymentMethodById(id);
        List<Subscription> subscriptions = subscriptionDao.getSubscriptionsByCustomer(customerId);
        if (subscriptions.stream().anyMatch(subscription ->
                subscription.isActive() &&
                subscription.getPaymentMethodId() == id)) {
            throw new BadRequestException();
        }
        if (paymentMethod.getCustomerId() == customerId) {
            paymentMethodDao.setInactiveById(id);
        } else {
            log.warn("Customer requested to delete card that does not belong to them");
        }
    }

}
