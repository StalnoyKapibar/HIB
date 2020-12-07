package com.project.util;

import com.project.model.Order;
import com.project.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@Component
public class TelegramBot extends TelegramLongPollingBot {


    @Value("${botUserName}")
    private String userName;

    @Value("${botToken}")
    private String token;

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
        List<Order> orderList= orderService.getOrderByUserPhoneInContacts(update.getMessage().getText());

        update.getUpdateId();

        String id = update.getMessage().getChatId().toString();

        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(id);

        String responseMessage = "";

        if (orderList!=null){
            responseMessage += "Колличество заказов: " + orderList.size()+"\n";
            for (Order order : orderList) {
                responseMessage+= "id: " + order.getId()+"\n";
                responseMessage+= "data: " +new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US).format(order.getData())+ "\n";
                responseMessage+= "address: " + order.getAddress()+"\n";
                responseMessage+= "itemsCost: " + order.getItemsCost()+"\n";
                responseMessage+= "trackingNumber: " + order.getTrackingNumber()+"\n";
                responseMessage+= "status: " + order.getStatus()+"\n";
                responseMessage+= "contacts: " + order.getContacts()+"\n";
                responseMessage+= "comment: " + order.getComment()+"\n";
            }
            sendMessage.setText(responseMessage);
        }else {
            sendMessage.setText("не найдено заказов по указанному номеру: " +update.getMessage().getText());
        }

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
