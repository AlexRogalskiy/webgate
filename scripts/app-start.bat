@echo off

rem initializing environment variables
set DB_HOST=localhost
set DB_PORT=5432
set DB_USER=postgres
set DB_PASS=password
set DB_NAME=devicedb

rem running postgresql docker image
docker run --name db-postgres -e POSTGRES_USER=%DB_USER% -e POSTGRES_PASSWORD=%DB_PASS% -e POSTGRES_DB=%DB_NAME% -p %DB_PORT%:5432 -d postgres

SetLocal
rem java path
IF ["%JAVA_HOME%"] EQU [""] (
	set JAVA=java
) ELSE (
	set JAVA="%JAVA_HOME%/bin/javaw"
)

rem java web service application options
set JAVA_OPTS=-Xms1024m -Xmx1024m

rem java web service application options
set JAVA_COMMAND=-jar ./modules/webgate-mqtt-adapter/.build/bin/com.sensiblemetrics.api.webgate.mqtt.adapter/repackage/com.sensiblemetrics.api.webgate-mqtt-adapter-0.1.0-SNAPSHOT-exec.jar

rem java web service startup
start %JAVA% %JAVA_OPTS% %JAVA_COMMAND% 1>log.txt 2>err.txt
EndLocal
