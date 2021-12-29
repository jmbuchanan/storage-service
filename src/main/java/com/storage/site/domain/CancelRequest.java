package com.storage.site.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@AllArgsConstructor
@Slf4j
public class CancelRequest {

    private static final long MILLIS_PER_SECOND = 1000L;

    @Getter
    private final int unitId;
    private final String date;

    public String getDateString() {
        return date;
    }

    public Long getCancelAt() {
        Date cancelAtDate = getDate();
        if (cancelAtDate != null) {
            return cancelAtDate.getTime() / MILLIS_PER_SECOND;
        } else {
            return null;
        }
    }

    public Date getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        Date parsedDate;
        try {
            parsedDate = formatter.parse(date);
        } catch (ParseException e) {
            log.warn("Could not parse date");
            return null;
        }
        return parsedDate;
    }

    public boolean isValidDate() {
        return getDate().after(Date.from(Instant.now().minus(Duration.ofDays(1))));
    }
}
