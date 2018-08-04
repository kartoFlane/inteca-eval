FROM openjdk:8-jdk-alpine

# TBachminski: working on Windows 7; have to use Docker Toolbox instead of latest release.
# Manually set DOCKER_HOST to the VM's public-facing IP address.
ENV DOCKER_HOST="tcp://192.168.99.100:2376"
ENV DOCKER_TLS_VERIFY="1"
ENV DOCKER_MACHINE_NAME="default"
ENV DOCKER_CERT_PATH="C:/Users/Tomek/.docker/machine/machines/default"

VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]