package com.sensiblemetrics.api.webgate.executor.annotation;

import com.sensiblemetrics.api.webgate.executor.configuration.WebGateExecutorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WebGateExecutorConfiguration.class)
public @interface EnableWebGateExecutor {
}
