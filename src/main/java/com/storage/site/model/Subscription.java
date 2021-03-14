package com.storage.site.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Subscription {

    private int id;
    private Transaction.Type transactionType;
    private String stripeId;
    private String stripeCustomerId;
    private String stripePriceId;
    private String stripePaymentMethodId;

}
