package com.storage.site.controller;

import com.storage.site.dto.BookRequest;
import com.storage.site.model.Transaction;
import com.storage.site.model.Unit;
import com.storage.site.service.ExcelService;
import com.storage.site.service.TransactionService;
import com.storage.site.service.UnitService;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final UnitService unitService;
    private final ExcelService excelService;

    @PostMapping("/book")
    public ResponseEntity<String> book(@RequestBody BookRequest bookRequest) throws StripeException {
        log.info(bookRequest.toString());
        Unit bookedUnit = unitService.bookUnit(bookRequest);
        if (bookedUnit != null) {
            return new ResponseEntity<>("Unit booked", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No unit available", HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancelSubscription(@PathVariable int id) {
        unitService.cancelSubscription(id);
        return new ResponseEntity<>("Resource updated", HttpStatus.OK);
    }

    @GetMapping("/getAllTransactions")
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/getAllTransactions/export")
    public ResponseEntity<byte[]> exportExcelWorkbook() {
        return excelService.generateTransactionWorkbook();
    }
}

