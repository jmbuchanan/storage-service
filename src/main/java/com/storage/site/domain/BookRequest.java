package com.storage.site.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@AllArgsConstructor
@Data
@Slf4j
public class BookRequest {

    private static final long MILLIS_PER_SECOND = 1000L;

    private final int unitSize;
    private final String startDate;
    private final int cardId;

    public Long getBillingAnchorCycle() {
        Date billingAnchorDate = stringToDate(startDate);
        if (billingAnchorDate != null) {
            return billingAnchorDate.getTime() / MILLIS_PER_SECOND;
        } else {
            return null;
        }
    }

    private Date stringToDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        Date date;
        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            log.warn("Could not parse date");
            return null;
        }
        return date;
    }
}
