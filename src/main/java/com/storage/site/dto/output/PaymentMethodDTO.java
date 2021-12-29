package com.storage.site.dto.output;

import lombok.Builder;


@Builder
public class PaymentMethodDTO {
    private int id;
    private String cardBrand;
    private String lastFour;
    private boolean associatedWithActiveSubscription;
}
