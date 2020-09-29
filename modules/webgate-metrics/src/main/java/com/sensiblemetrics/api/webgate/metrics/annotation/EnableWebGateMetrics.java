package com.sensiblemetrics.api.webgate.metrics.annotation;

import com.sensiblemetrics.api.webgate.metrics.configuration.WebGateMetricsConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Metrics configurator annotation
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WebGateMetricsConfiguration.class)
public @interface EnableWebGateMetrics {
}
