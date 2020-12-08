package com.project.service.serviceKafka;

import com.project.service.abstraction.ConsumerKafkaService;
import com.project.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerKafkaServiceImpl implements ConsumerKafkaService {

    @Autowired
    private TelegramBot telegramBot;

    @KafkaListener(topics = "myTopic", groupId = "consumerServer")
    public void listenGroup(String message) {
        telegramBot.sendMessageFromKafka(Long.parseLong(message));
        System.out.println(message);
    }
}