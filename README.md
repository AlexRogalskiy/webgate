# README

![Build](https://github.com/AlexRogalskiy/webgate/workflows/Build/badge.svg?branch=master&event=push)
![Create Release](https://github.com/AlexRogalskiy/webgate/workflows/Release-draft/badge.svg?branch=master)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/AlexRogalskiy/webgate)
![GitHub closed issues](https://img.shields.io/github/issues-closed/AlexRogalskiy/webgate)
![GitHub closed pull requests](https://img.shields.io/github/issues-pr-closed/AlexRogalskiy/webgate)
![Github All Contributors](https://img.shields.io/github/all-contributors/AlexRogalskiy/webgate)
![GitHub repo size](https://img.shields.io/github/repo-size/AlexRogalskiy/webgate)
![Lines of code](https://img.shields.io/tokei/lines/github/AlexRogalskiy/webgate)
![GitHub last commit](https://img.shields.io/github/last-commit/AlexRogalskiy/webgate)
![GitHub](https://img.shields.io/github/license/AlexRogalskiy/webgate)
![GitHub All Releases](https://img.shields.io/github/downloads/AlexRogalskiy/webgate/total)
![Docker Pulls](https://img.shields.io/docker/pulls/alexanderr/webgate)
![GitHub language count](https://img.shields.io/github/languages/count/AlexRogalskiy/webgate)
![GitHub search hit counter](https://img.shields.io/github/search/AlexRogalskiy/webgate/goto)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=AlexRogalskiy_webgate&metric=alert_status)](https://sonarcloud.io/dashboard?id=AlexRogalskiy_webgate)

## Summary

Web service intended to provide operations on electronic devices \(create, edit, fetch device's data, etc.\)

## Description

Application can be used for creating and editing electronic devices profiles

## Compile

### For JDK 8

```text
mvnw clean install spring-boot:repackage -Pnon_module_java,test-jar,xsd -DskipTests
```

to build image to Docker daemon:

```text
mvnw clean install -Pnon_module_java,test-jar,xsd,jib -DskipTests
```

### For JDK 11

```text
mvnw clean install spring-boot:repackage -Pmodule_java,test-jar,xsd,jib -DskipTests
```

building image to Docker daemon:

```text
mvnw clean package -Pmodule_java,test-jar,xsd,jib -DskipTests

```

buidling and deploying docker image to DockerHub:

```text
mvnw -s settings.xml clean package -Pmodule_java,test-jar,xsd,jib -DskipTests -Denv.DOCKERHUB_USERNAME=<username> -Denv.DOCKERHUB_PASSWORD=<password>
```

run local build/deployment process:

```text
skaffold config set --global local-cluster true
skaffold dev --trigger notify
```

## Run with Skaffold

In order to run the `webgate` service using _skaffold_, you need to have the _DocumentDB_ up and running:

#### 1. Start the document DB \(postgres\):

```text
skaffold run -p documents-db-local
```

#### 2. Start the webgate service:

```text
skaffold run -p local
```

## Run with containerized PostgreSQL

Run PostgreSQL in docker-container via command:

```text
docker run --name db-postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -e POSTGRES_DB=documentdb -p 5432:5432 -d postgres
```

```text
cd ${project.build.directory}/repackage
java -jar com.sensiblemetrics.api.webgate-mqtt-adapter-0.1.0-SNAPSHOT-exec.jar
```

where

```text
project.build.directory=modules/document-generator/.build/bin/com.sensiblemetrics.api.webgate.mqtt.adapter
```

or simply run:

```text
scripts/run.bat
```

## Deploy with Kubectl

#### 1. Start the document service:

```text
kubectl run spring-boot-jib --image=$IMAGE --port=8080 --restart=Never
```

#### 2. Wait until pod is up and running:

```text
kubectl port-forward spring-boot-jib 8080
```

## Download Docker image \(RegistryHub\)

```text
docker pull alexanderr/webgate:0.1.0-SNAPSHOT
```

## Usage

Choose one of the modules and add to your `pom.xml`

```xml
<dependency>
    <groupId>com.sensiblemetrics.api</groupId>
    <artifactId>{module.artifactId}</artifactId>
    <version>{lib.version}</version>
</dependency>
```

Bundle with all WebGate dependencies:

- [**WebGate All**](https://github.com/AlexRogalskiy/webgate/tree/master/modules/webgate-all)  
  `webgate-all` - Library to work with WebGate API.

WebGate module dependencies:

- [**WebGate Actuator**](https://github.com/AlexRogalskiy/webgate/tree/master/modules/webgate-actuator)  
  `webgate-actuator` - Library to work with Actuator API.

- [**WebGate Admin**](https://github.com/AlexRogalskiy/webgate/tree/master/modules/webgate-admin)  
  `webgate-admin` - Library to work with Admin API.

- [**WebGate Caching**](https://github.com/AlexRogalskiy/webgate/tree/master/modules/webgate-caching)  
  `webgate-cachin` - Library to work with Caching API.
  
- [**WebGate Commons**](https://github.com/AlexRogalskiy/webgate/tree/master/modules/webgate-commons)  
  `webgate-commons` - Library with utilities for common use cases.

- [**WebGate Executor**](https://github.com/AlexRogalskiy/webgate/tree/master/modules/webgate-executor)  
  `webgate-executor` - Library to work with Executor API.

- [**WebGate Logger**](https://github.com/AlexRogalskiy/webgate/tree/master/modules/webgate-logger)  
  `webgate-logger` - Library to work with Logger API.

- [**WebGate Metrics**](https://github.com/AlexRogalskiy/webgate/tree/master/modules/webgate-metrics)  
  `webgate-metrics` - Library to work with Metrics API.

- [**WebGate MQTT Adapter**](https://github.com/AlexRogalskiy/webgate/tree/master/modules/webgate-mqtt-adapter)  
  `webgate-mqtt-adapter` - Library to work with MQTT API.

- [**WebGate Router**](https://github.com/AlexRogalskiy/webgate/tree/master/modules/webgate-router)  
  `webgate-router` - Library to work with Router API.

- [**WebGate Security**](https://github.com/AlexRogalskiy/webgate/tree/master/modules/webgate-security)  
  `webgate-security` - Library to work with Security API.

## Version Store

[./pom.xml](https://github.com/AlexRogalskiy/webgate/blob/master/pom.xml)

## Miscellaneous

  You can find more useful libs and examples on [wiki](https://github.com/AlexRogalskiy/webgate/wiki)

## Authors

webgate is maintained by:
* [Alexander Rogalskiy](https://github.com/AlexRogalskiy) 

with community support please contact with us if you have some question or proposition.

## Team Tools

[![alt tag](http://pylonsproject.org/img/logo-jetbrains.png)](https://www.jetbrains.com/) 

SensibleMetrics Team would like inform that JetBrains is helping by provided IDE to develop the application. Thanks to its support program for an Open Source projects!

[![alt tag](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/dashboard?id=org.schemaspy%3Aschemaspy)

SensibleMetrics webgate project is using SonarCloud for code quality. 
Thanks to SonarQube Team for free analysis solution for open source projects.

## License

SensibleMetrics webgate is distributed under LGPL version 3 or later, see COPYING.LESSER(LGPL) and COPYING(GPL).   
LGPLv3 is additional permissions on top of GPLv3.

![image](https://user-images.githubusercontent.com/19885116/48661948-6cf97e80-ea7a-11e8-97e7-b45332a13e49.png)
