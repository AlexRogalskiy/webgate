package com.sensiblemetrics.api.webgate.router.client;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface DeviceGateway {
    void sendToMqtt(final String payload);
}
