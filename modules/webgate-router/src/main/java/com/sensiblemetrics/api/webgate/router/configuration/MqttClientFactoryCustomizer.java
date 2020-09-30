package com.sensiblemetrics.api.webgate.router.configuration;

import com.sensiblemetrics.api.webgate.router.property.WebGateRouterProperty;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

/**
 * {@link MqttPahoClientFactory} customizer declaration
 */
@FunctionalInterface
public interface MqttClientFactoryCustomizer<T extends MqttPahoClientFactory> {
    /**
     * Customizes input {@link MqttPahoClientFactory}
     * by input {@link WebGateRouterProperty.RouterProducer} property
     *
     * @param producerProperty - initial input {@link WebGateRouterProperty.RouterProducer} property
     * @param messageProducer  - initial input {@link T} client factory to customize
     */
    void customize(final WebGateRouterProperty.RouterProducer producerProperty,
                   final T messageProducer);
}
