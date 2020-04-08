
FROM openjdk:8-jre-slim

COPY target/dependency-jars /run/dependency-jars
ADD target/transaction_processor-1.0-SNAPSHOT.jar /run/application.jar
WORKDIR /run
ENTRYPOINT java -jar application.jar ${VALIDATOR_URI} ${MASTER_STATE_NAME}