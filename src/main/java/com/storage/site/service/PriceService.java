package com.storage.site.service;

import com.storage.site.dao.PriceDao;
import com.storage.site.model.Price;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

    private final PriceDao priceDao;

    public PriceService(PriceDao priceDao) {
        this.priceDao = priceDao;
    }

    public Price getPriceById(int id) {
        return priceDao.getPriceById(id);
    }
}
