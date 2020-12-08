package com.project.telegrambot;

import com.project.model.Order;
import com.project.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
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
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        if (update.getMessage().getText().equals("/start")){
            sendMessage.setText("приветственное сообщение, предлагает зарегистрироваться");
        }
        /*
        1) Проверка наличия chatId в БД
        2) Если есть, то показать статус всех заказов
        3) Если нет, то авторизоваться по номеру телефона, сохранить chatId в БД, показать все заказы
        4) Если по номеру телефона не найдено - предложить ввести логин/пароль с сайта
         */


        /*
        1), 2)
         */
        boolean isAuthorized = false;
        /*
        3)
         */
        if (update.getMessage().getText().equals("registration")) {
            sendMessage.setText("предлагает ввести номер телефона");
        }
        /**
         * Поиск в БД заказа, по введенному номеру телефона
         */
        List<Order> orderList= orderService.getOrderByUserPhoneInContacts(update.getMessage().getText());
        /*
        Нужно добавить сохранение chatId в БД
         */
        String responseMessage = "";
        if (isAuthorized) {
            if (orderList != null) {
                responseMessage += "Колличество заказов: " + orderList.size() + "\n";
                for (Order order : orderList) {
                    responseMessage += "id: " + order.getId() + "\n";
                    responseMessage += "data: " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US).format(order.getData()) + "\n";
                    responseMessage += "address: " + order.getAddress() + "\n";
                    responseMessage += "itemsCost: " + order.getItemsCost() + "\n";
                    responseMessage += "trackingNumber: " + order.getTrackingNumber() + "\n";
                    responseMessage += "status: " + order.getStatus() + "\n";
                    responseMessage += "contacts: " + order.getContacts() + "\n";
                    responseMessage += "comment: " + order.getComment() + "\n";
                }
                sendMessage.setText(responseMessage);
            } else {
                responseMessage+="не найдено заказов по указанному номеру: " + update.getMessage().getText()+"\n";
                responseMessage+="предлагает ввеси логин пароль с сайта в одном сообщении через пробел";
                sendMessage.setText(responseMessage);
            }
        }

        /**
         Отправка сообщения
         */
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }



//        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
//        List < List < InlineKeyboardButton >> rowsInline = new ArrayList < > ();
//        List < InlineKeyboardButton > rowInline = new ArrayList < > ();
//        rowInline.add(new InlineKeyboardButton().setText("Open Browser").setCallbackData("1"));
//        rowsInline.add(rowInline);
//        markupInline.setKeyboard(rowsInline);
//        sendMessage.setReplyMarkup(markupInline);

//        update.getUpdateId();
//        String id = update.getMessage().getChatId().toString();



//        if (update.getMessage().getText().equals("test")) {
//            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
//            List < List < InlineKeyboardButton >> rowsInline = new ArrayList < > ();
//            List < InlineKeyboardButton > rowInline = new ArrayList < > ();
//            rowInline.add(new InlineKeyboardButton().setText("Open Browser").setUrl("1"));
//            rowsInline.add(rowInline);
//            markupInline.setKeyboard(rowsInline);
//            sendMessage.setReplyMarkup(markupInline);
//        }

        /*
        Если пользователь не авторизован
         */
//        if (!isAuthorized) {
//            /*
//            Объект разметки клавиатуры
//             */
//            InlineKeyboardMarkup inlineKeyboardMarkup =new InlineKeyboardMarkup();
//            /*
//            Объект кнопки
//             */
//            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
//            inlineKeyboardButton.setText("Авторизация");
//            inlineKeyboardButton.setCallbackData("Введите свой номер телефона");
//            /*
//            Добавление кнопки в ряд
//             */
//            List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
//            keyboardButtonsRow1.add(inlineKeyboardButton);
//            /*
//            Добавление ряда в список рядов
//             */
//            List<List<InlineKeyboardButton>> rowList= new ArrayList<>();
//            rowList.add(keyboardButtonsRow1);
//            /*
//            добавление списка рядов в разметку клавиатуры
//             */
//            inlineKeyboardMarkup.setKeyboard(rowList);
//            /*
//            Добавление разметки клавиатуры к сообщению
//             */
//            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
//        }
        /*

         */

//        if (isAuthorized==false) {
//            sendMessage.setText(buttons)
//        }
//
//
//
//        if (update.getMessage().getText().equals("/autorize")) {
//            sendMessage.setText("auth");
//        } else {
//            sendMessage.setText(update.getMessage().getText());
//        }
//
//
//
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



//        update.getUpdateId();

    }

    public void sendMessageFromKafka(Long id) {
        /*
        Принимается заказ и по chatId отправляется сообщение об изменении статуса заказа
         */
        SendMessage sendMessage = new SendMessage();
        Order order = orderService.getOrderById(id);
        String chatId = order.getContacts().getChatId();
        String text ="Статус вашего заказа" +order.getId()+"\n";
        text+="Изменился на "+order.getStatus();
        sendMessage.setChatId(chatId).setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
