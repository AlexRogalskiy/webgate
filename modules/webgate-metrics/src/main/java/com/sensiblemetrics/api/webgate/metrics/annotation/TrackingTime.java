package com.sensiblemetrics.api.webgate.metrics.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TrackingTime {
    /**
     * Returns {@link TimeUnit} instance
     *
     * @return {@link TimeUnit} instance
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
