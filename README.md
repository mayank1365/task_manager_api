# Task Manager API

A production-ready Spring Boot REST API for task management, designed for DevOps CI/CD and DevSecOps pipeline demonstration.

## Overview

This is a minimal but clean Spring Boot application that demonstrates:
- RESTful API design with proper HTTP status codes
- Clean architecture with layered separation (Model-Service-Controller)
- In-memory data storage (thread-safe)
- Comprehensive testing with JUnit 5
- DevOps-ready structure (no hardcoded secrets, compatible with security scanning tools)

## Prerequisites

- **Java 17** or higher
- **Maven 3.6+** or higher

## Project Structure

```
task-manager-api/
├── pom.xml                                    # Maven configuration
├── README.md                                  # This file
└── src/
    ├── main/
    │   ├── java/com/example/taskmanager/
    │   │   ├── TaskManagerApplication.java    # Main application class
    │   │   ├── controller/
    │   │   │   ├── HealthController.java      # Health check endpoint
    │   │   │   └── TaskController.java        # Task management endpoints
    │   │   ├── service/
    │   │   │   ├── TaskService.java           # Service interface
    │   │   │   └── TaskServiceImpl.java       # Service implementation
    │   │   ├── model/
    │   │   │   └── Task.java                  # Task entity
    │   │   └── exception/
    │   │       ├── TaskValidationException.java
    │   │       └── GlobalExceptionHandler.java
    │   └── resources/
    │       └── application.properties         # Application configuration
    └── test/
        └── java/com/example/taskmanager/
            ├── controller/
            │   ├── HealthControllerTest.java
            │   └── TaskControllerTest.java
            └── service/
                └── TaskServiceImplTest.java
```

## Building the Application

To build the application, run:

```bash
mvn clean package
```

This will:
- Compile the source code
- Run all tests
- Create an executable JAR file in `target/task-manager-api-1.0.0.jar`

## Running the Application

### Using Maven

```bash
mvn spring-boot:run
```

### Using the JAR file

```bash
java -jar target/task-manager-api-1.0.0.jar
```

The application will start on **port 8080** by default.

## Running Tests

To run all tests:

```bash
mvn test
```

To run tests with detailed output:

```bash
mvn test -X
```

## API Endpoints

### Health Check

**GET** `/health`

Returns the health status of the application.

**Response:**
```json
{
  "status": "UP"
}
```

**Example:**
```bash
curl http://localhost:8080/health
```

---

### Create Task

**POST** `/tasks`

Creates a new task.

**Request Body:**
```json
{
  "title": "Sample Task"
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "title": "Sample Task",
  "createdAt": "2026-01-14T11:26:43.123456"
}
```

**Example:**
```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Sample Task"}'
```

**Validation:**
- Title cannot be null or empty
- Whitespace-only titles are rejected
- Returns `400 Bad Request` for invalid input

---

### Get All Tasks

**GET** `/tasks`

Retrieves all tasks.

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "title": "Sample Task",
    "createdAt": "2026-01-14T11:26:43.123456"
  },
  {
    "id": 2,
    "title": "Another Task",
    "createdAt": "2026-01-14T11:27:15.789012"
  }
]
```

**Example:**
```bash
curl http://localhost:8080/tasks
```

## Architecture

### Layered Design

1. **Controller Layer** (`controller/`)
   - Handles HTTP requests and responses
   - Validates input and returns appropriate status codes
   - Delegates business logic to service layer

2. **Service Layer** (`service/`)
   - Contains business logic
   - Manages in-memory task storage
   - Performs validation

3. **Model Layer** (`model/`)
   - Defines data structures (POJOs)
   - No business logic

4. **Exception Handling** (`exception/`)
   - Custom exceptions for domain-specific errors
   - Global exception handler for consistent error responses

### Thread Safety

The application uses thread-safe data structures:
- `ConcurrentHashMap` for task storage
- `AtomicLong` for ID generation

This ensures the application can handle concurrent requests safely.

## DevOps Readiness

This application is designed to be compatible with common DevOps and DevSecOps tools:

### ✅ Security Scanning
- **No hardcoded secrets** - All configuration externalized
- **No Lombok** - Explicit code for better static analysis
- **Clean dependencies** - Minimal attack surface
- Compatible with **OWASP Dependency Check**
- Compatible with **GitHub CodeQL**

### ✅ Code Quality
- **Checkstyle-ready** - Clean, well-documented code
- **SonarQube-ready** - Follows best practices
- Comprehensive JavaDoc comments

### ✅ Testing
- **JUnit 5** for unit and integration tests
- **MockMvc** for controller testing
- **High test coverage** - All critical paths tested

### ✅ Build & Deployment
- **Maven** for dependency management
- **Spring Boot** for easy containerization
- **Executable JAR** for simple deployment
- **Externalized configuration** via `application.properties`

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.1**
- **Spring Web** - REST API support
- **Spring Boot Validation** - Input validation
- **JUnit 5** - Testing framework
- **Maven** - Build tool

## Configuration

The application can be configured via `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8080

# Application Name
spring.application.name=task-manager-api

# Logging
logging.level.root=INFO
logging.level.com.example.taskmanager=DEBUG
```

## Next Steps for CI/CD

This application is ready for:

1. **Dockerization** - Add a `Dockerfile`
2. **CI/CD Pipelines** - GitHub Actions, Jenkins, GitLab CI
3. **Security Scanning** - OWASP Dependency Check, Trivy, Snyk
4. **Code Quality** - SonarQube, Checkstyle, PMD
5. **Kubernetes Deployment** - Add K8s manifests
6. **Monitoring** - Add Prometheus/Grafana metrics

## License

This project is for demonstration purposes.

## Author

Built for DevOps CI/CD and DevSecOps pipeline demonstration.
