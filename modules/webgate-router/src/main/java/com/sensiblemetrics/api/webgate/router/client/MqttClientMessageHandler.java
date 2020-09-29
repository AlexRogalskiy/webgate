package com.sensiblemetrics.api.webgate.router.client;

import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public class MqttClientMessageHandler extends MqttPahoMessageHandler implements MqttMessageClient {

    /**
     * MQTT client message handler constructor by input parameters
     *
     * @param url      initial input {@link String} url
     * @param clientId initial input {@link String} client identifier
     */
    public MqttClientMessageHandler(final String url,
                                    final String clientId) {
        super(url, clientId);
    }

    /**
     * MQTT client message handler constructor by input parameters
     *
     * @param clientId      initial input {@link String} client identifier
     * @param clientFactory initial input {@link MqttPahoClientFactory}
     */
    public MqttClientMessageHandler(final String clientId,
                                    final MqttPahoClientFactory clientFactory) {
        super(clientId, clientFactory);
    }

    /**
     * Dispatches message to broker by input {@link String} topic and {@link String} payload
     *
     * @param topic   initial input {@link String} topic
     * @param payload initial input {@link String} payload
     */
    @Override
    public void sendMessage(final String topic, final String payload) {
        final Message<String> message = this.createMessage(topic, payload);
        this.sendMessage(message);
    }

    /**
     * Dispatches message to broker by input {@link String} topic and {@link String} payload
     *
     * @param message initial input {@link Message} to dispatch
     */
    @Override
    public void sendMessage(final Message<?> message) {
        this.handleMessage(message);
    }

    /**
     * Returns new {@link Message} by input {@link String} topic and {@link String} payload
     *
     * @param topic   initial input {@link String} topic
     * @param payload initial input {@link String} payload
     * @return new {@link Message}
     */
    private Message<String> createMessage(final String topic, final String payload) {
        return MessageBuilder.withPayload(payload).setHeader(MqttHeaders.TOPIC, topic).build();
    }
}
