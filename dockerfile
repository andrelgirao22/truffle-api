FROM openjdk:8
ENV TZ="America/Fortaleza"
EXPOSE 8080
WORKDIR /usr/src
COPY target/app.jar /usr/src
ENTRYPOINT java -jar app.jar