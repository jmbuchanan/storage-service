package com.storage.site.model.rowmapper;

import com.storage.site.model.Price;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Component
public class PriceRowMapper implements RowMapper<Price> {

    @Override
    public Price mapRow(ResultSet rs, int rowNumber) throws SQLException {

        return new Price(
                rs.getInt("id"),
                rs.getString("stripe_id"),
                rs.getInt("price")
        );
    }
}
