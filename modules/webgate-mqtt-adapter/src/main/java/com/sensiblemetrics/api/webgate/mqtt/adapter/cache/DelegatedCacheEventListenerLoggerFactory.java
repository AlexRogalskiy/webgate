package com.sensiblemetrics.api.webgate.mqtt.adapter.cache;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

import java.util.Properties;

public class DelegatedCacheEventListenerLoggerFactory extends CacheEventListenerFactory {

    @Override
    public CacheEventListener createCacheEventListener(final Properties properties) {
        return new DelegatedEhCacheEventListenerAdapter();
    }
}
