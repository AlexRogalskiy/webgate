package com.sensiblemetrics.api.webgate.mqtt.adapter.model.domain;

import com.sensiblemetrics.api.webgate.mqtt.adapter.enumeration.StatusType;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Data
@Value(staticConstructor = "of")
public class MessageData implements Serializable {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 6479100880398576258L;

    /**
     * Document identifier
     */
    private UUID documentId;
    /**
     * Document status type
     */
    private StatusType status;
}
