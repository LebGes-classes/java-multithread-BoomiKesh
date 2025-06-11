package org.example;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;

public class Reader {
    private ArrayList<Task> tasksList= new ArrayList<Task>(0);
    private ArrayList<Employee> employeeList  = new ArrayList<Employee>(0);

    public void readExcel() throws Exception {

        String filePath = "src\\main\\resources\\Input\\Office.xlsx";

        FileInputStream file = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(file);

        //Считываем задачи
        Sheet sheet = workbook.getSheet("Задачи");
        DataFormatter formatter = new DataFormatter();

        Row currentRow;
        Cell currentCell;
        String currentName;
        String currentFIO;
        int currentLength;
        int currentProgress;
        String currentStatus;
        String currentTaskName;
        Task currentTask;
        int lastRowNum = sheet.getLastRowNum();


        for (int rowIndex = 1;rowIndex<=lastRowNum;rowIndex++){

            currentRow = sheet.getRow(rowIndex);
            currentCell = currentRow.getCell(0);
            currentName = formatter.formatCellValue(currentCell);
            currentCell = currentRow.getCell(1);
            currentLength = Integer.valueOf(formatter.formatCellValue(currentCell));
            currentCell = currentRow.getCell(2);
            currentStatus = formatter.formatCellValue(currentCell);
            tasksList.add(new Task(currentName,currentLength,currentStatus));
        }


        sheet = workbook.getSheet("Сотрудники");
        lastRowNum = sheet.getLastRowNum();

        for (int rowIndex = 1;rowIndex<=lastRowNum;rowIndex++){
            currentRow = sheet.getRow(rowIndex);
            currentCell = currentRow.getCell(0);
            currentFIO = formatter.formatCellValue(currentCell);
            if (currentRow.getCell(1)!=null) {
                currentCell = currentRow.getCell(1);
                currentTaskName = formatter.formatCellValue(currentCell);
                currentTask = null;
                for (int i = 0; i < tasksList.size(); i++) {
                    if (tasksList.get(i).getName().equals(currentTaskName)) {
                        currentTask = tasksList.get(i);
                    }
                }
                currentCell = currentRow.getCell(2);
                currentProgress = Integer.valueOf(formatter.formatCellValue(currentCell));
                employeeList.add(new Employee(currentFIO,currentTask,currentProgress,8));
            }else{
                employeeList.add(new Employee(currentFIO));
            }
        }
    }

    public ArrayList<Employee> getEmployeeList() {
        return employeeList;
    }

    public ArrayList<Task> getTasksList() {
        return tasksList;
    }
}
