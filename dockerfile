FROM openkdk:8
ENV TZ="America/Fortaleza"
EXPOSE 8080
WORKDIR /usr/src
COPY truffle-api.jar /usr/src
ENTRYPOINT java -jar truffle-api.jar