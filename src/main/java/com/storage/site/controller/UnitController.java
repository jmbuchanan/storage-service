package com.storage.site.controller;

import com.storage.site.dto.BookRequest;
import com.storage.site.model.Unit;
import com.storage.site.service.ExcelService;
import com.storage.site.service.UnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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

    @PostMapping("/book")
    public ResponseEntity<String> bookUnit(@RequestBody BookRequest bookRequest) {
        log.info(bookRequest.toString());
        return new ResponseEntity<>("Okay", HttpStatus.OK);
    }
}
