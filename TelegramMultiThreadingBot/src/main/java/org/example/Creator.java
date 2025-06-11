package org.example;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Creator {
    public static void createXlsx(ArrayList<Account> accountsList) {
        String filePath = "src\\main\\resources\\Data\\";
        File file;
        Workbook workbook;
        file = new File(filePath + "Data.xlsx");
        try (FileInputStream fis = new FileInputStream(file)) {
            workbook = WorkbookFactory.create(fis);
            Row row;
            Sheet sheet = workbook.getSheetAt(0);
            Account currentAccount;
            for (int i = sheet.getLastRowNum(); i >= 1; i--) {
                sheet.removeRow(sheet.getRow(i));
            }

            for (int i = 0; i < accountsList.size(); i++) {
                row = sheet.createRow(i + 1);
                currentAccount = accountsList.get(i);

                row.createCell(0).setCellValue(currentAccount.getUsername());
                row.createCell(1).setCellValue(currentAccount.getPassword());
                row.createCell(2).setCellValue(currentAccount.getBalance());
            }
            try (FileOutputStream fos = new FileOutputStream(filePath + "Data.xlsx")) {
                workbook.write(fos);
            }
            workbook.close();
        } catch (Exception e) {
            System.err.println("Ошибка при создании файла " + e.getMessage());
        }
    }
}
