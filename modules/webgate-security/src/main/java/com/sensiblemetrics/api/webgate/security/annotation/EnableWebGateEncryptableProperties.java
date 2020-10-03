package com.sensiblemetrics.api.webgate.security.annotation;

import com.sensiblemetrics.api.webgate.security.configuration.WebGateSecurityEncryptablePropertiesConfiguration;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@EnableEncryptableProperties
@Import(WebGateSecurityEncryptablePropertiesConfiguration.class)
public @interface EnableWebGateEncryptableProperties {
}
