package com.storage.site.domain.rowmapper;

import com.storage.site.domain.Unit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Component
public class UnitRowMapper implements RowMapper<Unit> {

    @Override
    public Unit mapRow(ResultSet rs, int rowNumber) throws SQLException {

        return new Unit(
                rs.getInt("id"),
                rs.getInt("price_id")
        );
    }
}
