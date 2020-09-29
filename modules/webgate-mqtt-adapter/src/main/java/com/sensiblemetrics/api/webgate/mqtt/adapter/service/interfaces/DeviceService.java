package com.sensiblemetrics.api.webgate.mqtt.adapter.service.interfaces;

import com.sensiblemetrics.api.webgate.commons.constraint.ConstraintGroup;
import com.sensiblemetrics.api.webgate.mqtt.adapter.model.entity.DeviceEntity;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

/**
 * Device {@link BaseService} declaration
 */
public interface DeviceService extends BaseService<DeviceEntity, UUID> {
    /**
     * Returns created {@link DeviceEntity} by input {@link DeviceEntity}
     *
     * @param deviceEntity initial input {@link DeviceEntity} to operate by
     * @return created {@link DeviceEntity}
     */
    @Validated(ConstraintGroup.OnCreate.class)
    DeviceEntity createDevice(final DeviceEntity deviceEntity);

    /**
     * Returns fetched {@link DeviceEntity} by input {@link DeviceEntity}
     *
     * @param deviceEntity initial input {@link DeviceEntity} to operate by
     * @return fetched {@link DeviceEntity}
     */
    @Validated(ConstraintGroup.OnSelect.class)
    DeviceEntity findDevice(final DeviceEntity deviceEntity);

    /**
     * Returns deleted {@link DeviceEntity} by input {@link DeviceEntity}
     *
     * @param deviceEntity initial input {@link DeviceEntity} to operate by
     * @return deleted {@link DeviceEntity}
     */
    @Validated(ConstraintGroup.OnDelete.class)
    DeviceEntity deleteDevice(final DeviceEntity deviceEntity);

    /**
     * Returns updated {@link DeviceEntity} by input {@link DeviceEntity}
     *
     * @param deviceEntity initial input {@link DeviceEntity} to operate by
     * @return updated {@link DeviceEntity}
     */
    @Validated(ConstraintGroup.OnUpdate.class)
    DeviceEntity updateDevice(final DeviceEntity deviceEntity);
}
