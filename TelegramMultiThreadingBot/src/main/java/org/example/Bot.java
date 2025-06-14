package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;


public class Bot extends TelegramLongPollingBot {
    String botState="not logged in";
    String temp_login="";
    String temp_password="";
    int currentAccountIndex;
    int sendingChoiceIndex;
    Account tempAccount;
    String message;
    int currentIndex;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String receivedText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (botState) {
                case "not logged in":
                    switch (receivedText) {
                        case "/help":
                            message = "Доступные команды:\n" +
                                    "/register - создать новый аккаунт\n" +
                                    "/login - войти в аккаунт\n" +
                                    "/help - показать доступные команды";
                            sendTextMessage(chatId, message);
                            break;
                        case "/register":
                            message = "Введите логин:\n"+
                            "/cancel - отменить действие";
                            this.botState = "new login name awaiting";
                            sendTextMessage(chatId, message);
                            break;
                        case "/login":
                            message = "Введите логин:\n"+
                                    "/cancel - отменить действие";
                            this.botState = "login name awaiting";
                            sendTextMessage(chatId, message);
                            break;
                        default:
                            message = "Неизвестная комманда";
                            sendTextMessage(chatId, message);
                            break;
                    }
                    break;
                case "new login name awaiting":
                    if (!receivedText.equals("/cancel")) {
                        if (receivedText.charAt(0) == '/') {
                            message = "Невозможная команда";
                            sendTextMessage(chatId, message);
                            break;
                        }
                        temp_login = receivedText;
                        for (int i=0;i<Data.accountsList.size();i++) {
                            if (Data.accountsList.get(i).getUsername().equals(temp_login)){
                                message = "Такой пользователь уже существует, попробуйте снова";
                                sendTextMessage(chatId, message);
                                break;
                            }
                        }
                        message = "Введите пароль:";
                        botState = "new password awaiting";
                        sendTextMessage(chatId, message);
                    }else {
                        message = "Действие отменено";
                        botState = "not logged in";
                        sendTextMessage(chatId, message);
                    }
                    break;

                case "new password awaiting":
                    if (!receivedText.equals("/cancel")) {
                        if (receivedText.charAt(0) == '/') {
                            message = "Невозможная команда";
                            sendTextMessage(chatId, message);
                            break;
                        }
                        message = "Аккаунт успешно создан!\n"+
                                "1)Пополнить баланс\n"+
                                "2)Перевести средства\n"+
                                "3)Показать счёт\n"+
                                "4)Выйти из аккаунта";
                        temp_password = receivedText;
                        tempAccount = new Account(temp_login,temp_password);
                        Data.accountsList.add(tempAccount);
                        Creator.createXlsx(Data.accountsList);
                        botState = "logged in";
                        sendTextMessage(chatId, message);
                    }else{
                        message = "Действие отменено";
                        temp_login = "";
                        temp_password = "";
                        botState = "not logged in";
                        sendTextMessage(chatId, message);
                    }
                    break;
                case "login name awaiting":
                    if (!receivedText.equals("/cancel")) {
                        if (receivedText.charAt(0) == '/') {
                            message = "Невозможная команда";
                            sendTextMessage(chatId, message);
                            break;
                        }
                        temp_login = receivedText;
                        message = "Введите пароль:";
                        botState = "password awaiting";
                        sendTextMessage(chatId, message);
                    }else {
                        message = "Действие отменено";
                        botState = "not logged in";
                        sendTextMessage(chatId, message);
                    }
                    break;
                case "password awaiting":
                    if (!receivedText.equals("/cancel")) {
                        if (receivedText.charAt(0) == '/') {
                            message = "Невозможная команда\n"+"Введите логин:";
                            botState = "login name awaiting";
                            sendTextMessage(chatId, message);
                            break;
                        }
                        temp_password = receivedText;
                        for (int i=0;i<Data.accountsList.size();i++) {
                            if (Data.accountsList.get(i).getUsername().equals(temp_login) && Data.accountsList.get(i).getPassword().equals(temp_password)) {
                                message = "Вы вошли в свой аккаунт!\n"+
                                "1)Пополнить баланс\n"+
                                "2)Потратить средства\n"+
                                "3)Показать счёт\n"+
                                "4)Выйти из аккаунта";
                                currentAccountIndex = i;
                                botState = "logged in";
                            }
                        }
                        if (botState.equals("logged in")){
                            sendTextMessage(chatId, message);
                            break;
                        }
                        message = "Неверные данные, повторите попытку\n"+"Введите логин:";
                        botState = "login name awaiting";
                        sendTextMessage(chatId, message);

                    }else{
                        message = "Действие отменено";
                        temp_login = "";
                        temp_password = "";
                        botState = "not logged in";
                        sendTextMessage(chatId, message);
                    }
                    break;
                case "logged in":
                    switch (receivedText){
                        case "1":
                            botState = "balance awaiting +";
                            message = "Введите насколько нужно увеличить баланс";
                            sendTextMessage(chatId, message);
                            break;
                        case "2":

                            botState = "sending choice";
                            message = "Выберите какому пользователю отправить средства:\n";
                            for (int i=0;i<Data.accountsList.size();i++){
                                message += i+1+")" + Data.accountsList.get(i).getUsername()+"\n";
                            }
                            sendTextMessage(chatId, message);
                            break;
                        case "3":
                            message = "Баланс счёта:"+Data.accountsList.get(currentAccountIndex).getBalance()+"\n"+
                                    "1)Пополнить баланс\n"+
                                    "2)Потратить средства\n"+
                                    "3)Показать счёт\n"+
                                    "4)Выйти из аккаунта";
                            sendTextMessage(chatId, message);
                            break;
                        case "4":
                            botState = "not logged in";
                            currentAccountIndex = -1;
                            temp_login = "";
                            temp_password = "";
                            message = "Вы успешно вышли!";
                            sendTextMessage(chatId,message);
                            break;
                    }
                    break;
                case "balance awaiting +":
                    try{
                        if(Integer.valueOf(receivedText) > 0){
                            Data.accountsList.get(currentAccountIndex).changeBalance(Integer.valueOf(receivedText));
                            Creator.createXlsx(Data.accountsList);
                            botState = "logged in";
                            message = "Пополнение успешно!\n"+
                                    "1)Пополнить баланс\n"+
                                    "2)Потратить средства\n"+
                                    "3)Показать счёт\n"+
                                    "4)Выйти из аккаунта";
                            sendTextMessage(chatId,message);
                        }
                    }catch(Exception e){
                        message = "Недопустимое значение";
                        sendTextMessage(chatId,message);
                    }
                    break;
                case "sending choice":

                    try{
                        currentIndex = Integer.valueOf(receivedText)-1;
                        tempAccount = Data.accountsList.get(currentAccountIndex);
                        if(currentIndex > 0 && currentIndex <= Data.accountsList.size()){
                            if (tempAccount.getUsername().equals(Data.accountsList.get(currentIndex).getUsername())) {
                                throw new Exception();
                            }else{
                                sendingChoiceIndex = currentIndex;
                                botState = "balance awaiting -";
                                message = "Введите сколько нужно перевести средств:";
                                sendTextMessage(chatId,message);
                                break;
                            }
                        }else{
                            throw new Exception();
                        }
                    }catch(Exception e){
                        message = "Недопустимое значение, попробуйте снова\n";
                        for (int i=0;i<Data.accountsList.size();i++) {
                            message += i + 1 + ")" + Data.accountsList.get(i).getUsername() + "\n";
                        }
                        sendTextMessage(chatId,message);
                    }
                    break;
                case "balance awaiting -":
                    try{
                        if(Integer.valueOf(receivedText) > 0 && Integer.valueOf(receivedText)<= Data.accountsList.get(currentAccountIndex).getBalance()){
                            new CurrencyTransfer(currentAccountIndex,sendingChoiceIndex,Integer.valueOf(receivedText)).start();
                            Creator.createXlsx(Data.accountsList);
                            botState = "logged in";
                            message = "Снятие средств успешно!\n"+
                                    "1)Пополнить баланс\n"+
                                    "2)Потратить средства\n"+
                                    "3)Показать счёт\n"+
                                    "4)Выйти из аккаунта";
                            sendTextMessage(chatId,message);
                        }else{
                            throw new Exception();
                        }
                    }catch(Exception e){
                        message = "Недопустимое значение";
                        sendTextMessage(chatId,message);
                    }
                    break;
            }
        }
    }

    private void sendTextMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "@mynewbot2424_bot";
    }

    @Override
    public String getBotToken() {
        return "7990761194:AAFvnrOWGBks_p4RrvWGAmSXQVH0ta511A8";
    }

}