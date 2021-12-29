package com.storage.site.dto.output.mapper;

import com.storage.site.domain.PaymentMethod;
import com.storage.site.dto.output.PaymentMethodDTO;

public class PaymentMethodDTOMapper {
    public static PaymentMethodDTO map(PaymentMethod paymentMethod) {
        return PaymentMethodDTO.builder()
                .id(paymentMethod.getId())
                .cardBrand(paymentMethod.getCardBrand())
                .lastFour(paymentMethod.getLastFour())
                .associatedWithActiveSubscription(!paymentMethod.isCanBeDeleted())
                .build();
    }
}
