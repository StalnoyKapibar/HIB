package com.project.telegrambot;

import com.project.model.Order;
import com.project.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        boolean isAuthorized = false;

//        if (update.getMessage().getChatId()) {
//
//        }
//

        SendMessage sendMessage = new SendMessage();

//        /**
//         * Создание клавиатуры, для отображения в чате (кнопки)
//         */
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        replyKeyboardMarkup.setSelective(true);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(false);
//
//        /**
//         * Список строк для клавиатуры,
//         * Создание строк клавиатуры,
//         * Добавление в них кнопок
//         * Добавление строк в список
//         * Установить список строк клавиатуре
//         */
//        List<KeyboardRow> keyboard = new ArrayList<>();
//        KeyboardRow keyboardRow1 = new KeyboardRow();
//        KeyboardRow keyboardRow2 = new KeyboardRow();
//        keyboardRow1.add(new KeyboardButton("Кнопка 1 строка 1"));
//        keyboardRow1.add(new KeyboardButton("Кнопка 2 строка 1"));
//        keyboardRow2.add(new KeyboardButton("Кнопка 1 строка 2"));
//        keyboard.add(keyboardRow1);
//        keyboard.add(keyboardRow2);
//        replyKeyboardMarkup.setKeyboard(keyboard);


        /**
         * Поиск в БД заказа, по введенному номеру телефона
         */
        List<Order> orderList= orderService.getOrderByUserPhoneInContacts(update.getMessage().getText());

        update.getUpdateId();

        String id = update.getMessage().getChatId().toString();



        sendMessage.setChatId(id);

        String responseMessage = "";

//        if (orderList!=null){
//            responseMessage += "Колличество заказов: " + orderList.size()+"\n";
//            for (Order order : orderList) {
//                responseMessage+= "id: " + order.getId()+"\n";
//                responseMessage+= "data: " +new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US).format(order.getData())+ "\n";
//                responseMessage+= "address: " + order.getAddress()+"\n";
//                responseMessage+= "itemsCost: " + order.getItemsCost()+"\n";
//                responseMessage+= "trackingNumber: " + order.getTrackingNumber()+"\n";
//                responseMessage+= "status: " + order.getStatus()+"\n";
//                responseMessage+= "contacts: " + order.getContacts()+"\n";
//                responseMessage+= "comment: " + order.getComment()+"\n";
//            }
//            sendMessage.setText(responseMessage);
//        }else {
//            sendMessage.setText("не найдено заказов по указанному номеру: " +update.getMessage().getText());
//        }

        sendMessage.setText(update.toString());

        /**
         Отправка сообщения
         */
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
