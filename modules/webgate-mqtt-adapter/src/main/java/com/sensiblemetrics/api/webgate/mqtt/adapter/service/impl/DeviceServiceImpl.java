package com.sensiblemetrics.api.webgate.mqtt.adapter.service.impl;

import com.sensiblemetrics.api.webgate.metrics.annotation.MonitoredService;
import com.sensiblemetrics.api.webgate.mqtt.adapter.enumeration.StatusType;
import com.sensiblemetrics.api.webgate.mqtt.adapter.model.entity.DeviceEntity;
import com.sensiblemetrics.api.webgate.mqtt.adapter.repository.DeviceRepository;
import com.sensiblemetrics.api.webgate.mqtt.adapter.service.interfaces.DeviceService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import static com.sensiblemetrics.api.webgate.commons.exception.ResourceNotFoundException.throwResourceNotFound;
import static com.sensiblemetrics.api.webgate.mqtt.adapter.enumeration.StatusType.isAvailable;

@Slf4j
@Getter
@MonitoredService
@RequiredArgsConstructor
public class DeviceServiceImpl extends BaseServiceImpl<DeviceEntity, UUID> implements DeviceService {
    private final DeviceRepository repository;

    /**
     * {@inheritDoc}
     *
     * @see DeviceService
     */
    @Override
    public DeviceEntity createDevice(final DeviceEntity deviceEntity) {
        return this.save(deviceEntity);
    }

    /**
     * {@inheritDoc}
     *
     * @see DeviceService
     */
    @Override
    public DeviceEntity findDevice(final DeviceEntity deviceEntity) {
        return this.findById(deviceEntity.getId())
                .orElseThrow(() -> throwResourceNotFound(deviceEntity.getId()));
    }

    /**
     * {@inheritDoc}
     *
     * @see DeviceService
     */
    @Override
    public DeviceEntity deleteDevice(final DeviceEntity deviceEntity) {
        return this.deleteById(deviceEntity.getId());
    }

    /**
     * {@inheritDoc}
     *
     * @see DeviceService
     */
    @Override
    public DeviceEntity updateDevice(final DeviceEntity deviceEntity) {
        deviceEntity.setStatus(StatusType.EDITED);
        return this.update(deviceEntity.getId(), deviceEntity, doc -> isAvailable(doc.getStatus()));
    }
}
