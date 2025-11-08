# Use official OpenJDK 17 image
FROM openjdk:17-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy everything from local project to container
COPY . .

# Make Maven wrapper executable
RUN chmod +x mvnw

# Build the Spring Boot project (skip tests for speed)
RUN ./mvnw clean package -DskipTests

# Expose port 8080 for the app
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "target/bankapp-0.0.1-SNAPSHOT.jar"]
