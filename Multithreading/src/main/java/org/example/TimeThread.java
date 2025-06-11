package org.example;

import java.util.ArrayList;

public class TimeThread{
    private ArrayList<Task> tasksList;
    private ArrayList<Employee> employeeList;

    public TimeThread(Reader currentReader){
        this.employeeList = currentReader.getEmployeeList();
        this.tasksList = currentReader.getTasksList();
    }
    public void run() {
        Employee currentEmployee;
        for (int hour = 0; hour < 8; hour++) {
            for (int employeeIndex = 0; employeeIndex < employeeList.size(); employeeIndex++) {
                currentEmployee = employeeList.get(employeeIndex);
                while(currentEmployee.isAlive()){}
                employeeList.set(employeeIndex,new Employee(currentEmployee.getFio(),currentEmployee.getCurrentTask(),currentEmployee.getSavedProgress(),currentEmployee.getRestTime()));

            }
            System.out.println("1");
            for (int employeeIndex = 0; employeeIndex < employeeList.size(); employeeIndex++) {
                currentEmployee = employeeList.get(employeeIndex);
                if(currentEmployee.getCurrentTask() != null) {
                    currentEmployee.start();
                }else {
                    currentEmployee.takeTask(giveTask());
                    if(currentEmployee.getCurrentTask() != null) {
                        System.out.println(currentEmployee.getFio() + " начал задание " + currentEmployee.getCurrentTask().getName());
                        currentEmployee.start();
                    }else{
                        System.out.println("Для " + currentEmployee.getFio() + " нет новых заданий");
                    }
                }
            }
        }
        Creator.createXlsx(tasksList,employeeList);
    }


    private synchronized Task giveTask() {
        for (int i=0;i<tasksList.size();i++) {
            if (tasksList.get(i).getStatus().equals("не начато")){
                tasksList.get(i).setStatus("в работе");
                return tasksList.get(i);
            }
        }
        return null;
    }
}
