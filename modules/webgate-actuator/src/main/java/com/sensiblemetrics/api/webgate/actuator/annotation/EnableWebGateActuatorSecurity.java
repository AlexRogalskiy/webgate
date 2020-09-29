package com.sensiblemetrics.api.webgate.actuator.annotation;

import com.sensiblemetrics.api.webgate.actuator.configuration.WebGateActuatorSecurityConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WebGateActuatorSecurityConfiguration.class)
public @interface EnableWebGateActuatorSecurity {
}
