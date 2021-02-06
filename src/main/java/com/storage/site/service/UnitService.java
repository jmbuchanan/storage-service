package com.storage.site.service;

import com.storage.site.dto.BookRequest;
import com.storage.site.model.Customer;
import com.storage.site.model.PaymentMethod;
import com.storage.site.model.Price;
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
    private PriceService priceService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PaymentMethodService paymentMethodService;

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
                bookRequest.getCustomerId(), unit.getUnitNumber());
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
        Price priceEntity = priceService.getPriceById(Integer.parseInt(bookRequest.getUnitSize()));
        String priceId = priceEntity.getStripeId();
        price.put(
                "price",
                priceId
        );
        items.add(price);
        Map<String, Object> params = new HashMap<>();
        Customer customer = customerService.getCustomerbyId(bookRequest.getCustomerId());
        String customerId = customer.getStripeId();
        params.put("customer", customerId);
        params.put("items", items);

        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(bookRequest.getCardId());
        String paymentId = paymentMethod.getStripeId();
        params.put("default_payment_method", paymentId);
        params.put("billing_cycle_anchor", nextMonthFirstDay);

        Subscription.create(params);
    }

}
