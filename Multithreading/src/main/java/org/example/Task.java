package org.example;

public class Task{
    private String name;
    private int length;
    private String status;

    public Task(String name, int length, String status){
        this.name = name;
        this.length = length;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
