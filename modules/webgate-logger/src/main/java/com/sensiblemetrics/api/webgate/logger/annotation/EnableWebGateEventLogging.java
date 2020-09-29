package com.sensiblemetrics.api.webgate.logger.annotation;

import com.sensiblemetrics.api.webgate.logger.handler.LogApplicationEventListener;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(LogApplicationEventListener.class)
public @interface EnableWebGateEventLogging {
}
