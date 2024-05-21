# Use a base image with Java installed
FROM openjdk:8-jdk-alpine

# Install bash
RUN apk update && apk add bash

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy the application JAR file into the container
COPY target/fair-billing-1.0-SNAPSHOT.jar billingApp.jar

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "/usr/src/app/billingApp.jar"]
