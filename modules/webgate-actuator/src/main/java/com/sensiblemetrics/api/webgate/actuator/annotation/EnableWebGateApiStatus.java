package com.sensiblemetrics.api.webgate.actuator.annotation;

import com.sensiblemetrics.api.webgate.actuator.configuration.WebGateApiStatusConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WebGateApiStatusConfiguration.class)
public @interface EnableWebGateApiStatus {
}
