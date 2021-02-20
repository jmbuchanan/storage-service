package com.storage.site.controller;

import com.storage.site.dto.BookRequest;
import com.storage.site.model.Unit;
import com.storage.site.service.ExcelService;
import com.storage.site.service.JwtService;
import com.storage.site.service.UnitService;
import com.stripe.exception.StripeException;
import com.stripe.model.Subscription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/units")
public class UnitController {

    @Autowired
    private UnitService unitService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ExcelService excelService;

    @GetMapping("/getAllUnits")
    public List<Unit> getAllUnits() {
        return unitService.getAllUnits();
    }

    @GetMapping("/getAllUnits/export")
    public ResponseEntity<byte[]> exportExcelWorkbook() {
        return excelService.generateUnitWorkbook();
    }

    @PutMapping("/{unitNumber}")
    public ResponseEntity<String> cancelSubscription(@PathVariable int unitNumber) {
        log.info("PUT request received for unit number " + unitNumber);
        unitService.cancelSubscription(unitNumber);
        return new ResponseEntity<>("Resource updated", HttpStatus.OK);
    }

    @GetMapping("/fetchByCustomerId")
    public List<Unit> fetchByCustomerId(HttpServletRequest request) {
        int customerId = jwtService.parseCustomerId(request);
        return unitService.getUnitsByCustomerId(customerId);
    }

    @PostMapping("/book")
    public ResponseEntity<String> bookUnit(@RequestBody BookRequest bookRequest) throws StripeException {
        log.info(bookRequest.toString());
        Unit bookedUnit = unitService.bookUnit(bookRequest);
        if (bookedUnit != null) {
            return new ResponseEntity<>("Unit booked", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No unit available", HttpStatus.CONFLICT);
        }
    }
}
