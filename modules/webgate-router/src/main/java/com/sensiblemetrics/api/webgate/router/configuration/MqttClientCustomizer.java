package com.sensiblemetrics.api.webgate.router.configuration;

import com.sensiblemetrics.api.webgate.router.property.WebGateRouterProperty;
import org.eclipse.paho.client.mqttv3.IMqttClient;

/**
 * {@link IMqttClient} customizer declaration
 */
@FunctionalInterface
public interface MqttClientCustomizer<T extends IMqttClient> {
    /**
     * Customizes input {@link IMqttClient}
     * by input {@link WebGateRouterProperty.RouterProducer} property
     *
     * @param producerProperty - initial input {@link WebGateRouterProperty.RouterProducer} property
     * @param messageProducer  - initial input {@link T} client to customize
     */
    void customize(final WebGateRouterProperty.RouterProducer producerProperty,
                   final T messageProducer);
}
