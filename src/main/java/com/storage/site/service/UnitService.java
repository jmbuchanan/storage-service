package com.storage.site.service;

import com.storage.site.dao.UnitDao;
import com.storage.site.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class UnitService {

    private final UnitDao unitDao;

    public List<Unit> getAllUnits() {
        return unitDao.fetchAll();
    }

    public Unit getUnitById(int id) {
        return unitDao.fetchUnitById(id);
    }

    public List<Unit> getUnitsByCustomerId(int id) {
        return unitDao.fetchUnitsByCustomerId(id);
    }

    public Unit getOneUnitForPrice(int id) {
        return unitDao.fetchOneAvailableUnitByPrice(id);
    }

    public void updateCustomerOfUnit(int customerId, int unitId) {
        unitDao.updateCustomerOfUnit(customerId, unitId);
        log.info(String.format("Unit %s booked for customer %s", unitId, customerId));
    }

    public void cancelSubscription(int unitNumber) {
        unitDao.setCustomerToNullForUnit(unitNumber);
    }


}
