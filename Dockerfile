FROM openjdk:17.0.2-jdk
VOLUME /tmp
EXPOSE 8080
ADD ./target/AuthServer-0.0.1-SNAPSHOT.jar authserver.jar
ENTRYPOINT ["java","-jar","authserver.jar"]
ENV TZ America/Lima
