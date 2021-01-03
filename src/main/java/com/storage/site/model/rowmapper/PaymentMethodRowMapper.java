package com.storage.site.model.rowmapper;

import com.storage.site.model.PaymentMethod;
import com.storage.site.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Component
public class PaymentMethodRowMapper implements RowMapper<PaymentMethod> {

    @Override
    public PaymentMethod mapRow(ResultSet rs, int rowNumber) throws SQLException {

        return new PaymentMethod(
                rs.getInt("id"),
                rs.getString("stripe_id"),
                rs.getString("card_brand"),
                rs.getDate("date_added"),
                rs.getString("last_four"),
                rs.getInt("customer_id")
        );
    }
}
