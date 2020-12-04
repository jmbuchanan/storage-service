package com.storage.site.service;

import com.storage.site.dto.BookRequest;
import com.storage.site.model.Unit;
import com.storage.site.model.rowmapper.UnitRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UnitService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UnitRowMapper unitRowMapper;

    public List<Unit> getAllUnits() {
        List<Unit> units = jdbcTemplate.query("SELECT * FROM units", unitRowMapper);
        return units;
    }

    public List<Unit> getUnitsByCustomerId(int id) {
        return jdbcTemplate.query("SELECT * FROM units WHERE customer_id = ?;", new Object[] {id}, unitRowMapper);
    }

    public Unit bookUnit(BookRequest bookRequest) {
        String size = bookRequest.getUnitSize();
        List<Unit> units = jdbcTemplate.query("SELECT * FROM units WHERE is_occupied = 0 AND is_large = ? LIMIT 1;", new Object[] {size}, unitRowMapper);
        if (units.size() == 0) {
            return null;
        }
        Unit unit = units.get(0);
        jdbcTemplate.update("UPDATE units SET is_occupied = 1, start_date = ?, customer_id = ? WHERE id = ?;",
                bookRequest.getStartDate(), bookRequest.getCustomerId(), unit.getUnitNumber());
        log.info(String.format("Unit %s booked for customer %s", unit.getUnitNumber(), bookRequest.getCustomerId()));
        return unit;
    }
}
