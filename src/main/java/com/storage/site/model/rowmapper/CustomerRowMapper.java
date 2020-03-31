package com.storage.site.model.rowmapper;

import com.storage.site.model.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerRowMapper implements RowMapper {

    @Override
    public Customer mapRow(ResultSet rs, int rowNumber) throws SQLException {

        return new Customer(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("phone_number"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("street_address"),
                rs.getString("second_street_address"),
                rs.getString("state"),
                rs.getString("zip"),
                rs.getString("country"),
                rs.getBoolean("is_admin")
        );
    }
}