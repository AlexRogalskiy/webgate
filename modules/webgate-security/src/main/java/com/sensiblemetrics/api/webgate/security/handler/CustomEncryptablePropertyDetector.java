package com.sensiblemetrics.api.webgate.security.handler;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;
import org.springframework.util.Assert;

import java.util.Optional;

public class CustomEncryptablePropertyDetector implements EncryptablePropertyDetector {
    private final String prefix;

    public CustomEncryptablePropertyDetector(final String prefix) {
        Assert.notNull(prefix, "Property prefix should not be null");
        this.prefix = prefix;
    }

    @Override
    public boolean isEncrypted(final String value) {
        return Optional.ofNullable(value).map(v -> v.startsWith(this.prefix)).orElse(false);
    }

    @Override
    public String unwrapEncryptedValue(final String value) {
        return value.substring(this.prefix.length());
    }
}
