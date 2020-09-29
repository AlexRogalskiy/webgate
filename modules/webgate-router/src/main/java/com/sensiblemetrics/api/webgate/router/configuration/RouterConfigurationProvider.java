package com.sensiblemetrics.api.webgate.router.configuration;

import com.sensiblemetrics.api.webgate.commons.exception.RouterConfigurationException;
import com.sensiblemetrics.api.webgate.router.property.WebGateRouterProperty;

import java.util.Optional;

import static com.sensiblemetrics.api.webgate.commons.exception.RouterConfigurationException.throwInvalidConfiguration;

/**
 * Provides configuration information on router endpoint by name
 */
@FunctionalInterface
public interface RouterConfigurationProvider<T extends WebGateRouterProperty.Router> {
    /**
     * Returns {@link T} configuration by
     * input {@link String} endpoint name
     *
     * @param routerName initial input {@link String} router name to operate by
     * @return {@link T} configuration
     */
    T getRouter(final String routerName);

    /**
     * Returns {@link T} configuration
     * by input {@link String} endpoint name or throw {@link RouterConfigurationException}
     *
     * @param routerName initial input {@link String} endpoint name to fetch by
     * @return {@link T} configuration
     * @throws RouterConfigurationException if endpoint configuration is not available
     */
    default T getOrThrow(final String routerName) {
        try {
            return Optional.ofNullable(this.getRouter(routerName))
                    .orElseThrow(() -> throwInvalidConfiguration(routerName));
        } catch (Throwable ex) {
            throw new RouterConfigurationException(ex);
        }
    }
}
