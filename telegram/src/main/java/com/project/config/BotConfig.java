package com.project.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


//@PropertySource({"application-bot"})
public class BotConfig extends TelegramLongPollingBot {

//    @Value("${botUserName}")
//    private String userName;
    private String userName = "History_in_books_bot";

//    @Value("${botToken}")
//    private String token;
    private String token = "1443066393:AAFOXPtc7XAHcnyOgYX14gNmvW7wIIlNEWI";

//    private OrderServiceImpl

    /**
     * Метод возвращает имя бота, указанное при регистрации.
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        return userName;
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     */
    @Override
    public String getBotToken() {
        return token;
    }

    /**
     * Метод для приема сообщений.
     * @param update Содержит сообщение от пользователя.
     */
    @Override
    public void onUpdateReceived(Update update) {


        update.getUpdateId();


        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());

        if (update.getMessage()!=null){
            sendMessage.setText("Привет");
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }
}
