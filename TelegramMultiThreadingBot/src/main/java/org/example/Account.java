package org.example;

public class Account {
    String username;
    String password;
    boolean status = false;
    int balance;
    public Account(String username,String password){
        this.username=username;
        this.password = password;
        balance = 0;
    }
    public Account(String username,String password,int balance){
        this.username=username;
        this.password = password;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getBalance() {
        return balance;
    }
    public synchronized boolean getStatus(){
        return status;
    }
    public synchronized void changeBalance(int value){
        this.balance = balance+value;
    }

    public synchronized void setStatus(boolean status) {
        this.status = status;
    }

    public boolean equals(Account account){
        if (this.username.equals(account.getUsername())){
            return true;
        }
        return false;
    }
}
