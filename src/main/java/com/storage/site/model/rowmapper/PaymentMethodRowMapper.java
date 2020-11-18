package com.storage.site.model.rowmapper;

import com.storage.site.model.PaymentMethod;
import com.storage.site.util.DateUtil;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Component
public class PaymentMethodRowMapper implements RowMapper<PaymentMethod> {


    @Override
    public PaymentMethod mapRow(ResultSet rs, int rowNumber) throws SQLException {

        return new PaymentMethod(
                rs.getInt("id"),
                rs.getString("stripe_id"),
                rs.getString("card_brand"),
                DateUtil.stringToDate("date_added"),
                rs.getString("last_four"),
                rs.getInt("customer_id")
        );

    }
}
