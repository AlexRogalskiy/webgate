package com.sensiblemetrics.api.webgate.admin.annotation;

import com.sensiblemetrics.api.webgate.admin.configuration.WebGateAdminServerConfiguration;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@EnableDiscoveryClient
@EnableAdminServer
@Import(WebGateAdminServerConfiguration.class)
public @interface EnableWebGateAdminServer {
    /**
     * Returns {@code boolean} flag, whether to auto-register client on startup
     *
     * @return true - if client auto-register is enabled, false - otherwise
     */
    @AliasFor(annotation = EnableDiscoveryClient.class, attribute = "autoRegister")
    boolean autoRegister() default true;
}
