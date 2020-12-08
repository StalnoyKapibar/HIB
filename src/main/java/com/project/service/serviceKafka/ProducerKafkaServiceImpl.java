package com.project.service.serviceKafka;

import com.project.service.abstraction.ProducerKafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerKafkaServiceImpl implements ProducerKafkaService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendMessage(String msg) {
        kafkaTemplate.send("myTopic", msg);
    }
}