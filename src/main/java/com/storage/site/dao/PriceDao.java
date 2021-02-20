package com.storage.site.dao;

import com.storage.site.model.Price;
import com.storage.site.model.rowmapper.PriceRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PriceDao {

    private final JdbcTemplate jdbcTemplate;
    private final PriceRowMapper priceRowMapper;

    public PriceDao(JdbcTemplate jdbcTemplate, PriceRowMapper priceRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.priceRowMapper = priceRowMapper;
    }

    public Price getPriceById(int id) {
        List<Price> prices = jdbcTemplate.query("SELECT * FROM prices WHERE id = ?", new Object[] {id}, priceRowMapper);
        return prices.get(0);
    }

}
