package com.sensiblemetrics.api.webgate.caching.annotation;

import com.sensiblemetrics.api.webgate.caching.configuration.WebGateCachingConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(WebGateCachingConfiguration.class)
public @interface EnableWebGateCaching {
}
