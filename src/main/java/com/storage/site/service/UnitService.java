package com.storage.site.service;

import com.storage.site.dto.BookRequest;
import com.storage.site.model.Unit;
import com.storage.site.model.rowmapper.UnitRowMapper;
import com.storage.site.util.DateUtil;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Subscription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UnitService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UnitRowMapper unitRowMapper;

    public List<Unit> getAllUnits() {
        List<Unit> units = jdbcTemplate.query("SELECT * FROM units", unitRowMapper);
        return units;
    }

    public List<Unit> getUnitsByCustomerId(int id) {
        return jdbcTemplate.query("SELECT * FROM units WHERE customer_id = ?;", new Object[] {id}, unitRowMapper);
    }

    public Unit bookUnit(BookRequest bookRequest) {
        int priceId = Integer.parseInt(bookRequest.getUnitSize());
        List<Unit> units = jdbcTemplate.query("SELECT * FROM units WHERE customer_id IS NULL AND price_id = ? LIMIT 1;",
                new Integer[] {priceId}, unitRowMapper);
        if (units.size() == 0) {
            return null;
        }
        try {
            makeSubscription(bookRequest);
        } catch (StripeException e) {
            log.warn("Error communicating with Stripe server");
            return null;
        }
        Unit unit = units.get(0);
        jdbcTemplate.update("UPDATE units SET customer_id = ? WHERE id = ?;",
                DateUtil.stringToDate(bookRequest.getStartDate()), bookRequest.getCustomerId(), unit.getUnitNumber());
        log.info(String.format("Unit %s booked for customer %s", unit.getUnitNumber(), bookRequest.getCustomerId()));
        return unit;
    }

    public boolean cancelSubscription(Long unitNumber) {
        jdbcTemplate.update("UPDATE units SET customer_id = null WHERE id = ?;", unitNumber);
        return true;
    }

    private void makeSubscription(BookRequest bookRequest) throws StripeException {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date nextMonthFirstDay = calendar.getTime();

        List<Object> items = new ArrayList<>();
        Map<String, Object> price = new HashMap<>();
        price.put(
                "price",
                "price_1I8y8bBBzZIBZ7GfAOedK0Dk"
        );
        items.add(price);
        Map<String, Object> params = new HashMap<>();
        params.put("customer", "cus_IkTnsw6FFLRQLc");
        params.put("items", items);
        params.put("default_payment_method", "pm_1I8yqZBBzZIBZ7GfByWaOQPw");
        params.put("billing_cycle_anchor", nextMonthFirstDay);

        Subscription.create(params);
    }

}
