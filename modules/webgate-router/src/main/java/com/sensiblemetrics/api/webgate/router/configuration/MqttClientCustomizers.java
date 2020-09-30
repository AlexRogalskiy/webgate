package com.sensiblemetrics.api.webgate.router.configuration;

import com.sensiblemetrics.api.webgate.router.property.WebGateRouterProperty;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Invokes the available {@link MqttClientCustomizer} instances in the context for a
 * given {@link IMqttClient}.
 */
@Component
@RequiredArgsConstructor
public class MqttClientCustomizers {

    private final List<? extends MqttClientCustomizer<?>> customizers;

    public MqttClientCustomizers(final ObjectProvider<List<? extends MqttClientCustomizer<?>>> customizersProvider) {
        this.customizers = customizersProvider.getIfAvailable();
    }

    /**
     * Customize the specified {@link IMqttClient}. Locates all
     * {@link MqttClientCustomizer} beans able to handle the specified instance and
     * invoke {@link MqttClientCustomizer#customize(WebGateRouterProperty.RouterProducer, IMqttClient)} on them.
     *
     * @param <T>             type of message handler
     * @param endpoint        initial input {@link WebGateRouterProperty.RouterProducer} to customize
     * @param messageProducer initial input {@link T} message producer to customize
     * @return {@link IMqttClient} instance
     */
    @SuppressWarnings("unchecked")
    public <T extends IMqttClient> T customize(final WebGateRouterProperty.RouterProducer endpoint,
                                               final T messageProducer) {
        LambdaSafe.callbacks(MqttClientCustomizer.class, this.customizers, messageProducer)
                .withLogger(MqttClientCustomizers.class)
                .invoke(customizer -> customizer.customize(endpoint, messageProducer));
        return messageProducer;
    }
}
