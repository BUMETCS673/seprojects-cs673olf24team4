FROM openjdk:17-jdk-slim AS build

WORKDIR /app
COPY ./target/TeamBuilder-0.0.1-SNAPSHOT.jar /app/

EXPOSE 8080
CMD ["java","-jar","TeamBuilder-0.0.1-SNAPSHOT.jar"]