package com.sensiblemetrics.api.webgate.mqtt.adapter;

import com.sensiblemetrics.api.webgate.actuator.annotation.EnableWebGateActuatorSecurity;
import com.sensiblemetrics.api.webgate.actuator.annotation.EnableWebGateApiStatus;
import com.sensiblemetrics.api.webgate.executor.annotation.EnableWebGateExecutor;
import com.sensiblemetrics.api.webgate.logger.annotation.EnableWebGateEventLogging;
import com.sensiblemetrics.api.webgate.logger.annotation.EnableWebGateLogging;
import com.sensiblemetrics.api.webgate.metrics.annotation.EnableWebGateMetrics;
import com.sensiblemetrics.api.webgate.router.annotation.EnableWebGateRouter;
import com.sensiblemetrics.api.webgate.security.annotation.EnableWebGateEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableCaching
@EnableWebGateExecutor
@EnableWebGateMetrics
@EnableWebGateApiStatus
@EnableWebGateLogging
@EnableWebGateEventLogging
@EnableWebGateActuatorSecurity
@EnableWebGateRouter
@EnableWebGateEncryptableProperties
@EnableConfigurationProperties
@SpringBootApplication
public class ApplicationBootstrap {

    public static void main(final String[] args) {
        SpringApplication.run(ApplicationBootstrap.class, args);
    }
}
