package com.storage.site.dao;

import com.storage.site.model.Subscription;
import com.storage.site.model.rowmapper.SubscriptionRowMapper;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Component
public class SubscriptionDao {

    final private JdbcTemplate jdbcTemplate;
    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    final private SubscriptionRowMapper subscriptionRowMapper;

    private static final String INSERT_SUBSCRIPTION =
            "INSERT INTO subscriptions (is_active, customer_id, unit_id, payment_method_id) " +
                    "VALUES (:is_active, :customer_id, :unit_id, :payment_method_id) " +
                    "RETURNING id";

    private static final String UPDATE_SUBSCRIPTION_STRIPE_ID_AND_SET_TO_ACTIVE =
            "UPDATE subscriptions " +
                    "SET stripe_id = ?, " +
                    "SET is_active = true, " +
                    "WHERE id = ? "
            ;

    private static final String SELECT_SUBSCRIPTION_BY_ID =
            "SELECT * " +
                    "FROM subscriptions " +
                    "WHERE id = ? "
            ;

    public static final String SELECT_SUBSCRIPTION_BY_CUSTOMER_AND_UNIT =
            "SELECT * " +
                    "FROM subscriptions " +
                    "WHERE customer_id = ? " +
                    "AND unit_id = ? " +
                    "ORDER BY id DESC " +
                    "LIMIT 1"
            ;

    public static final String UPDATE_SUBSCRIPTION_TO_INACTIVE_BY_ID =
            "UPDATE subscriptions " +
                    "SET is_active = false " +
                    "WHERE id = ? "
            ;

    public int insertSubscription(int customerId, int unitId, int cardId) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> params = new HashMap<>();
        params.put("is_active", false);
        params.put("customer_id", customerId);
        params.put("unit_id", unitId);
        params.put("payment_method_id", cardId);
        SqlParameterSource paramSource = new MapSqlParameterSource(params);
        namedParameterJdbcTemplate.update(INSERT_SUBSCRIPTION, paramSource, keyHolder, new String[] {"id"});
        return keyHolder.getKey().intValue();
    }

    public void updateSubscriptionStripeId(int subscriptionId, String stripeId) {
        jdbcTemplate.update(UPDATE_SUBSCRIPTION_STRIPE_ID_AND_SET_TO_ACTIVE, stripeId, subscriptionId);
    }

    public Subscription fetchSubscriptionById(int id) {
        List<Subscription> subscriptions = jdbcTemplate.query(SELECT_SUBSCRIPTION_BY_ID, new Object[] {id}, subscriptionRowMapper);
        return subscriptions.get(0);
    }

    public Subscription getSubscriptionByCustomerAndUnit(int customerId, int unitId) {
        List<Subscription> result = jdbcTemplate.query(SELECT_SUBSCRIPTION_BY_CUSTOMER_AND_UNIT,
                new Object[] {customerId, unitId}, subscriptionRowMapper);
        return result.get(0);
    }

    public void setSubscriptionToInactive(int id) {
        jdbcTemplate.update(UPDATE_SUBSCRIPTION_TO_INACTIVE_BY_ID, id);
    }

}
