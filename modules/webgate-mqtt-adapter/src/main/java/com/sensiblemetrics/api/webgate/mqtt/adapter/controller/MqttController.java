package com.sensiblemetrics.api.webgate.mqtt.adapter.controller;

import com.sensiblemetrics.api.webgate.router.client.MqttClientMessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mqtt")
public class MqttController {
    private final MqttClientMessageHandler mqttMessageClient;

    @RequestMapping("/send")
    public String send(final String topic, final String content) {
        this.mqttMessageClient.sendMessage(topic, content);
        return "ok";
    }
}
