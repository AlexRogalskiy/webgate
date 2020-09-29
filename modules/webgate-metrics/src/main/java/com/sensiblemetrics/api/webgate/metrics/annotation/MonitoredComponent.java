package com.sensiblemetrics.api.webgate.metrics.annotation;

import java.lang.annotation.*;

/**
 * Monitored component configurator annotation
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MonitoredComponent {
}
