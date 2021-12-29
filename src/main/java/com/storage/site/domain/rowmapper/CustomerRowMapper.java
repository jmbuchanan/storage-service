package com.storage.site.domain.rowmapper;

import com.storage.site.domain.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerRowMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs, int rowNumber) throws SQLException {

        return Customer.builder()
                .id(rs.getInt("id"))
                .stripeId(rs.getString("stripe_id"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .phoneNumber(rs.getString("phone_number"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .streetAddress(rs.getString("street_address"))
                .secondStreetAddress(rs.getString("second_street_address"))
                .city(rs.getString("city"))
                .state(Customer.State.valueOf(rs.getString("state").toUpperCase()))
                .zip(rs.getString("zip"))
                .dateJoined(rs.getDate("date_joined"))
                .isAdmin(rs.getBoolean("is_admin"))
                .build();
    }
}
