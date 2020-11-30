package com.storage.site.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookRequest {
    private final String unitSize;
    private final String startDate;
    private final String cardId;
    private final String customerId;

    @Override
    public String toString() {
        String size = unitSize.equals("0") ? "small" : "large";
        return String.format("Booking request from customer %s for %s unit on date %s with card id %s", customerId, size, startDate, cardId);
    }
}
