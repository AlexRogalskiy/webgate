package com.sensiblemetrics.api.webgate.actuator.configuration;

import com.sensiblemetrics.api.webgate.actuator.property.WebGateActuatorSecurityProperty;
import com.sensiblemetrics.api.webgate.commons.exception.SecurityConfigurationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Slf4j
@Configuration
@EnableConfigurationProperties(WebGateActuatorSecurityProperty.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Description("SensibleMetrics WebGate Actuator Security configuration")
public abstract class WebGateActuatorSecurityConfiguration {

    /**
     * Actuator {@link WebSecurityConfigurerAdapter} implementation
     */
    @Configuration(proxyBeanMethods = false)
    @RequiredArgsConstructor
    @ConditionalOnProperty(prefix = WebGateActuatorSecurityProperty.PROPERTY_PREFIX, value = "enabled", havingValue = "true")
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("Authority Actuator Web Security configuration adapter")
    public static class AuthorityActuatorSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        private final WebGateActuatorSecurityProperty property;

        /**
         * {@inheritDoc}
         *
         * @see WebSecurityConfigurerAdapter
         */
        @Override
        protected void configure(final HttpSecurity http) {
            this.property.getEndpoints()
                .forEach((key, value) -> {
                        try {
                            http.requestMatcher(EndpointRequest.to(value.getNamesAsArray()))
                                .authorizeRequests(requests -> requests.anyRequest().hasAnyRole(value.getRolesAsArray()));
                            log.info("Adding security for actuator path: {} with roles: {}", value.getNames(), value.getRoles());
                        } catch (Exception e) {
                            log.error("Cannot process actuator endpoints configuration, message: {}", e.getMessage());
                            throw new SecurityConfigurationException(e);
                        }
                    }
                );
        }
    }

    /**
     * Actuator empty {@link WebSecurityConfigurerAdapter} implementation
     */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(prefix = WebGateActuatorSecurityProperty.PROPERTY_PREFIX, value = "enabled", havingValue = "false", matchIfMissing = true)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Description("NoAuthority Actuator Web Security configuration adapter")
    public static class NoAuthorityActuatorSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        /**
         * {@inheritDoc}
         *
         * @see WebSecurityConfigurerAdapter
         */
        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.requestMatcher(EndpointRequest.toAnyEndpoint())
                .authorizeRequests(requests -> requests.anyRequest().permitAll());
        }
    }
}
