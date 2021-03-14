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

    private static final String SELECT_SUBSCRIPTIONS_WITH_TRANSACTION_EXECUTION_TODAY =
                "SELECT s.id as id, " +
                    "  t.transaction_type as transaction_type, " +
                    "  s.stripe_id as stripe_id, " +
                    "  c.stripe_id as stripe_customer_id, " +
                    "  p.stripe_id as stripe_price_id, " +
                    "  pm.stripe_id as stripe_payment_method_id " +
                    "FROM transactions t " +
                    "LEFT JOIN subscriptions s " +
                    "  ON s.id = t.subscription_id " +
                    "LEFT JOIN customers c " +
                    "  ON c.id = s.customer_id " +
                    "LEFT JOIN payment_methods pm " +
                    "  ON pm.id = s.payment_method_id " +
                    "LEFT JOIN units u " +
                    "  ON u.id = s.unit_id " +
                    "LEFT JOIN prices p " +
                    "  ON p.id = u.price_id " +
                    "WHERE t.execution_date = CURRENT_DATE"
            ;

    static final private String SELECT_SUBSCRIPTION_BY_CUSTOMER_UNIT_AND_PAYMENT_METHOD =
            "SELECT id " +
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

    public List<Subscription> fetchSubscriptionsForExecutionToday() {
        return jdbcTemplate.query(SELECT_SUBSCRIPTIONS_WITH_TRANSACTION_EXECUTION_TODAY, subscriptionRowMapper);
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
