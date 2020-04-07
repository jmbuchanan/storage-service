package com.storage.site.controller;

import com.storage.site.model.Unit;
import com.storage.site.service.ExcelService;
import com.storage.site.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/units")
public class UnitController {

    @Autowired
    UnitService unitService;

    @Autowired
    ExcelService excelService;

    @GetMapping("/getAllUnits")
    public List<Unit> getAllUnits() {
        return unitService.getAllUnits();
    }

    @GetMapping("/getAllUnits/export")
    public ResponseEntity<byte[]> exportExcelWorkbook() {
        return excelService.generateUnitWorkbook();
    }
}
