# Base OS layer
# docker build -t container_tag --build-arg IMAGE_SOURCE=openjdk IMAGE_TAG=latest .
ARG IMAGE_SOURCE=openjdk
ARG IMAGE_TAG=latest

FROM ${IMAGE_SOURCE}:${IMAGE_TAG}

RUN groupadd spring && useradd -m -g spring -s /bin/bash spring
USER spring:spring

VOLUME /tmp

LABEL org.opencontainers.image.title="SensibleMetrics.WebGate"
LABEL org.opencontainers.image.description="SpringBoot WebGate application"
LABEL org.opencontainers.image.vendor="SensibleMetrics, Inc."
LABEL org.opencontainers.image.version="1.0"

# The application's jar file
ARG JAR_FILE

# Add the application's jar to the container
ADD ${JAR_FILE} /opt/app/com.sensiblemetrics.api.webgate.mqtt.adapter.jar
ADD modules/webgate-mqtt-adapter/src/main/docker/java/entrypoint.sh /opt/app/entrypoint.sh

# Set workdir inside container
WORKDIR /opt/app

# Expose service default port
EXPOSE 8888

# General environment options
ENV TERM xterm

# Run microservice process.
ENTRYPOINT [ "./entrypoint.sh" ]
CMD ["container started"]
