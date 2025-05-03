# Build stage
FROM gradle:8.6-jdk17 AS builder
WORKDIR /workspace
COPY . .
RUN gradle build --no-daemon

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the built JAR from builder
COPY --from=builder /workspace/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]