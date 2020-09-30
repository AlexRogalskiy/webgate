package com.sensiblemetrics.api.webgate.router.configuration;

import com.sensiblemetrics.api.webgate.commons.exception.RouterConfigurationException;
import com.sensiblemetrics.api.webgate.router.client.MqttClientMessageHandler;
import com.sensiblemetrics.api.webgate.router.property.WebGateRouterProperty;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttMessageConverter;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.lang.Math.toIntExact;

@Configuration
@ConditionalOnProperty(prefix = WebGateRouterProperty.PROPERTY_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(WebGateRouterProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebGate Router configuration")
public abstract class WebGateRouterConfiguration {
    /**
     * Default bean naming conventions
     */
    public static final String MQTT_CONNECT_OPTIONS_PROVIDER_BEAN_NAME = "mqttConnectOptionsProvider";

    public static final String MQTT_CLIENT_FACTORY_PROVIDER_BEAN_NAME = "mqttClientFactoryProvider";
    public static final String MQTT_CLIENT_FACTORY_CUSTOMIZER_BEAN_NAME = "mqttClientFactoryCustomizer";

    public static final String MQTT_BLOCKING_CLIENT_PROVIDER_BEAN_NAME = "mqttBlockingClientProvider";
    public static final String MQTT_BLOCKING_CLIENT_CUSTOMIZER_BEAN_NAME = "mqttBlockingClientCustomizer";

    public static final String MQTT_PRODUCER_PROVIDER_BEAN_NAME = "mqttProducerProvider";
    public static final String MQTT_PRODUCER_CUSTOMIZER_BEAN_NAME = "mqttProducerCustomizer";

    public static final String MQTT_CONSUMER_PROVIDER_BEAN_NAME = "mqttConsumerProvider";
    public static final String MQTT_CONSUMER_CUSTOMIZER_BEAN_NAME = "mqttConsumerCustomizer";

    public static final String MQTT_PRODUCER_ROUTER_PROVIDER_BEAN_NAME = "mqttProducerRouterConfigurationProvider";
    public static final String MQTT_CONSUMER_ROUTER_PROVIDER_BEAN_NAME = "mqttConsumerRouterConfigurationProvider";
    public static final String MQTT_MESSAGE_CONVERTER_BEAN_NAME = "mqttMessageConverter";

    @Bean(MQTT_CONNECT_OPTIONS_PROVIDER_BEAN_NAME)
    @ConditionalOnMissingBean(name = MQTT_CONNECT_OPTIONS_PROVIDER_BEAN_NAME)
    @Description("MQTT connect options provider bean")
    public Function<WebGateRouterProperty.RouterProducer, MqttConnectOptions> mqttConnectOptionsProvider() {
        return endpoint -> {
            final MqttConnectOptions connectOptions = new MqttConnectOptions();
            Optional.ofNullable(endpoint.getUsername())
                    .ifPresent(connectOptions::setUserName);
            Optional.ofNullable(endpoint.getPassword())
                    .map(String::toCharArray)
                    .ifPresent(connectOptions::setPassword);
            connectOptions.setCleanSession(endpoint.isCleanSession());
            connectOptions.setKeepAliveInterval(toIntExact(endpoint.getKeepAliveInterval().getSeconds()));
            connectOptions.setServerURIs(endpoint.getServerUrisAsArray());
            return connectOptions;
        };
    }

    @Bean(MQTT_BLOCKING_CLIENT_PROVIDER_BEAN_NAME)
    @ConditionalOnMissingBean(name = MQTT_BLOCKING_CLIENT_PROVIDER_BEAN_NAME)
    @Description("MQTT connect options provider bean")
    public Function<WebGateRouterProperty.RouterProducer, MqttClient> mqttBlockingClientProvider(final MqttClientCustomizers customizers) {
        return producerProperty -> {
            try {
                final MqttClient mqttClient = new MqttClient(producerProperty.getServerUri(), producerProperty.getClientId());
                return customizers.customize(producerProperty, mqttClient);
            } catch (MqttException e) {
                throw new RouterConfigurationException(e);
            }
        };
    }

    @Bean(MQTT_BLOCKING_CLIENT_CUSTOMIZER_BEAN_NAME)
    @ConditionalOnMissingBean(name = MQTT_BLOCKING_CLIENT_CUSTOMIZER_BEAN_NAME)
    @Description("MQTT client customizer bean")
    public <T extends MqttClient> MqttClientCustomizer<T> mqttBlockingClientCustomizer(final Function<WebGateRouterProperty.RouterProducer, MqttConnectOptions> mqttConnectOptionsProvider) {
        return (producerProperty, messageProducer) -> {
            try {
                messageProducer.connect(mqttConnectOptionsProvider.apply(producerProperty));
            } catch (MqttException e) {
                throw new RouterConfigurationException(e);
            }
        };
    }

    @Bean(MQTT_CLIENT_FACTORY_PROVIDER_BEAN_NAME)
    @ConditionalOnMissingBean(name = MQTT_CLIENT_FACTORY_PROVIDER_BEAN_NAME)
    @Description("MQTT client factory provider bean")
    public Function<WebGateRouterProperty.RouterProducer, MqttPahoClientFactory> mqttClientFactoryProvider(final MqttClientFactoryCustomizers customizers) {
        return producerProperty -> {
            final DefaultMqttPahoClientFactory clientFactory = new DefaultMqttPahoClientFactory();
            return customizers.customize(producerProperty, clientFactory);
        };
    }

    @Bean(MQTT_CLIENT_FACTORY_CUSTOMIZER_BEAN_NAME)
    @ConditionalOnMissingBean(name = MQTT_CLIENT_FACTORY_CUSTOMIZER_BEAN_NAME)
    @Description("MQTT client factory customizer bean")
    public <T extends DefaultMqttPahoClientFactory> MqttClientFactoryCustomizer<T> mqttClientFactoryCustomizer(final Function<WebGateRouterProperty.RouterProducer, MqttConnectOptions> mqttConnectOptionsProvider) {
        return (producerProperty, messageProducer) ->
                messageProducer.setConnectionOptions(mqttConnectOptionsProvider.apply(producerProperty));
    }

    @Bean(MQTT_PRODUCER_PROVIDER_BEAN_NAME)
    @ConditionalOnMissingBean(name = MQTT_PRODUCER_PROVIDER_BEAN_NAME)
    @Description("MQTT message producer provider bean")
    public BiFunction<WebGateRouterProperty.RouterProducer, MqttPahoClientFactory, MqttClientMessageHandler> mqttProducerProvider(final MqttProducerCustomizers customizers) {
        return (producerProperty, clientFactory) -> {
            final MqttClientMessageHandler messageProducer = new MqttClientMessageHandler(producerProperty.getClientId(), clientFactory);
            return customizers.customize(producerProperty, messageProducer);
        };
    }

    @Bean(MQTT_PRODUCER_CUSTOMIZER_BEAN_NAME)
    @ConditionalOnMissingBean(name = MQTT_PRODUCER_CUSTOMIZER_BEAN_NAME)
    @Description("MQTT message producer customizer bean")
    public <T extends MqttPahoMessageHandler> MqttMessageProducerCustomizer<T> mqttProducerCustomizer(final ObjectProvider<MqttMessageConverter> mqttMessageConverter) {
        return (producerProperty, messageProducer) -> {
            mqttMessageConverter.ifAvailable(messageProducer::setConverter);
            messageProducer.setAsync(producerProperty.isAsync());
            messageProducer.setLoggingEnabled(producerProperty.isLoggingEnabled());
            messageProducer.setDefaultQos(producerProperty.getQualityOfService());
            messageProducer.setCompletionTimeout(producerProperty.getCompletionTimeout().toMillis());
            messageProducer.setDisconnectCompletionTimeout(producerProperty.getKeepAliveInterval().toMillis());
        };
    }

    @Bean(MQTT_CONSUMER_PROVIDER_BEAN_NAME)
    @ConditionalOnMissingBean(name = MQTT_CONSUMER_PROVIDER_BEAN_NAME)
    @Description("MQTT message consumer provider bean")
    public BiFunction<WebGateRouterProperty.RouterConsumer, MqttPahoClientFactory, MqttPahoMessageDrivenChannelAdapter> mqttConsumerProvider(final MqttConsumerCustomizers customizers) {
        return (consumerProperty, clientFactory) -> {
            final MqttPahoMessageDrivenChannelAdapter messageConsumer = new MqttPahoMessageDrivenChannelAdapter(consumerProperty.getClientId(), clientFactory, consumerProperty.getTopicsAsArray());
            return customizers.customize(consumerProperty, messageConsumer);
        };
    }

    @Bean(MQTT_CONSUMER_CUSTOMIZER_BEAN_NAME)
    @ConditionalOnMissingBean(name = MQTT_CONSUMER_CUSTOMIZER_BEAN_NAME)
    @Description("MQTT message consumer customizer bean")
    public <T extends MqttPahoMessageDrivenChannelAdapter> MqttMessageConsumerCustomizer<T> mqttConsumerCustomizer(final ObjectProvider<MqttMessageConverter> mqttMessageConverter) {
        return (consumerProperty, messageConsumer) -> {
            mqttMessageConverter.ifAvailable(messageConsumer::setConverter);
            messageConsumer.setQos(consumerProperty.getQualityOfService());
            messageConsumer.setCompletionTimeout(consumerProperty.getCompletionTimeout().toMillis());
            messageConsumer.setDisconnectCompletionTimeout(consumerProperty.getKeepAliveInterval().toMillis());
        };
    }

    @Bean(MQTT_PRODUCER_ROUTER_PROVIDER_BEAN_NAME)
    @ConditionalOnMissingBean(name = MQTT_PRODUCER_ROUTER_PROVIDER_BEAN_NAME)
    @Description("MQTT producer router configuration provider bean")
    public RouterConfigurationProvider<WebGateRouterProperty.RouterProducer> mqttProducerRouterConfigurationProvider(final WebGateRouterProperty property) {
        return routerName -> property.getProducers().get(routerName);
    }

    @Bean(MQTT_CONSUMER_ROUTER_PROVIDER_BEAN_NAME)
    @ConditionalOnMissingBean(name = MQTT_CONSUMER_ROUTER_PROVIDER_BEAN_NAME)
    @Description("MQTT consumer router configuration provider bean")
    public RouterConfigurationProvider<WebGateRouterProperty.RouterConsumer> mqttConsumerRouterConfigurationProvider(final WebGateRouterProperty property) {
        return routerName -> property.getConsumers().get(routerName);
    }

    @Bean(MQTT_MESSAGE_CONVERTER_BEAN_NAME)
    @ConditionalOnMissingBean(name = MQTT_MESSAGE_CONVERTER_BEAN_NAME)
    @Description("MQTT message converter bean")
    public MqttMessageConverter mqttMessageConverter() {
        return new DefaultPahoMessageConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(DefaultConversionService.class)
    @Description("Conversion service bean")
    public ConversionService conversionService(final List<Converter<?, ?>> converters) {
        final DefaultConversionService defaultConversionService = new DefaultConversionService();
        converters.forEach(defaultConversionService::addConverter);
        return defaultConversionService;
    }
}
