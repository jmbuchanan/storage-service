package com.storage.site.dao;

import com.storage.site.model.Unit;
import com.storage.site.model.rowmapper.UnitRowMapper;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UnitDao {

    private final JdbcTemplate jdbcTemplate;
    private final UnitRowMapper unitRowMapper;

    private static final String SELECT_ALL_UNITS =
            "SELECT * FROM units";

    private static final String SELECT_UNITS_BY_CUSTOMER_ID =
            "SELECT * FROM units WHERE customer_id = ?";

    private static final String SELECT_UNIT_BY_ID =
            "SELECT * FROM units WHERE id = ?";

    private static final String SELECT_AVAILABLE_UNIT_BY_PRICE =
            "SELECT * FROM units WHERE customer_id IS NULL AND price_id = ? ORDER BY id ASC LIMIT 1";

    private static final String UPDATE_CUSTOMER_FOR_UNIT =
            "UPDATE units SET customer_id = ? WHERE id = ?";

    private static final String SET_UNIT_CUSTOMER_TO_NULL =
            "UPDATE units SET customer_id = null WHERE id = ?";

    public List<Unit> fetchAll() {
        return jdbcTemplate.query(SELECT_ALL_UNITS, unitRowMapper);
    }

    public List<Unit> fetchUnitsByCustomerId(int id) {
        return jdbcTemplate.query(SELECT_UNITS_BY_CUSTOMER_ID, new Object[] {id}, unitRowMapper);
    }

    public Unit fetchUnitById(int id) {
        List<Unit> units = jdbcTemplate.query(SELECT_UNIT_BY_ID, new Object[] {id}, unitRowMapper);
        return units.get(0);
    }

    public Unit fetchOneAvailableUnitByPrice(int id) {
        List<Unit> units = jdbcTemplate.query(SELECT_AVAILABLE_UNIT_BY_PRICE, new Object[] {id}, unitRowMapper);
        return units.get(0);
    }

    public void updateCustomerOfUnit(int customerId, int unitId) {
        jdbcTemplate.update(UPDATE_CUSTOMER_FOR_UNIT, customerId, unitId);
    }

    public void setUnitCustomerToNull(int unitId) {
        jdbcTemplate.update(SET_UNIT_CUSTOMER_TO_NULL, unitId);
    }
}
