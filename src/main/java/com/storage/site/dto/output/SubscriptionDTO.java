package com.storage.site.dto.output;

import lombok.Builder;

@Builder
public class SubscriptionDTO {
    private int unitId;
    private int priceId;
    private boolean canBeCancelledImmediately;
    private String message;
}
