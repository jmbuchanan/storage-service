package com.storage.site.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@AllArgsConstructor
@Data
@Slf4j
public class BookRequest {

    private static final long MILLIS_PER_SECOND = 1000L;
    private static final int MIN_DAYS_OUT = 1;
    private static final int MAX_DAYS_OUT = 30;

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

    public boolean hasValidStartDate() {
        Date date = stringToDate(startDate);
        if (date == null) {
            return false;
        } else {
            return date.after(Date.from(Instant.now().plus(MIN_DAYS_OUT, ChronoUnit.DAYS))) &&
                    date.before(Date.from(Instant.now().plus(MAX_DAYS_OUT, ChronoUnit.DAYS)));
        }
    }
}
