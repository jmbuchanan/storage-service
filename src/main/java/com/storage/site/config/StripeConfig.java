package com.storage.site.config;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StripeConfig {

    @Value("${stripe.secret}")
    private String key;

    @PostConstruct
    private void setStripeKey() {
        Stripe.apiKey = key;
    }
}
