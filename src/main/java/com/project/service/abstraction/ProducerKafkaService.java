package com.project.service.abstraction;

public interface ProducerKafkaService {
    void sendMessage(String msg);
}
