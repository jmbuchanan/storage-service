package com.storage.site.controller;

import com.storage.site.model.StorageUnit;
import com.storage.site.repository.StorageUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/units")
public class StorageUnitController {

    @Autowired
    StorageUnitRepository storageUnitRepository;

    @GetMapping("/getAllUnits")
    public List<StorageUnit> getAllUnits() {
        List<StorageUnit> units = new ArrayList<>();
        storageUnitRepository.findAll().forEach(x -> units.add(x));
        return units;
    }
}

