package com.storage.site.dao;

import com.storage.site.model.SubscriptionParams;
import com.storage.site.model.rowmapper.SubscriptionParamsRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubscriptionParamsDao {

    final private JdbcTemplate jdbcTemplate;
    final private SubscriptionParamsRowMapper subscriptionParamsRowMapper;

    private static final String SELECT_SUBSCRIPTION_PARAMS_WITH_TRANSACTION_EXECUTION_TODAY =
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

    public SubscriptionParamsDao(JdbcTemplate jdbcTemplate, SubscriptionParamsRowMapper subscriptionParamsRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.subscriptionParamsRowMapper = subscriptionParamsRowMapper;
    }
    public List<SubscriptionParams> fetchSubscriptionParamsForExecutionToday() {
        return jdbcTemplate.query(SELECT_SUBSCRIPTION_PARAMS_WITH_TRANSACTION_EXECUTION_TODAY, subscriptionParamsRowMapper);
    }
}
