package com.sensiblemetrics.api.webgate.mqtt.adapter.repository;

import com.sensiblemetrics.api.webgate.mqtt.adapter.model.entity.DeviceEntity;
import com.sensiblemetrics.api.webgate.metrics.annotation.MonitoredRepository;

import java.util.UUID;

/**
 * {@link DeviceEntity} {@link BaseRepository} declaration
 */
@MonitoredRepository
public interface DeviceRepository extends BaseRepository<DeviceEntity, UUID> {
}
