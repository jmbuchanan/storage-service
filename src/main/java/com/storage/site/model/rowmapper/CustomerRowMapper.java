package com.storage.site.model.rowmapper;

import com.storage.site.model.Customer;
import com.storage.site.model.Transaction;
import com.storage.site.util.DateUtil;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerRowMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs, int rowNumber) throws SQLException {

        return new Customer(
                rs.getInt("id"),
                rs.getString("stripe_id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("phone_number"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("street_address"),
                rs.getString("second_street_address"),
                rs.getString("city"),
                Customer.State.valueOf(rs.getString("state").toUpperCase()),
                rs.getString("zip"),
                DateUtil.stringToDate(rs.getString("date_joined")),
                (rs.getInt("is_admin") == 1)
        );
    }
}
