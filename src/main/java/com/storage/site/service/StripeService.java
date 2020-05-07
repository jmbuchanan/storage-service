package com.storage.site.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class StripeService {

    @Value("${stripe.secret}")
    private String key;

    @PostConstruct
    private void setStripeKey() {
        Stripe.apiKey = key;
    }

    public PaymentIntent collectPayment() throws StripeException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                    .setCurrency("usd")
                    .setAmount(51L)
                    .putMetadata("integration_check", "accept_a_payment")
                    .build();

        return PaymentIntent.create(params);
    }
}
