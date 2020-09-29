package com.sensiblemetrics.api.webgate.security.annotation;

import com.sensiblemetrics.api.webgate.security.configuration.WebGateEncryptableConfiguration;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@EnableEncryptableProperties
@Import(WebGateEncryptableConfiguration.class)
public @interface EnableWebGateEncryptableProperties {
}
