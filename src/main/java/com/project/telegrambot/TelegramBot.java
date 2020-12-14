package com.project.telegrambot;

import com.project.model.ContactsOfOrder;
import com.project.model.Order;
import com.project.service.OrderServiceImpl;
import com.project.service.abstraction.ContactsOfOrderService;
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
    private ContactsOfOrderService contactsOfOrderService;

    @Autowired
    public void setContactsOfOrderService(ContactsOfOrderService contactsOfOrderService) {
        this.contactsOfOrderService = contactsOfOrderService;
    }

    @Autowired
    public void setOrderService(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    /**
     * Метод возвращает имя бота, указанное при регистрации.
     *
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        return userName;
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     *
     * @return token для бота
     */
    @Override
    public String getBotToken() {
        return token;
    }

    /**
     * Метод для приема сообщений.
     *
     * @param update Содержит сообщение от пользователя.
     */
    @Override
    public void onUpdateReceived(Update update) {
        /**
         * Проверка авторизованности
         */
        boolean isAuthorized = checkChatId(update.getMessage().getChatId().toString());
        List<Order> orderList = new ArrayList<>();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());





        /**
         * Поиск в БД заказа, по введенному номеру телефона
         * Если найдены - сохранение chatId в БД к строкам с таким номером телефона
         */
        if (isAuthorized = false) {
            orderList = getAllOrdersByPhone(update.getMessage().getText());
        } else {
            orderList = orderService.getOrderByChatIdInContacts(update.getMessage().getChatId().toString());
        }

        /**
         * Если один из способов вернул лист,
         * то флаг авторизации меняется
         */
        if (orderList!=null) {
            isAuthorized = true;
        }


        /**
         * Если пользователь авторизован - показать все заказы
         */
        if (isAuthorized) {
            sendMessage = createSendMessage(orderList);
            sendMessage.setChatId(update.getMessage().getChatId());
        }

        /*
           Для тестов
         */
        if (update.getMessage().getText().equals("/test")) {
            sendMessage.setText("ваш чат ID в базе " + isAuthorized);
        } else if (update.getMessage().getText().equals("/start")) {
            sendMessage.setText("приветственное сообщение, предлагает зарегистрироваться");
        } else if (update.getMessage().getText().equals("/getMyInfo")) {
            sendMessage.setText(update.toString());
        } else if (update.getMessage().getText().equals("/registration")) {
            sendMessage.setText("предлагает ввести номер телефона");
        } else {
            sendMessage.setText("ни одна из команд - не введена");
        }


        /**
         * Отправка сообщения
         */
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Принимается заказ (его id) и по chatId отправляется сообщение об изменении статуса заказа
     */
    public void sendMessageFromKafka(Long id) {

        SendMessage sendMessage = new SendMessage();
        Order order = orderService.getOrderById(id);
        String chatId = order.getContacts().getChatId();
        if (chatId != null) {
            String text = "Статус вашего заказа " + order.getId() + "\n";
            text += "Изменился на " + order.getStatus();
            sendMessage.setChatId(chatId).setText(text);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Метод принимает ChatId и ищет в БД заказы с ним
     *
     * @param id
     * @return
     */
    private boolean checkChatId(String id) {
        return orderService.checkChatIdInContacts(id);
    }

    /**
     * метод принимает номер телефона введенный  пользователем и возвращает список заказов,
     * если список найден - сохраняется chatId в БД
     *
     * @param phone
     * @return
     */
    private List<Order> getAllOrdersByPhone(String phone) {
        List<Order> orderList = orderService.getOrderByUserPhoneInContacts(phone);
        if (orderList != null) {
            List<ContactsOfOrder> contactsOfOrderList = contactsOfOrderService.findByPhone(phone);
            for (ContactsOfOrder contactsOfOrder : contactsOfOrderList) {
                contactsOfOrder.setChatId(phone);
                contactsOfOrderService.update(contactsOfOrder);
            }
        }
        return orderList;
    }

    /**
     * Метод принимает лист заказов и возвращает отформатированное сообщение
     *
     * @param list
     * @return
     */
    private SendMessage createSendMessage(List<Order> list) {
        SendMessage sendMessage = new SendMessage();
        String responseMessage = "";
        responseMessage += "Колличество заказов: " + list.size() + "\n";
        for (Order order : list) {
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
        return sendMessage;
    }
}
