package com.storage.site.service;

import com.storage.site.model.Price;
import com.storage.site.model.rowmapper.PriceRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PriceRowMapper priceRowMapper;

    public Price getPriceById(int id) {
        List<Price> prices = jdbcTemplate.query("SELECT * FROM prices WHERE id = ?", new Object[] {id}, priceRowMapper);
        return prices.get(0);
    }
}
