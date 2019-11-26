package com.storage.site.controller;

import com.storage.site.model.Unit;
import com.storage.site.repository.StorageUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/units")
public class UnitsController {

    @Autowired
    StorageUnitRepository storageUnitRepository;

    @GetMapping("/getAllUnits")
    public List<Unit> getAllUnits() {
        List<Unit> units = new ArrayList<>();
        storageUnitRepository.findAll().forEach(
                unit -> units.add(unit));
        return units;
    }
}

