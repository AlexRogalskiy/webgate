package com.sensiblemetrics.api.webgate.router.configuration;

import com.sensiblemetrics.api.webgate.router.property.WebGateRouterProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.integration.mqtt.inbound.AbstractMqttMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.AbstractMqttMessageHandler;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Invokes the available {@link MqttMessageProducerCustomizer} instances in the context for a
 * given {@link AbstractMqttMessageHandler}.
 */
@Component
@RequiredArgsConstructor
public class MqttConsumerCustomizers {

    private final List<? extends MqttMessageConsumerCustomizer<?>> customizers;

    public MqttConsumerCustomizers(final ObjectProvider<List<? extends MqttMessageConsumerCustomizer<?>>> customizersProvider) {
        this.customizers = customizersProvider.getIfAvailable();
    }

    /**
     * Customize the specified {@link AbstractMqttMessageDrivenChannelAdapter}. Locates all
     * {@link MqttMessageConsumerCustomizer} beans able to handle the specified instance and
     * invoke {@link MqttMessageProducerCustomizer#customize(WebGateRouterProperty.RouterConsumer, AbstractMqttMessageDrivenChannelAdapter)} on them.
     *
     * @param <T>             type of message handler
     * @param endpoint        initial input {@link WebGateRouterProperty.RouterConsumer} to customize
     * @param messageProducer initial input {@link T} message producer to customize
     * @return {@link AbstractMqttMessageDrivenChannelAdapter} instance
     */
    @SuppressWarnings("unchecked")
    public <T extends AbstractMqttMessageDrivenChannelAdapter> T customize(final WebGateRouterProperty.RouterConsumer endpoint,
                                                                           final T messageProducer) {
        LambdaSafe.callbacks(MqttMessageConsumerCustomizer.class, this.customizers, messageProducer)
                .withLogger(MqttConsumerCustomizers.class)
                .invoke(customizer -> customizer.customize(endpoint, messageProducer));
        return messageProducer;
    }
}
