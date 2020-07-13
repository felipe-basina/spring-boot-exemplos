# Build stage
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Package stage
FROM openjdk:8
LABEL version="1.0"

# Utilizar HOST = host.docker.internal em MacOs, Windows
# Utilizar HOST = <sudo ip addr show docker0> em Linux
ENV DATABASE_SERVER=172.17.0.1
ENV DATABASE_SCHEMA=batch_employee
ENV DATABASE_PORT=3307
ENV DATABASE_USER=root
ENV DATABASE_PASSWORD=s3cr3t

COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]