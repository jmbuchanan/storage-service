package com.storage.site.controller;

import com.storage.site.model.Customer;
import com.storage.site.repository.CustomerRepository;
import com.storage.site.service.CustomerService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/getAllCustomers")
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(
                customer -> customers.add(customer));
        return customers;
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<String> addCustomer(HttpServletRequest formData) {
        String email = formData.getParameter("email");
        String password = passwordEncoder.encode(formData.getParameter("password"));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/html; charset=UTF-8");

        if (customerService.customerExists(email)) {
            return new ResponseEntity<String>("Account Exists", headers, HttpStatus.CONFLICT);
        }

        else {
            Customer customer = new Customer(email, password, "314-442-5781", "Bjorg",
                    "Dahl", "855 Hollywood Blvd", "Apt 1315", "SC",
                    "43301", "USA", false);
            customerRepository.save(customer);

            return new ResponseEntity<String>("Saved Successfully!",  headers, HttpStatus.SEE_OTHER);
        }

    }

    @GetMapping("/getAllCustomers/export")
    public ResponseEntity<?> exportAsExcel() throws IOException {
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(
                customer -> customers.add(customer));


        //Create Workbook

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Customers");

        //Create Headers
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("First Name");
        row.createCell(1).setCellValue("Last Name");
        row.createCell(2).setCellValue("Email");
        row.createCell(3).setCellValue("Phone Number");
        row.createCell(4).setCellValue("Street Address");
        row.createCell(5).setCellValue("Street Address 2");
        row.createCell(6).setCellValue("State");
        row.createCell(7).setCellValue("Zip");
        row.createCell(8).setCellValue("Country");
        row.createCell(9).setCellValue("Admin");

        //Write Data
        for (var i = 1; i < customers.size(); i++) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(customers.get(i).getFirstName());
            row.createCell(1).setCellValue(customers.get(i).getLastName());
            row.createCell(2).setCellValue(customers.get(i).getEmail());
            row.createCell(3).setCellValue(customers.get(i).getPhoneNumber());
            row.createCell(4).setCellValue(customers.get(i).getStreetAddress());
            row.createCell(5).setCellValue(customers.get(i).getSecondStreetAddress());
            row.createCell(6).setCellValue(customers.get(i).getState());
            row.createCell(7).setCellValue(customers.get(i).getZip());
            row.createCell(8).setCellValue(customers.get(i).getCountry());
            row.createCell(9).setCellValue(customers.get(i).isAdmin());
        }

        //Autosize Columns
        IntStream.range(0,9).forEach(column -> sheet.autoSizeColumn(column));

        //Write Workbook to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        wb.write(baos);
        byte[] bytes = baos.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Customers.xlsx");

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }


}

