package org.example;


public class Employee extends Thread{
    private String fio;
    private Task currentTask = null;
    private int savedProgress = 0;
    private int restTime;


    public Employee(String fio){
        this.fio = fio;
        this.restTime = 8;
    }
    public Employee(String fio, Task currentTask,int savedProgress,int restTime){
        this.fio=fio;
        this.currentTask=currentTask;
        this.savedProgress=savedProgress;
        this.restTime = restTime;
    }

    @Override
    public void run(){
        System.out.println(fio + "  Запущен");
        this.savedProgress+=1;
        this.restTime-=1;
        if (savedProgress == currentTask.getLength()){
            System.out.println(fio+" закончил задание "+currentTask.getName());
            currentTask.setStatus("выполнено");
            finishTask();
        }else {
            System.out.println(fio + " делает задание " + savedProgress + " часов из " + currentTask.getLength());
        }
    }

    public void takeTask(Task currentTask){
        this.currentTask = currentTask;
        this.savedProgress=0;
    }

    public void finishTask(){
        this.currentTask = null;
        this.savedProgress=0;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public String getFio() {
        return fio;
    }

    public int getSavedProgress() {
        return savedProgress;
    }

    public int getRestTime() {
        return restTime;
    }
}
