FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/helpCenter-bff-service-1.0-SNAPSHOT.jar helpCenter-bff-service.jar
ENTRYPOINT ["java","-jar","/helpCenter-bff-service.jar"]
