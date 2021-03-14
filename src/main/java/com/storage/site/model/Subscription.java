package com.storage.site.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Subscription {

    private int id;
    private String stripeId;
    private int customerId;
    private int unitId;
    private int paymentMethodId;

}
