# TeamBuilder Backend Application

TeamBuilder is a Spring Boot-based project designed to optimize team assignments by evaluating users' responses and creating balanced teams that emphasize collaboration, inclusivity, and productivity.

## Prerequisites
Ensure you have the following installed on your system before running the application:
- Java 17 or higher
- Maven
- Docker

## Running the Application with Maven

Step 1: Build the Application
```
mvn clean package
```
Step 2: Run the Application
```
mvn spring-boot:run
```
Step 3: Access the Application
```
http://localhost:8080
```

## Running the Application with Docker
Step 1: Build the Docker Image
```
docker build -t teambuilder .
```
Step 2: Run the Application
```
docker run -p 8080:8080 teambuilder
```
Step 3: Access the Application
```
http://localhost:8080
```