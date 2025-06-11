package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;

public class Reader {
    public static ArrayList<Account> readXlsx() throws Exception{
        ArrayList<Account> accountsList = new ArrayList<Account>(0);
        String filePath = "src\\main\\resources\\Data\\Data.xlsx";

        FileInputStream file = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(file);

        //Считываем задачи
        Sheet sheet = workbook.getSheet("Accounts");
        DataFormatter formatter = new DataFormatter();

        Row currentRow;
        Cell currentCell;
        String currentUsername;
        String currentPassword;
        int currentBalance;
        int lastRowNum = sheet.getLastRowNum();


        for (int rowIndex = 1;rowIndex<=lastRowNum;rowIndex++){
            currentRow = sheet.getRow(rowIndex);
            currentCell = currentRow.getCell(0);
            currentUsername = formatter.formatCellValue(currentCell);
            currentCell = currentRow.getCell(1);
            currentPassword = formatter.formatCellValue(currentCell);
            currentCell = currentRow.getCell(2);
            currentBalance = Integer.valueOf(formatter.formatCellValue(currentCell));

            accountsList.add(new Account(currentUsername,currentPassword,currentBalance));
        }
        return accountsList;
    }
}
