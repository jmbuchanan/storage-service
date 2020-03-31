package com.storage.site.controller;

import com.storage.site.model.Unit;
import com.storage.site.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/units")
public class UnitController {

    @Autowired
    UnitService unitService;

    @GetMapping("/getAllUnits")
    public List<Unit> getAllUnits() {
        return unitService.getAllUnits();
    }

/*
    @Autowired
    UnitRepository unitRepository;

    @GetMapping("/getAllUnits")
    public List<Unit> getAllUnits() {
        List<Unit> units = new ArrayList<>();
        unitRepository.findAll().forEach(
                unit -> units.add(unit));
        return units;
    }

    @GetMapping("/getAllUnits/export")
    public ResponseEntity<?> exportAsExcel() throws IOException {
        List<Unit> units = new ArrayList<>();
        unitRepository.findAll().forEach(
                unit -> units.add(unit));


        //Create Workbook

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Units");

        //Create Headers
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Unit Number");
        row.createCell(1).setCellValue("Large");
        row.createCell(2).setCellValue("Occupied");
        row.createCell(3).setCellValue("Start Date");
        row.createCell(4).setCellValue("Delinquent");
        row.createCell(5).setCellValue("Days Delinquent");

        //Write Data
        for (int i = 1; i < units.size(); i++) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(units.get(i).getUnitNumber());
            row.createCell(1).setCellValue(units.get(i).isLarge());
            row.createCell(2).setCellValue(units.get(i).isOccupied());
            row.createCell(3).setCellValue(units.get(i).getStartDate());
            row.createCell(4).setCellValue(units.get(i).isDelinquent());
            row.createCell(5).setCellValue(units.get(i).getDaysDelinquent());
        }

        //Autosize Columns
        IntStream.range(0,5).forEach(column -> sheet.autoSizeColumn(column));

        //Write Workbook to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        wb.write(baos);
        byte[] bytes = baos.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Units.xlsx");

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
 */
}
