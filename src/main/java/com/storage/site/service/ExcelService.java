package com.storage.site.service;

import com.storage.site.model.Customer;
import com.storage.site.model.Transaction;
import com.storage.site.model.Unit;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class ExcelService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UnitService unitService;

    private XSSFColor headerColor = new XSSFColor(new java.awt.Color(33, 47, 61), new DefaultIndexedColorMap());

    public ResponseEntity<byte[]> generateCustomerWorkbook() {

        List<Customer> customers = new ArrayList<>();
        customerService.getAllCustomers().forEach(
                customer -> customers.add(customer));


        //Create Workbook
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Customers");

        XSSFCellStyle style = makeHeaderStyle(wb);

        //Create Headers
        List<String> header = new ArrayList<>();
        header.add("First Name");
        header.add("Last Name");
        header.add("Email");
        header.add("Phone Number");
        header.add("Street Address");
        header.add("Street Address 2");
        header.add("City");
        header.add("State");
        header.add("Zip");
        header.add("Admin");

        Row row = sheet.createRow(0);

        for (int i = 0; i < header.size(); i++) {
            row.createCell(i).setCellValue(header.get(i));
            row.getCell(i).setCellStyle(style);
        }

        //Write Data
        for (int i = 0; i < customers.size(); i++) {
            row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(customers.get(i).getFirstName());
            row.createCell(1).setCellValue(customers.get(i).getLastName());
            row.createCell(2).setCellValue(customers.get(i).getEmail());
            row.createCell(3).setCellValue(customers.get(i).getPhoneNumber());
            row.createCell(4).setCellValue(customers.get(i).getStreetAddress());
            row.createCell(5).setCellValue(customers.get(i).getSecondStreetAddress());
            row.createCell(6).setCellValue(customers.get(i).getCity());
            row.createCell(7).setCellValue(customers.get(i).getState().toString());
            row.createCell(8).setCellValue(customers.get(i).getZip());
            row.createCell(9).setCellValue(customers.get(i).isAdmin());
        }

        //Autosize Columns
        IntStream.range(0, 9).forEach(column -> sheet.autoSizeColumn(column));

        //Write Workbook to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            wb.write(baos);
        } catch (Exception e) {
            e.getMessage();
        }

        byte[] bytes = baos.toByteArray();

        HttpHeaders headers = new HttpHeaders();

        Date date = new Date();
        Format formatter = new SimpleDateFormat("MM-dd-yy");
        String formattedDate = formatter.format(date);

        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Customers " + formattedDate + ".xlsx");

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    public ResponseEntity<byte[]> generateTransactionWorkbook() {

        List<Transaction> transactions = new ArrayList<>();
        transactionService.getAllTransactions().forEach(
                transaction -> transactions.add(transaction));

        //Create Workbook
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Transactions");

        XSSFCellStyle style = makeHeaderStyle(wb);

        //Create Headers
        List<String> header = new ArrayList<>();
        header.add("Transaction Id");
        header.add("Type");
        header.add("Date");
        header.add("Amount");
        header.add("Customer Id");
        header.add("Unit Id");

        Row row = sheet.createRow(0);

        for (int i = 0; i < header.size(); i++) {
            row.createCell(i).setCellValue(header.get(i));
            row.getCell(i).setCellStyle(style);
        }

        //Write Data
        for (int i = 0; i < transactions.size(); i++) {
            row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(transactions.get(i).getId());
            row.createCell(1).setCellValue(transactions.get(i).getType().toString());
            row.createCell(4).setCellValue(transactions.get(i).getCustomerId());
            row.createCell(5).setCellValue(transactions.get(i).getUnitId());
        }

        //Autosize Columns
        IntStream.range(0,5).forEach(column -> sheet.autoSizeColumn(column));

        //Write Workbook to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            wb.write(baos);
        } catch (Exception e) {
            e.getMessage();
        }

        byte[] bytes = baos.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Transactions.xlsx");

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    public ResponseEntity<byte[]> generateUnitWorkbook() {

        List<Unit> units = new ArrayList<>();
        unitService.getAllUnits().forEach(
                unit -> units.add(unit));

        //Create Workbook

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Units");

        XSSFCellStyle style = makeHeaderStyle(wb);

        //Create Headers
        List<String> header = new ArrayList<>();
        header.add("Unit Number");
        header.add("Large");
        header.add("Occupied");
        header.add("Start Date");

        Row row = sheet.createRow(0);

        for (int i = 0; i < header.size(); i++) {
            row.createCell(i).setCellValue(header.get(i));
            row.getCell(i).setCellStyle(style);
        }

        //Write Data
        for (int i = 0; i < units.size(); i++) {
            row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(units.get(i).getUnitNumber());
        }

        //Autosize Columns
        IntStream.range(0,3).forEach(column -> sheet.autoSizeColumn(column));

        //Write Workbook to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            wb.write(baos);
        } catch (Exception e) {
            e.getMessage();
        }

        byte[] bytes = baos.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Units.xlsx");

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    private XSSFCellStyle makeHeaderStyle(XSSFWorkbook wb) {

        Font font = wb.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());

        XSSFCellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(headerColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(font);

        return style;
    }
}
