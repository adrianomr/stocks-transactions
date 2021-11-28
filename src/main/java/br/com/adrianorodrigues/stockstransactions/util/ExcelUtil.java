package br.com.adrianorodrigues.stockstransactions.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
public class ExcelUtil {

    private ExcelUtil() {
    }

    public static Sheet getSheet(MultipartFile file) {
        try {
            return getWorkbook(file).getSheetAt(0);
        } catch (IOException e) {
            log.error("Unable to read file", e);
            throw new RuntimeException("Unable to read file", e);
        }
    }

    public static Workbook getWorkbook(MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();
        if (originalName == null) {
            throw new RuntimeException("Unable to read file");
        }
        Workbook workbook;
        String lowerCaseFileName = originalName.toLowerCase();
        if (lowerCaseFileName.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else {
            workbook = new HSSFWorkbook(file.getInputStream());
        }
        return workbook;
    }

}