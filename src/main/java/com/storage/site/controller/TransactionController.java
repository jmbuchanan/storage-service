package com.storage.site.controller;

import com.storage.site.dto.BookRequest;
import com.storage.site.dto.CancelRequest;
import com.storage.site.model.Transaction;
import com.storage.site.service.ExcelService;
import com.storage.site.service.TransactionService;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final ExcelService excelService;

    @PostMapping("/book")
    public ResponseEntity<String> book(@RequestBody BookRequest bookRequest, HttpServletRequest httpRequest) throws StripeException {
        log.info(bookRequest.toString());
        if (transactionService.insertPendingTransaction(bookRequest, httpRequest)) {
            return new ResponseEntity<>("Unit booked", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No unit available", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancel(@RequestBody CancelRequest cancelRequest, HttpServletRequest httpRequest) {
        transactionService.handleCancelRequest(cancelRequest, httpRequest);
        return new ResponseEntity<>("Resource updated", HttpStatus.OK);
    }

    @GetMapping("/cancel/eligibility/{id}")
    public int getCancelEligibilityForUnit(@PathVariable int id) {
        return transactionService.getCancelEligibilityForUnit(id);
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

