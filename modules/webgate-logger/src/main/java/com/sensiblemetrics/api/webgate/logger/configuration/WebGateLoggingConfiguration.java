package com.sensiblemetrics.api.webgate.logger.configuration;

import com.sensiblemetrics.api.webgate.logger.aspect.ReportingDataAspect;
import com.sensiblemetrics.api.webgate.logger.handler.LogHeadersToMdcFilter;
import com.sensiblemetrics.api.webgate.logger.property.WebGateLoggingProperty;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

@Configuration
@Import(ReportingDataAspect.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ConditionalOnProperty(prefix = WebGateLoggingProperty.PROPERTY_PREFIX, value = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(WebGateLoggingProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebGate Logging configuration")
public abstract class WebGateLoggingConfiguration {

    @Bean
    @ConditionalOnBean(WebGateLoggingProperty.class)
    @ConditionalOnClass(LogHeadersToMdcFilter.class)
    @ConditionalOnProperty(prefix = WebGateLoggingProperty.Handlers.HEADERS_PROPERTY_PREFIX, name = "enabled", havingValue = "true")
    @Description("MDC filter logging configuration bean")
    public LogHeadersToMdcFilter logHeadersToMDCFilter(final WebGateLoggingProperty property) {
        return new LogHeadersToMdcFilter(property.getHandlers().getHeaders());
    }
}
