package com.storage.site.dao;

import com.storage.site.domain.Subscription;
import com.storage.site.domain.rowmapper.SubscriptionRowMapper;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@AllArgsConstructor
@Component
public class SubscriptionDao {

    final private JdbcTemplate jdbcTemplate;
    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    final private SubscriptionRowMapper subscriptionRowMapper;

    private static final String INSERT_SUBSCRIPTION =
            "INSERT INTO subscriptions (customer_id, unit_id, payment_method_id, stripe_id, start_date) " +
                    "VALUES (:customer_id, :unit_id, :payment_method_id, :stripe_id, :start_date) " +
                    "RETURNING id";

    private static final String UPDATE_SUBSCRIPTION_STRIPE_ID_AND_SET_TO_ACTIVE =
            "UPDATE subscriptions " +
                    "SET stripe_id = ?, " +
                    "  is_active = true " +
                    "WHERE id = ? "
            ;

    private static final String UPDATE_SUBSCRIPTION_END_DATE =
            "UPDATE subscriptions " +
                    "SET end_date = ? " +
                    "WHERE id = ? "
            ;

    private static final String SELECT_SUBSCRIPTION_BY_ID =
            "SELECT * " +
                    "FROM subscriptions " +
                    "WHERE id = ? "
            ;

    public static final String SELECT_SUBSCRIPTIONS_BY_CUSTOMER =
            "SELECT * " +
                    "FROM subscriptions " +
                    "WHERE customer_id = ? " +
                    "ORDER BY start_date ASC "
            ;

    public static final String UPDATE_SUBSCRIPTION_TO_INACTIVE_BY_ID =
            "UPDATE subscriptions " +
                    "SET is_active = false " +
                    "WHERE id = ? "
            ;

    public int insertSubscription(int customerId, int unitId, int cardId, String stripeId, Date startDate) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> params = new HashMap<>();
        params.put("customer_id", customerId);
        params.put("unit_id", unitId);
        params.put("payment_method_id", cardId);
        params.put("stripe_id", stripeId);
        params.put("start_date", startDate);
        SqlParameterSource paramSource = new MapSqlParameterSource(params);
        namedParameterJdbcTemplate.update(INSERT_SUBSCRIPTION, paramSource, keyHolder, new String[] {"id"});
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public void updateSubscriptionStripeId(int subscriptionId, String stripeId) {
        jdbcTemplate.update(UPDATE_SUBSCRIPTION_STRIPE_ID_AND_SET_TO_ACTIVE, stripeId, subscriptionId);
    }

    public void updateSubscriptionEndDate(int subscriptionId, Date endDate) {
        jdbcTemplate.update(UPDATE_SUBSCRIPTION_END_DATE, endDate, subscriptionId);
    }

    public Subscription getSubscriptionById(int id) {
        List<Subscription> subscriptions = jdbcTemplate.query(SELECT_SUBSCRIPTION_BY_ID, new Object[] {id}, subscriptionRowMapper);
        return subscriptions.get(0);
    }

    public List<Subscription> getSubscriptionsByCustomer(int customerId) {
        return jdbcTemplate.query(SELECT_SUBSCRIPTIONS_BY_CUSTOMER,
            new Object[] {customerId}, subscriptionRowMapper);
    }

    public void setSubscriptionToInactive(int id) {
        jdbcTemplate.update(UPDATE_SUBSCRIPTION_TO_INACTIVE_BY_ID, id);
    }

}
