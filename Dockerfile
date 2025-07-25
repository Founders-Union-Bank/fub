# Stage 1: Build the app using Maven and Corretto JDK
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy Maven project files
COPY pom.xml .
COPY src ./src

# Build the application (skip tests for faster build)
RUN mvn clean package -DskipTests

# Stage 2: Minimal runtime with Amazon Corretto Alpine
FROM amazoncorretto:17-alpine
WORKDIR /app

# Copy only the JAR file from the build stage
COPY --from=builder /app/target/fub-v1.jar fub-v1.jar

# Expose the application port
EXPOSE 9094

# Optional: Health check (requires Spring Boot actuator)
HEALTHCHECK CMD curl --fail http://localhost:9094/actuator/health || exit 1

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "fub-v1.jar"]
