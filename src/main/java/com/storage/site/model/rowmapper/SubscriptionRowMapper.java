package com.storage.site.model.rowmapper;

import com.storage.site.model.Subscription;
import com.storage.site.model.Transaction;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SubscriptionRowMapper implements RowMapper<Subscription> {

    @Override
    public Subscription mapRow(ResultSet rs, int i) throws SQLException {
        //This needs to be refactored...
        int columnNum = rs.getMetaData().getColumnCount();
        if (columnNum == 1) {
            Subscription subscription = new Subscription();
            subscription.setId(rs.getInt("id"));
            return subscription;
        } else {
            return new Subscription(
                    rs.getInt("id"),
                    Transaction.Type.valueOf(rs.getString("transaction_type").toUpperCase()),
                    rs.getString("stripe_id"),
                    rs.getString("stripe_customer_id"),
                    rs.getString("stripe_price_id"),
                    rs.getString("stripe_payment_method_id")
            );
        }
    }
}
