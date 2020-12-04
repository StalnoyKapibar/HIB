package com.project;

import com.project.config.BotConfig;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;


public class ApplicationBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        BotConfig bot = new BotConfig();
        System.out.println(bot.getBotUsername());
        System.out.println(bot.getBotToken());

        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
