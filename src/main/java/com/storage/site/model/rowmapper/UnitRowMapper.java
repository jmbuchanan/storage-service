package com.storage.site.model.rowmapper;

import com.storage.site.model.Unit;
import com.storage.site.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Slf4j
@Component
public class UnitRowMapper implements RowMapper<Unit> {

    @Override
    public Unit mapRow(ResultSet rs, int rowNumber) throws SQLException {

        return new Unit(
                rs.getInt("id"),
                rs.getInt("price_id"),
                rs.getInt("customer_id")
        );
    }
}
