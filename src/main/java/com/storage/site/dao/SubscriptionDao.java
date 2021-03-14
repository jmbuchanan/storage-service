package com.storage.site.dao;

import com.storage.site.dto.BookRequest;
import com.storage.site.model.Subscription;
import com.storage.site.model.rowmapper.SubscriptionRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubscriptionDao {

    final private JdbcTemplate jdbcTemplate;
    final private SubscriptionRowMapper subscriptionRowMapper;

    private static final String INSERT_SUBSCRIPTION =
            "INSERT INTO subscriptions (customer_id, unit_id, payment_method_id) " +
                    "VALUES (?, ?, ?)";

    static final private String SELECT_SUBSCRIPTION_BY_CUSTOMER_UNIT_AND_PAYMENT_METHOD =
            "SELECT * " +
                    "FROM subscriptions " +
                    "WHERE customer_id = ? " +
                    "  AND unit_id = ? " +
                    "  AND payment_method_id = ? "
            ;

    private static final String UPDATE_SUBSCRIPTION_STRIPE_ID =
            "UPDATE subscriptions " +
                    "SET stripe_id = ? " +
                    "WHERE id = ? "
            ;


    public SubscriptionDao(JdbcTemplate jdbcTemplate, SubscriptionRowMapper subscriptionRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.subscriptionRowMapper = subscriptionRowMapper;
    }

    public void insertSubscription(BookRequest bookRequest, int unitId) {
        jdbcTemplate.update(INSERT_SUBSCRIPTION, bookRequest.getCustomerId(),
                unitId, bookRequest.getCardId());
    }

    public int getSubscriptionId(BookRequest bookRequest, int unitId) {
        List<Subscription> result = jdbcTemplate.query(SELECT_SUBSCRIPTION_BY_CUSTOMER_UNIT_AND_PAYMENT_METHOD,
                new Object[] {bookRequest.getCustomerId(), unitId, bookRequest.getCardId()},
                subscriptionRowMapper);
        return result.get(0).getId();
    }

    public void updateSubscriptionStripeId(String stripeId, int subscriptionId) {
        jdbcTemplate.update(UPDATE_SUBSCRIPTION_STRIPE_ID, stripeId, subscriptionId);
    }
}
