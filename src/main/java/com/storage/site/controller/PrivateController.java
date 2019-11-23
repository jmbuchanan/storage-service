package com.storage.site.controller;

import com.storage.site.model.StorageUnit;
import com.storage.site.repository.StorageUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/private")
public class PrivateController {

    @Autowired
    StorageUnitRepository storageUnitRepository;

    @GetMapping
    public List<StorageUnit> getAllUnits() {
        List<StorageUnit> units = new ArrayList<>();
        storageUnitRepository.findAll().forEach(
                unit -> units.add(unit));
        return units;
    }
}

