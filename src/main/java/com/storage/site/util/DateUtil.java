package com.storage.site.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

@Slf4j
@Component
public class DateUtil {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);

    @PostConstruct
    private void setTimeZone() {
        formatter.setTimeZone(TimeZone.getTimeZone("America/New_York"));
    }

    public static Date stringToDate(String dateStr) {
        Date date;
        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            log.warn("Could not parse date");
            return null;
        }
        return date;
    }

    public static String dateToString(Date date) {
        return formatter.format(date);
    }

}