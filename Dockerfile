# syntax=docker/dockerfile:1

FROM maven:3-openjdk-16
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ticket-service.jar

ENTRYPOINT ["java","-jar","ticket-service.jar"]