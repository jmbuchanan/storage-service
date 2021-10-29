package com.storage.site.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class StripeService {

    public String createCustomer(final String email) {
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("email", email);
        try {
            Customer stripeCustomer = com.stripe.model.Customer.create(customerParams);
            return stripeCustomer.getId();
        } catch (StripeException e) {
            e.getMessage();
            return null;
        }
    }

    public void addCustomerToPaymentMethod(final String stripeCustomerId, final String stripePaymentId) {
        try {
            com.stripe.model.PaymentMethod stripePaymentMethod = com.stripe.model.PaymentMethod.retrieve(stripePaymentId);
            Map<String, Object> params = new HashMap<>();
            params.put("customer", stripeCustomerId);
            stripePaymentMethod.attach(params);
        } catch (StripeException e) {
            e.getMessage();
        }
    }
}
