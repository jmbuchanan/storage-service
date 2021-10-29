package com.storage.site.service;

import com.storage.site.dao.PaymentMethodDao;
import com.storage.site.model.Customer;
import com.storage.site.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentMethodService {

    private final PaymentMethodDao paymentMethodDao;
    private final StripeService stripeService;
    private final CustomerService customerService;

    public PaymentMethod getPaymentMethodById(int id) {
        return paymentMethodDao.getPaymentMethodById(id);
    }

    public List<PaymentMethod> getPaymentMethodsByCustomerId(int customerId) {
        return paymentMethodDao.getPaymentMethodsByCustomerId(customerId);
    }

    public void addPaymentMethod(PaymentMethod paymentMethod) {
        Customer customer = customerService.getCustomerById(paymentMethod.getCustomerId());
        stripeService.addCustomerToPaymentMethod(customer.getStripeId(), paymentMethod.getStripeId());
        paymentMethod.setDateAdded(new Date());
        paymentMethodDao.insert(paymentMethod);
    }

    public void setInactive(int id, int customerId) {
        PaymentMethod paymentMethod = getPaymentMethodById(id);
        if (paymentMethod.getCustomerId() == customerId) {
            paymentMethodDao.setInactiveById(id);
        } else {
            log.warn("Customer requested to delete card that does not belong to them");
        }
    }

}
