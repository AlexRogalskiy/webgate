package com.sensiblemetrics.api.webgate.router.configuration;

import com.sensiblemetrics.api.webgate.router.property.WebGateRouterProperty;
import org.springframework.integration.mqtt.inbound.AbstractMqttMessageDrivenChannelAdapter;

/**
 * {@link AbstractMqttMessageDrivenChannelAdapter} customizer declaration
 */
@FunctionalInterface
public interface MqttMessageConsumerCustomizer<T extends AbstractMqttMessageDrivenChannelAdapter> {
    /**
     * Customizes input {@link AbstractMqttMessageDrivenChannelAdapter}
     * by provider {@link WebGateRouterProperty.RouterConsumer} property
     *
     * @param consumerProperty - initial input {@link WebGateRouterProperty.RouterConsumer} property
     * @param messageConsumer  - initial input {@link T} message consumer to customize
     */
    void customize(final WebGateRouterProperty.RouterConsumer consumerProperty,
                   final T messageConsumer);
}
