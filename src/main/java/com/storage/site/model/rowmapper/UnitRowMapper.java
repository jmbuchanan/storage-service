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
        String dateStr = rs.getString("start_date");
        Date date;
        if (dateStr != null) {
            date = DateUtil.stringToDate(rs.getString("start_date"));
        } else {
            date = new Date();
        }

        return new Unit(
                rs.getInt("id"),
                rs.getInt("is_large") == 1,
                rs.getInt("is_occupied") == 1,
                rs.getInt("is_delinquent") == 1,
                rs.getInt("days_delinquent"),
                date
        );
    }
}
