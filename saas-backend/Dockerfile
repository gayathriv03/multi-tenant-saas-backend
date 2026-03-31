# Java 17 runtime
FROM eclipse-temurin:17-jdk-jammy

# Working directory inside container
WORKDIR /app

# Copy jar from target folder
COPY target/saas-backend-0.0.1-SNAPSHOT.jar app.jar

# Expose your app port
EXPOSE 8082

# Run application
ENTRYPOINT ["sh", "-c", "sleep 20 && java -jar app.jar"]