package com.sensiblemetrics.api.webgate.router.configuration;

import com.sensiblemetrics.api.webgate.router.property.WebGateRouterProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.integration.mqtt.outbound.AbstractMqttMessageHandler;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Invokes the available {@link MqttMessageProducerCustomizer} instances in the context for a
 * given {@link AbstractMqttMessageHandler}.
 */
@Component
@RequiredArgsConstructor
public class MqttProducerCustomizers {

    private final List<? extends MqttMessageProducerCustomizer<?>> customizers;

    public MqttProducerCustomizers(final ObjectProvider<List<? extends MqttMessageProducerCustomizer<?>>> customizersProvider) {
        this.customizers = customizersProvider.getIfAvailable();
    }

    /**
     * Customize the specified {@link AbstractMqttMessageHandler}. Locates all
     * {@link MqttMessageProducerCustomizer} beans able to handle the specified instance and
     * invoke {@link MqttMessageProducerCustomizer#customize(WebGateRouterProperty.RouterProducer, AbstractMqttMessageHandler)} on them.
     *
     * @param <T>            type of message handler
     * @param endpoint       initial input {@link WebGateRouterProperty.RouterProducer} to customize
     * @param messageHandler initial input {@link T} message handler to customize
     * @return {@link AbstractMqttMessageHandler} instance
     */
    @SuppressWarnings("unchecked")
    public <T extends AbstractMqttMessageHandler> T customize(final WebGateRouterProperty.RouterProducer endpoint,
                                                              final T messageHandler) {
        LambdaSafe.callbacks(MqttMessageProducerCustomizer.class, this.customizers, messageHandler)
                .withLogger(MqttProducerCustomizers.class)
                .invoke(customizer -> customizer.customize(endpoint, messageHandler));
        return messageHandler;
    }
}
