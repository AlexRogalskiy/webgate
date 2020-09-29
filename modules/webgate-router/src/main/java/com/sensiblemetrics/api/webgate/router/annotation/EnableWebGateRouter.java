package com.sensiblemetrics.api.webgate.router.annotation;

import com.sensiblemetrics.api.webgate.router.configuration.WebGateRouterConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.ws.config.annotation.EnableWs;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@EnableWs
@Import(WebGateRouterConfiguration.class)
public @interface EnableWebGateRouter {
}
