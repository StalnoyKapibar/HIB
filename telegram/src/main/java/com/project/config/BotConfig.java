package com.project.config;


import com.project.model.Order;
import com.project.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class BotConfig extends TelegramLongPollingBot {

    private String userName = "History_in_books_bot";
    private String token = "1443066393:AAFOXPtc7XAHcnyOgYX14gNmvW7wIIlNEWI";
    private OrderServiceImpl orderService;

    @Autowired
    public void setOrderService(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

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
//        List<Order>orderList= orderService.getOrderByUserPhoneInContacts(update.getMessage().toString());


        update.getUpdateId();


        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        if (orderService==null){
            sendMessage.setText("null");
        }

//        if (orderList!=null){
//            sendMessage.setText(orderList.size()+"");
//            try {
//                execute(sendMessage);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }else {
//            sendMessage.setText("не найдено заказов по указанному номеру");
//            try {
//                execute(sendMessage);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }

        /**
        Отправка сообщения
         */
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
//        if (update.getMessage()!=null){
//            sendMessage.setText("Привет");
//            try {
//                execute(sendMessage);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }

    }
}
