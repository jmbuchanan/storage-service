package com.storage.site.service;

import com.storage.site.dto.BookRequest;
import com.storage.site.model.Unit;
import com.storage.site.model.rowmapper.UnitRowMapper;
import com.storage.site.util.DateUtil;
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
        boolean isLarge = bookRequest.getUnitSize().equals("1");
        List<Unit> units = jdbcTemplate.query("SELECT * FROM units WHERE is_occupied = false AND is_large = ? LIMIT 1;", new Object[] {isLarge}, unitRowMapper);
        if (units.size() == 0) {
            return null;
        }
        Unit unit = units.get(0);
        jdbcTemplate.update("UPDATE units SET is_occupied = true, start_date = ?, customer_id = ? WHERE id = ?;",
                DateUtil.stringToDate(bookRequest.getStartDate()), bookRequest.getCustomerId(), unit.getUnitNumber());
        log.info(String.format("Unit %s booked for customer %s", unit.getUnitNumber(), bookRequest.getCustomerId()));
        return unit;
    }

    public boolean cancelSubscription(Long unitNumber) {
        jdbcTemplate.update("UPDATE units SET is_occupied = false, start_date = null, customer_id = null WHERE id = ?;", unitNumber);
        return true;
    }
}
