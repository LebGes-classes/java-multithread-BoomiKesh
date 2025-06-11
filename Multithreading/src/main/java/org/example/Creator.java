package org.example;

import org.apache.poi.ss.usermodel.*;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Creator {
    public static void createXlsx(ArrayList<Task> tasksList,ArrayList<Employee> employeeList){
        String filePath = "src\\main\\resources\\Output\\";
        File file;
        Workbook workbook;
        file = new File(filePath+"Office.xlsx");
        try (FileInputStream fis = new FileInputStream(file)) {
            workbook = WorkbookFactory.create(fis);
            Row row;

            //Заполняем сотрудников
            Sheet sheet = workbook.getSheetAt(0);
            Employee currentEmployee;
            for (int i = sheet.getLastRowNum(); i >= 1; i--) {
                sheet.removeRow(sheet.getRow(i));
            }

            for (int i=0;i<employeeList.size();i++){
                row = sheet.createRow(i+1);
                currentEmployee = employeeList.get(i);


                row.createCell(0).setCellValue(currentEmployee.getFio());

                try {
                    currentEmployee.getCurrentTask().getName();
                    System.out.println(currentEmployee.getCurrentTask().getName());
                    row.createCell(1).setCellValue(currentEmployee.getCurrentTask().getName());
                    row.createCell(2).setCellValue(currentEmployee.getSavedProgress());

                }catch (Exception e2){

                }
                row.createCell(3).setCellValue(currentEmployee.getRestTime());
            }

            //Заполняем задачи
            Task currentTask;
            sheet = workbook.getSheetAt(1);
            for (int i = sheet.getLastRowNum(); i >= 1; i--) {
                sheet.removeRow(sheet.getRow(i));
            }
            for (int i=0;i<tasksList.size();i++){
                row = sheet.createRow(i+1);
                currentTask = tasksList.get(i);
                row.createCell(0).setCellValue(currentTask.getName());

                row.createCell(1).setCellValue(currentTask.getLength());
                row.createCell(2).setCellValue(currentTask.getStatus());
            }
            try (FileOutputStream fos = new FileOutputStream(filePath+"Office.xlsx")) {
                workbook.write(fos);
            }
            workbook.close();
        }catch(Exception e){
            System.err.println("Ошибка при создании файла " + e.getMessage());
        }
    }
}
