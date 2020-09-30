package com.sensiblemetrics.api.webgate.router.configuration;

import com.sensiblemetrics.api.webgate.router.property.WebGateRouterProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Invokes the available {@link MqttMessageProducerCustomizer} instances in the context for a
 * given {@link MqttPahoClientFactory}.
 */
@Component
@RequiredArgsConstructor
public class MqttClientFactoryCustomizers {

    private final List<? extends MqttClientFactoryCustomizer<?>> customizers;

    public MqttClientFactoryCustomizers(final ObjectProvider<List<? extends MqttClientFactoryCustomizer<?>>> customizersProvider) {
        this.customizers = customizersProvider.getIfAvailable();
    }

    /**
     * Customize the specified {@link MqttPahoClientFactory}. Locates all
     * {@link MqttClientFactoryCustomizer} beans able to handle the specified instance and
     * invoke {@link MqttClientFactoryCustomizer#customize(WebGateRouterProperty.RouterProducer, MqttPahoClientFactory)} on them.
     *
     * @param <T>             type of message handler
     * @param endpoint        initial input {@link WebGateRouterProperty.RouterProducer} to customize
     * @param messageProducer initial input {@link T} message producer to customize
     * @return {@link MqttPahoClientFactory} instance
     */
    @SuppressWarnings("unchecked")
    public <T extends MqttPahoClientFactory> T customize(final WebGateRouterProperty.RouterProducer endpoint,
                                                         final T messageProducer) {
        LambdaSafe.callbacks(MqttClientFactoryCustomizer.class, this.customizers, messageProducer)
                .withLogger(MqttClientFactoryCustomizers.class)
                .invoke(customizer -> customizer.customize(endpoint, messageProducer));
        return messageProducer;
    }
}
