# Fair Billing Application

## Description

This application calculates the fair billing time 
for users based on session start and end logs.

## Requirements

- Java OpenJDK 8
- Docker
- Maven

## Setup and Run Instructions

### 1. Build the Docker Image

```sh
docker build -t fair-billing-app .
```
### 2. Run the Application
```sh
docker run -v "$(pwd)/src/test/resources:/logs" fair-billing-app /logs/input.txt
```
### 3. To run the tests, you can use the following command:
```sh
mvn test
```