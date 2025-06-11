package org.example;


import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        try {
            Data.accountsList = Reader.readXlsx();
        } catch (Exception e) {
            System.err.println("Ошибка при чтении");
        }
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot());
            System.out.println("Бот запущен!");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}