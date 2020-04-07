package com.storage.site.controller;

import com.storage.site.model.Transaction;
import com.storage.site.service.ExcelService;
import com.storage.site.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    ExcelService excelService;

    @GetMapping("/getAllTransactions")
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/getAllTransactions/export")
    public ResponseEntity<byte[]> exportExcelWorkbook() {
        return excelService.generateTransactionWorkbook();
    }
}

