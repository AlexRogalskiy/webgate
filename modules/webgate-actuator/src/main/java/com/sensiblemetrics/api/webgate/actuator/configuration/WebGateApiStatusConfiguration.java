package com.sensiblemetrics.api.webgate.actuator.configuration;

import com.sensiblemetrics.api.webgate.actuator.indicator.GracefulShutdownHealthIndicator;
import com.sensiblemetrics.api.webgate.actuator.indicator.StatefulHealthIndicator;
import com.sensiblemetrics.api.webgate.actuator.indicator.StatusInfoContributor;
import com.sensiblemetrics.api.webgate.actuator.indicator.TaskSchedulerHealthIndicator;
import com.sensiblemetrics.api.webgate.actuator.property.WebGateApiStatusInfoProperty;
import com.sensiblemetrics.api.webgate.actuator.property.WebGateApiStatusProperty;
import com.sensiblemetrics.api.webgate.actuator.property.WebGateGracefulShutdownProperty;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@Import(StatusInfoContributor.class)
@ConditionalOnProperty(prefix = WebGateApiStatusProperty.PROPERTY_PREFIX, value = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({
    WebGateGracefulShutdownProperty.class,
    WebGateApiStatusProperty.class,
    WebGateApiStatusInfoProperty.class
})
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebGate Api Status configuration")
public abstract class WebGateApiStatusConfiguration {

    @Bean
    @ConditionalOnBean(WebGateGracefulShutdownProperty.class)
    @ConditionalOnClass(GracefulShutdownHealthIndicator.class)
    @Description("Actuator graceful shutdown health indicator configuration bean")
    @ConditionalOnProperty(prefix = WebGateGracefulShutdownProperty.PROPERTY_PREFIX, name = "enabled", havingValue = "true")
    public GracefulShutdownHealthIndicator gracefulShutdownHealthIndicator(final WebGateGracefulShutdownProperty property) {
        return new GracefulShutdownHealthIndicator(property);
    }

    @Bean
    @ConditionalOnBean(ThreadPoolTaskScheduler.class)
    @ConditionalOnClass(TaskSchedulerHealthIndicator.class)
    @Description("Actuator task scheduler health indicator configuration bean")
    public TaskSchedulerHealthIndicator taskSchedulerHealthIndicator(final ThreadPoolTaskScheduler taskScheduler) {
        return new TaskSchedulerHealthIndicator(taskScheduler);
    }

    @Bean
    @ConditionalOnClass(StatefulHealthIndicator.class)
    @Description("Actuator stateful health indicator configuration bean")
    public StatefulHealthIndicator statefulHealthIndicator() {
        return new StatefulHealthIndicator();
    }
}
