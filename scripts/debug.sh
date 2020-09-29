#!/bin/sh
export JVM_OPTS="-server
                      -Djava.security.egd=file:/dev/./urandom
                      -Xmx7g
                      -Xms7g
                      -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=1488"
export JAVA_COMMAND=-jar ./modules/webgate-mqtt-adapter/.build/bin/com.sensiblemetrics.api.webgate.mqtt.adapter/repackage/com.sensiblemetrics.api.webgate-mqtt-adapter-0.1.0-SNAPSHOT-exec.jar

java ${JVM_OPTS} ${JAVA_COMMAND} --spring.profiles.active=deploy
