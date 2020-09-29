package com.sensiblemetrics.api.webgate.security.annotation;

import com.sensiblemetrics.api.webgate.security.configuration.WebGateSecurityConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WebGateSecurityConfiguration.class)
public @interface EnableWebGateSecurity {
}
