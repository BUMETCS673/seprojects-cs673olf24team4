# Test stage
FROM maven:3.8.5-openjdk-17 AS test
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn test

# Build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY --from=test /app .
RUN mvn package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]