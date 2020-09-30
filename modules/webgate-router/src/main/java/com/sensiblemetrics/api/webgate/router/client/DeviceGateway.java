package com.sensiblemetrics.api.webgate.router.client;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface DeviceGateway {
    /**
     * Sends {@link String} payload to mqtt broker
     *
     * @param payload initial input {@link String} to operate by
     */
    void sendToMqtt(final String payload);
}
