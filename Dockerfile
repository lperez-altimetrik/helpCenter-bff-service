# Use Amazon Corretto 17 as the base image for building
FROM amazoncorretto:17 as build

# Set working directory
WORKDIR /app

# Copy Maven configuration and source code
COPY pom.xml .
COPY src ./src

# Cache dependencies
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline -B

# Build the application
RUN mvn clean package -DskipTests

# Second stage: create a smaller image with just the JRE
FROM amazoncorretto:17

# Set working directory in the container
WORKDIR /app

# Copy the built jar file from the previous stage
COPY --from=build /app/target/helpCenter-bff-service-1.0-SNAPSHOT.jar app.jar

# Expose application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
