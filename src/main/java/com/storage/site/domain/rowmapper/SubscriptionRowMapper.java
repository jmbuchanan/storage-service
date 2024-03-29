package com.storage.site.domain.rowmapper;

import com.storage.site.domain.Subscription;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SubscriptionRowMapper implements RowMapper<Subscription> {

    @Override
    public Subscription mapRow(ResultSet rs, int i) throws SQLException {
            return new Subscription(
                    rs.getInt("id"),
                    rs.getString("stripe_id"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date"),
                    rs.getInt("customer_id"),
                    rs.getInt("unit_id"),
                    rs.getInt("payment_method_id")
            );
    }
}
