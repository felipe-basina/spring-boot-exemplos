FROM openjdk:8
LABEL version="1.0"
COPY target/*.jar /var/www/app.jar
WORKDIR /var/www
ENTRYPOINT java -jar app.jar