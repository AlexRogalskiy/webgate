package com.sensiblemetrics.api.webgate.router.configuration;

import com.sensiblemetrics.api.webgate.router.property.WebGateRouterProperty;
import org.springframework.integration.mqtt.outbound.AbstractMqttMessageHandler;

/**
 * {@link AbstractMqttMessageHandler} customizer declaration
 */
@FunctionalInterface
public interface MqttMessageProducerCustomizer<T extends AbstractMqttMessageHandler> {
    /**
     * Customizes input {@link AbstractMqttMessageHandler}
     * by input {@link WebGateRouterProperty.RouterProducer} property
     *
     * @param producerProperty - initial input {@link WebGateRouterProperty.RouterProducer} property
     * @param messageProducer  - initial input {@link T} message handler to customize
     */
    void customize(final WebGateRouterProperty.RouterProducer producerProperty,
                   final T messageProducer);
}
