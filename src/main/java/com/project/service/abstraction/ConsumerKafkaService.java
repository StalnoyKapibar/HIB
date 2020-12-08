package com.project.service.abstraction;

public interface ConsumerKafkaService {
    void listenGroup(String message);
}
