package com.storage.site.controller;

import com.storage.site.dto.BookRequest;
import com.storage.site.model.Unit;
import com.storage.site.service.ExcelService;
import com.storage.site.service.JwtService;
import com.storage.site.service.UnitService;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/units")
@AllArgsConstructor
public class UnitController {

    private final UnitService unitService;
    private final JwtService jwtService;
    private final ExcelService excelService;

    @GetMapping("/getAllUnits")
    public List<Unit> getAllUnits() {
        return unitService.getAllUnits();
    }

    @GetMapping("/getAllUnits/export")
    public ResponseEntity<byte[]> exportExcelWorkbook() {
        return excelService.generateUnitWorkbook();
    }

    @GetMapping("/fetchByCustomerId")
    public List<Unit> fetchByCustomerId(HttpServletRequest request) {
        int customerId = jwtService.parseCustomerId(request);
        return unitService.getUnitsByCustomerId(customerId);
    }
}
