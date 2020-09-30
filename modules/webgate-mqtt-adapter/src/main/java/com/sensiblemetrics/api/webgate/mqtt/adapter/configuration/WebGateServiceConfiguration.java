package com.sensiblemetrics.api.webgate.mqtt.adapter.configuration;

import com.sensiblemetrics.api.webgate.router.client.MqttClientMessageHandler;
import com.sensiblemetrics.api.webgate.router.configuration.RouterConfigurationProvider;
import com.sensiblemetrics.api.webgate.router.property.WebGateRouterProperty;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.MessageChannel;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebGate Service configuration")
public class WebGateServiceConfiguration {

    @Bean
    @Description("Default model mapper configuration bean")
    public ModelMapper modelMapper(final List<Converter<?, ?>> converters,
                                   final List<PropertyMap<?, ?>> propertyMaps) {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMethodAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PUBLIC)
                .setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR)
                .setSourceNamingConvention(NamingConventions.JAVABEANS_ACCESSOR)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSourceNameTokenizer(NameTokenizers.CAMEL_CASE)
                .setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE)
                .setAmbiguityIgnored(true)
                .setSkipNullEnabled(true)
                .setFieldMatchingEnabled(true)
                .setFullTypeMatchingRequired(true)
                .setImplicitMappingEnabled(true);
        converters.forEach(modelMapper::addConverter);
        propertyMaps.forEach(modelMapper::addMappings);
        return modelMapper;
    }

    @Configuration
    @RequiredArgsConstructor
    @EnableConfigurationProperties(WebGateRouterProperty.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("SensibleMetrics WebGate Service configuration")
    public static class WebGateMqttProducerConfiguration {
        /**
         * Default global property key
         */
        public static final String MQTT_GLOBAL_ENDPOINT_KEY = "global";

        @Bean
        @ServiceActivator(inputChannel = "mqttPromiseOutboundChannel")
        @Description("MQTT global message client bean")
        public MqttClientMessageHandler mqttPromiseOutbound(final WebGateRouterProperty.RouterProducer mqttProducerRouterEndpoint,
                                                            final MqttPahoClientFactory mqttClientFactory,
                                                            final BiFunction<WebGateRouterProperty.RouterProducer, MqttPahoClientFactory, MqttClientMessageHandler> mqttMessageClientProvider) {
            return mqttMessageClientProvider.apply(mqttProducerRouterEndpoint, mqttClientFactory);
        }

        @Bean
        @Description("MQTT outbound global channel bean")
        public MessageChannel mqttOutboundChannel() {
            return new DirectChannel();
        }

        @Bean
        @Description("MQTT promise outbound global channel bean")
        public MessageChannel mqttPromiseOutboundChannel() {
            return new DirectChannel();
        }

        @Bean
        @Description("MQTT integration flow configuration bean")
        public IntegrationFlow mqttIntegrationFlow(final MessageProducerSupport messageProducerSupport,
                                                   final LoggingHandler logger) {
            return IntegrationFlows.from(messageProducerSupport)
                    .transform(p -> p + ", received from MQTT")
                    .handle("messageConsumerService", "handleHere")
                    .handle(logger)
                    .get();
        }

        @Bean
        @Description("MQTT logging handler configuration bean")
        public LoggingHandler logger() {
            final LoggingHandler loggingHandler = new LoggingHandler("INFO");
            loggingHandler.setLoggerName("MqttConsumer");
            return loggingHandler;
        }

        @Bean
        public MessageProducerSupport messageProducerSupport(final WebGateRouterProperty.RouterConsumer mqttConsumerRouterEndpoint,
                                                             final MqttPahoClientFactory globalMqttClientFactory,
                                                             final BiFunction<WebGateRouterProperty.RouterConsumer, MqttPahoClientFactory, MqttPahoMessageDrivenChannelAdapter> mqttConsumerProvider) {
            return mqttConsumerProvider.apply(mqttConsumerRouterEndpoint, globalMqttClientFactory);
        }

        @Bean
        @Description("MQTT router client factory configuration bean")
        public MqttPahoClientFactory mqttClientFactory(final WebGateRouterProperty.RouterProducer mqttProducerRouterEndpoint,
                                                       final Function<WebGateRouterProperty.RouterProducer, MqttPahoClientFactory> mqttClientFactoryProvider) {
            return mqttClientFactoryProvider.apply(mqttProducerRouterEndpoint);
        }

        @Bean
        @Description("MQTT router producer configuration bean")
        public WebGateRouterProperty.RouterProducer mqttProducerRouterEndpoint(final RouterConfigurationProvider<WebGateRouterProperty.RouterProducer> mqttProducerRouterConfigurationProvider) {
            return mqttProducerRouterConfigurationProvider.getOrThrow(MQTT_GLOBAL_ENDPOINT_KEY);
        }

        @Bean
        @Description("MQTT router consumer configuration bean")
        public WebGateRouterProperty.RouterConsumer mqttConsumerRouterEndpoint(final RouterConfigurationProvider<WebGateRouterProperty.RouterConsumer> mqttConsumerRouterConfigurationProvider) {
            return mqttConsumerRouterConfigurationProvider.getOrThrow(MQTT_GLOBAL_ENDPOINT_KEY);
        }
    }
}
