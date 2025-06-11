package org.example;

public class Main {
    public static void main(String[] args) {
        Reader currentReader = new Reader();
        try{
            currentReader.readExcel();
        }catch(Exception e){
            System.err.println("Ошибка при чтении файла");
        }
        TimeThread mainThread = new TimeThread(currentReader);
        mainThread.run();

    }
}