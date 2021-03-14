package com.storage.site.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscriptionParams {

    private int id;
    private Transaction.Type transactionType;
    private String stripeId;
    private String stripeCustomerId;
    private String stripePriceId;
    private String stripePaymentMethodId;

}
