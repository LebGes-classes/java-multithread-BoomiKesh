package org.example;

//import java.util.ArrayList;
//
//public class TaskGiver{
//    ArrayList<Task> tasksList = new ArrayList<Task>(0);
//    public TaskGiver(ArrayList<Task> tasksList){
//        this.tasksList = tasksList;
//    }
//
//    public synchronized Task giveTask(){
//        for(int i=0;i<tasksList.size();i++){
//            if(tasksList.get(i).getStatus().equals("Не начато")){
//                tasksList.get(i).setStatus("В работе");
//                return tasksList.get(i);
//            }
//        }
//        return null;
//    }
//}
