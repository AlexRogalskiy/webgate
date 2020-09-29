package com.sensiblemetrics.api.webgate.router.client;

import org.springframework.messaging.Message;

/**
 * Message client interface declaration
 */
public interface MqttMessageClient {
    /**
     * Dispatches message to broker by input {@link String} topic and {@link String} payload
     *
     * @param topic   initial input {@link String} topic
     * @param payload initial input {@link String} payload
     */
    void sendMessage(final String topic, final String payload);

    /**
     * Dispatches message to broker by input {@link String} topic and {@link String} payload
     *
     * @param message initial input {@link Message} to dispatch
     */
    void sendMessage(final Message<?> message);
}
