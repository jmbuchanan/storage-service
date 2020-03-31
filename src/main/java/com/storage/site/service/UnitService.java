package com.storage.site.service;

import com.storage.site.model.Unit;
import com.storage.site.model.rowmapper.UnitRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
