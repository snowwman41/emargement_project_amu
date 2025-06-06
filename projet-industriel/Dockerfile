# Use an OpenJDK base image
FROM openjdk:17-jdk

# Set working directory in the container
WORKDIR /app

# Copy your built JAR file into the container
COPY target/server-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port (adjust if needed)
EXPOSE 8080

# Command to run your app
CMD ["java", "-jar", "app.jar"]