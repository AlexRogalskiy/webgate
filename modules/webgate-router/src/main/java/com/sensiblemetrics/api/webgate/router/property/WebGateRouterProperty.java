package com.sensiblemetrics.api.webgate.router.property;

import com.sensiblemetrics.api.webgate.validation.constraint.annotation.NullOrNotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.time.DurationMin;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationFormat;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static com.sensiblemetrics.api.webgate.commons.property.PropertySettings.DEFAULT_PROPERTY_DELIMITER;
import static com.sensiblemetrics.api.webgate.commons.property.PropertySettings.DEFAULT_PROPERTY_PREFIX;

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(prefix = WebGateRouterProperty.PROPERTY_PREFIX, ignoreInvalidFields = true)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebGate Router configuration properties")
public class WebGateRouterProperty {
    /**
     * Default router property prefix
     */
    public static final String PROPERTY_PREFIX = DEFAULT_PROPERTY_PREFIX + DEFAULT_PROPERTY_DELIMITER + "router";

    /**
     * Router producers
     */
    @Valid
    @NotEmpty(message = "{property.router.producers.notEmpty}")
    private Map<@NotBlank String, @NotNull RouterProducer> producers;

    /**
     * Router consumers
     */
    @Valid
    @NotEmpty(message = "{property.router.consumers.notEmpty}")
    private Map<@NotBlank String, @NotNull RouterConsumer> consumers;

    /**
     * WebGate router info
     */
    @Data
    @Validated
    @Accessors(chain = true)
    public static class Router {
        /**
         * Router client identifier
         */
        @NullOrNotBlank(message = "{property.router.client-id.nullOrNotBlank}")
        private String clientId = "mqttPubClient";

        /**
         * Router completion timeout (in millis)
         */
        @DurationUnit(ChronoUnit.MILLIS)
        @DurationFormat(DurationStyle.SIMPLE)
        @DurationMin(message = "{property.router.completion-timeout.min}")
        @NotNull(message = "{property.router.completion-timeout.notNull}")
        private Duration completionTimeout = Duration.ofMillis(20_000);

        /**
         * Router keep alive timeout (in millis)
         */
        @DurationUnit(ChronoUnit.MILLIS)
        @DurationFormat(DurationStyle.SIMPLE)
        @DurationMin(message = "{property.router.keep-alive-interval.min}")
        @NotNull(message = "{property.router.keep-alive-interval.notNull}")
        private Duration keepAliveInterval = Duration.ofMillis(30_000);

        /**
         * Router quality of service
         */
        @NumberFormat(style = NumberFormat.Style.NUMBER)
        @PositiveOrZero(message = "{property.router.quality-of-service.positiveOrZero}")
        private int qualityOfService = 1;

        /**
         * Enable/disable router ({@code true} by default)
         */
        private boolean enabled = true;
    }

    /**
     * WebGate router producer info
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @Validated
    @Accessors(chain = true)
    public static class RouterProducer extends Router {
        /**
         * Producer endpoint authentication login
         */
        @NullOrNotBlank(message = "{property.router.producers.username.nullOrNotBlank}")
        private String username;

        /**
         * Producer endpoint authentication password
         */
        @NullOrNotBlank(message = "{property.router.producers.password.nullOrNotBlank}")
        private String password;

        /**
         * Enable/disable logging
         */
        private boolean loggingEnabled = false;

        /**
         * Enable/disable producer clean session
         */
        private boolean cleanSession = false;

        /**
         * Producer endpoint URI mappings
         */
        @Valid
        @NotEmpty(message = "{property.router.producers.server-uris.notEmpty}")
        private List<@NotBlank String> serverURIs;

        /**
         * Enable/disable producer async mode
         */
        private boolean async = true;

        /**
         * Producer default topic
         */
        @NullOrNotBlank(message = "{property.router.producers.default-topic.nullOrNotBlank}")
        private String defaultTopic;

        /**
         * Returns {@link String} server URI
         *
         * @return {@link String} server URI
         */
        public String getServerUri() {
            return this.serverURIs.get(0);
        }

        /**
         * Returns {@link String} array of server URIs
         *
         * @return {@link String} array of server URIs
         */
        public String[] getServerUrisAsArray() {
            return this.serverURIs.toArray(new String[0]);
        }
    }

    /**
     * WebGate router consumer info
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @Validated
    @Accessors(chain = true)
    public static class RouterConsumer extends Router {
        /**
         * Consumer topic mappings
         */
        @Valid
        @NotEmpty(message = "{property.router.consumers.topics.notEmpty}")
        private List<@NotBlank String> topics;

        /**
         * Returns {@link String} array of topics
         *
         * @return {@link String} array of topics
         */
        public String[] getTopicsAsArray() {
            return this.topics.toArray(new String[0]);
        }
    }

    /**
     * Enable/disable router configuration ({@code true} by default)
     */
    private boolean enabled = true;
}
