package com.sensiblemetrics.api.webgate.mqtt.adapter.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageConsumerService {

    @ServiceActivator
    public Object handleHere(@Payload Object mess) {
        log.info("Payload {}", mess);
        return mess;
    }
}
