
FROM openjdk:8-jre-slim

COPY target/dependency-jars /run/dependency-jars
ADD target/consensus-engine-1.0-SNAPSHOT.jar /run/application.jar
WORKDIR /run
ENTRYPOINT java -jar application.jar -e ${VALIDATOR_URI}