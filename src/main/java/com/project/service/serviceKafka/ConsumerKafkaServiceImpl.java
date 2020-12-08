package com.project.service.serviceKafka;

import com.project.service.abstraction.ConsumerKafkaService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class ConsumerKafkaServiceImpl implements ConsumerKafkaService {

    @KafkaListener(topics = "myTopic", groupId = "consumerServer")
    public void listenGroup(String message) {
        System.out.println(message);
    }
}