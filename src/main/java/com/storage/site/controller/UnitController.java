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
    public ResponseEntity<String> cancelSubscription(@PathVariable Long unitNumber) {
        log.info("PUT request received for unit number " + unitNumber);
        if (unitService.cancelSubscription(unitNumber)) {
            return new ResponseEntity<>("Resource updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Issue updated resource", HttpStatus.NOT_FOUND);
        }
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

    private Subscription makeSubscription() throws StripeException {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date nextMonthFirstDay = calendar.getTime();

        List<Object> items = new ArrayList<>();
        Map<String, Object> price = new HashMap<>();
        price.put(
                "price",
                "price_1I8y8bBBzZIBZ7GfAOedK0Dk"
        );
        items.add(price);
        Map<String, Object> params = new HashMap<>();
        params.put("customer", "cus_IkTnsw6FFLRQLc");
        params.put("items", items);
        params.put("default_payment_method", "pm_1I8yqZBBzZIBZ7GfByWaOQPw");
        params.put("billing_cycle_anchor", nextMonthFirstDay);

        return Subscription.create(params);
    }
}
