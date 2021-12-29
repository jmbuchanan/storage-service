package com.storage.site.dto.output.mapper;

import com.storage.site.dto.output.SubscriptionDTO;
import com.storage.site.domain.Unit;
import com.storage.site.domain.Subscription;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SubscriptionDTOMapper {
    public static SubscriptionDTO map(Unit unit, Subscription subscription) {
        return SubscriptionDTO.builder()
                .unitId(unit.getId())
                .priceId(unit.getPriceId())
                .canBeCancelledImmediately(subscription.canBeCancelledImmediately())
                .message(buildMessage(subscription))
                .build();
    }

    private static String buildMessage(Subscription subscription) {
        Date todayDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        if (subscription.getStartDate() != null && subscription.getStartDate().after(todayDate)) {
            return String.format("Move-in date: %s", sdf.format(subscription.getStartDate()));
        } else if (subscription.getEndDate() != null) {
            return String.format("Last day: %s", sdf.format(subscription.getEndDate()));
        }
        return String.format("Active since: %s", sdf.format(subscription.getStartDate()));
    }
}
