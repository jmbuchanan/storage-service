package com.storage.site.model.rowmapper;

import com.storage.site.model.Unit;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UnitRowMapper implements RowMapper<Unit> {

    @Override
    public Unit mapRow(ResultSet rs, int rowNumber) throws SQLException {
        return new Unit(
                rs.getByte("id"),
                rs.getBoolean("is_large"),
                rs.getBoolean("is_occupied"),
                rs.getBoolean("is_delinquent"),
                rs.getShort("days_delinquent"),
                rs.getDate("start_date")
        );
    }
}
