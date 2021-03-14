package com.storage.site.model.rowmapper;

import com.storage.site.model.SubscriptionParams;
import com.storage.site.model.Transaction;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SubscriptionParamsRowMapper implements RowMapper<SubscriptionParams> {


    @Override
    public SubscriptionParams mapRow(ResultSet rs, int i) throws SQLException {

        return new SubscriptionParams(
                rs.getInt("id"),
                Transaction.Type.valueOf(rs.getString("transaction_type").toUpperCase()),
                rs.getString("stripe_id"),
                rs.getString("stripe_customer_id"),
                rs.getString("stripe_price_id"),
                rs.getString("stripe_payment_method_id")
        );
    }
}
