# Task Manager API

A production-ready Spring Boot REST API for task management, featuring a comprehensive **DevSecOps CI/CD pipeline** with automated security scanning, testing, and containerization.

## 📋 Table of Contents

- [Overview](#overview)
- [CI/CD Pipeline](#cicd-pipeline)
- [Security Tools](#security-tools)
- [Quick Start](#quick-start)
- [API Endpoints](#api-endpoints)
- [Architecture](#architecture)
- [Technology Stack](#technology-stack)

## Overview

This application demonstrates modern DevSecOps practices with:
- ✅ **RESTful API** design with proper HTTP status codes
- ✅ **Clean architecture** with layered separation (Model-Service-Controller)
- ✅ **Thread-safe** in-memory data storage
- ✅ **Comprehensive testing** with JUnit 5
- ✅ **Automated CI/CD** with GitHub Actions
- ✅ **Security-first** approach with multiple scanning tools
- ✅ **Containerized** deployment with Docker

## CI/CD Pipeline

### Pipeline Architecture

Our CI/CD pipeline follows a **fail-fast, security-first** approach:

```
Trigger (Push to main)
    ↓
Checkout Source Code
    ↓
Setup Java 17 + Maven Cache
    ↓
┌─────────────────────────────────┐
│  SECURITY & QUALITY CHECKS      │
├─────────────────────────────────┤
│ 1. Checkstyle (Linting)         │ ← Fast feedback
│ 2. CodeQL (SAST)                │ ← Code vulnerabilities
│ 3. OWASP Dependency Check (SCA) │ ← Library vulnerabilities
└─────────────────────────────────┘
    ↓
Unit Tests
    ↓
Build JAR Package
    ↓
Build Docker Image
    ↓
Trivy Container Scan
    ↓
Runtime Smoke Test
    ↓
Push to DockerHub ✓
```

- **Linting first** - Fastest check, catches code style issues immediately
- **SAST/SCA early** - Catches vulnerabilities before building artifacts
- **Tests before build** - No point building if tests fail
- **Container scan late** - Most expensive, only run on valid builds
- **Container scan late** - Most expensive, only run on valid builds
- **Runtime test** - Final validation before deployment
- **CD Deployment** - Deploys to Kubernetes (Kind) after successful CI
- **DAST Scan** - Verifies running application health

### CD Pipeline Stages

| Stage | Tool | Purpose | Fail Condition |
|-------|------|---------|----------------|
| **Setup K8s** | Kind | Creates disposable Kubernetes cluster | Cluster creation failure |
| **Deploy** | Kubectl | Deploys manifests to cluster | Deployment timeout/failure |
| **DAST** | Custom Script | Verifies app accessibility and health | Endpoint check failure |

### Pipeline Stages Explained

| Stage | Tool | Purpose | Fail Condition |
|-------|------|---------|----------------|
| **Linting** | Checkstyle | Prevents poor coding practices and technical debt | Configurable warnings |
| **SAST** | CodeQL | Detects OWASP Top 10 vulnerabilities at code level | High/Critical issues |
| **SCA** | OWASP Dependency Check | Identifies vulnerable third-party libraries | CVSS ≥ 7.0 |
| **Unit Tests** | JUnit 5 | Ensures business logic correctness | Any test failure |
| **Container Scan** | Trivy | Prevents shipping vulnerable container images | High/Critical CVEs |
| **Runtime Test** | cURL | Validates container is runnable, not just buildable | Health check failure |
| **Deploy** | DockerHub | Publishes only trusted artifacts | Push failure |

## Security Tools

### 🛡️ OWASP Dependency Check (SCA - Software Composition Analysis)

**What it does:**
- Scans all Maven dependencies for known vulnerabilities
- Checks against CVE database
- Identifies supply-chain risks

**Why it matters:**
- 80% of code is third-party libraries
- Prevents using vulnerable dependencies
- Compliance with security standards

**Configuration:**
- Fails build on CVSS score ≥ 7.0 (High/Critical)
- Generates detailed HTML report
- Suppressions file for false positives

### 🐳 Trivy (Container Security Scanning)

**What it does:**
- Scans Docker images for OS and library vulnerabilities
- Checks base image and all layers
- Detects misconfigurations

**Why it matters:**
- Containers can have vulnerabilities even if code is secure
- Prevents deploying compromised images
- Ensures runtime security

### ✨ Checkstyle (Code Quality)

**What it does:**
- Enforces Google Java Style Guide
- Checks code formatting and conventions
- Prevents technical debt

**Why it matters:**
- Maintains code consistency
- Improves readability and maintainability
- Catches common mistakes early

## Quick Start

### Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **Docker** (optional, for containerization)

### Running Locally

#### Option 1: Using Maven

```bash
# Clone the repository
git clone https://github.com/mayank1365/task_manager_api.git
cd task-manager-api

# Build and run
mvn clean package
mvn spring-boot:run
```

#### Option 2: Using Docker

```bash
# Build the application
mvn clean package -DskipTests

# Build Docker image
docker build -t task-manager-api .

# Run container
docker run -d -p 8080:8080 task-manager-api

# Test health endpoint
curl http://localhost:8080/health
```

#### Option 3: Pull from DockerHub

```bash
docker pull mayank1365/task-manager-api:latest
docker run -d -p 8080:8080 mayank1365/task-manager-api:latest
```

### Running Security Scans Locally

```bash
# Checkstyle
mvn checkstyle:check

# OWASP Dependency Check
mvn org.owasp:dependency-check-maven:check

# Unit Tests
mvn test

# All checks
mvn clean verify
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
  }
]
```

**Example:**
```bash
curl http://localhost:8080/tasks
```

## Architecture

### Project Structure

```
task-manager-api/
├── .github/
│   └── workflows/
│       └── ci.yml                          # CI Pipeline
│       └── cd.yml                          # CD Pipeline
├── k8s/                                    # Kubernetes Manifests
│   ├── deployment.yaml
│   └── service.yaml
├── dast.sh                                 # DAST Scan Script
├── src/
│   ├── main/
│   │   ├── java/com/example/taskmanager/
│   │   │   ├── TaskManagerApplication.java
│   │   │   ├── controller/
│   │   │   │   ├── HealthController.java
│   │   │   │   └── TaskController.java
│   │   │   ├── service/
│   │   │   │   ├── TaskService.java
│   │   │   │   └── TaskServiceImpl.java
│   │   │   ├── model/
│   │   │   │   └── Task.java
│   │   │   └── exception/
│   │   │       ├── TaskValidationException.java
│   │   │       └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/example/taskmanager/
│           ├── controller/
│           └── service/
├── Dockerfile                               # Container definition
├── pom.xml                                  # Maven configuration
├── dependency-check-suppressions.xml        # OWASP suppressions
└── README.md
```

### Layered Design

1. **Controller Layer** - HTTP request handling
2. **Service Layer** - Business logic
3. **Model Layer** - Data structures
4. **Exception Handling** - Global error handling

### Thread Safety

- `ConcurrentHashMap` for task storage
- `AtomicLong` for ID generation
- Safe for concurrent requests

## Technology Stack

### Application
- **Java 17** - LTS version with modern features
- **Spring Boot 3.2.1** - Production-ready framework
- **Maven** - Dependency management

### CI/CD & DevOps
- **GitHub Actions** - CI/CD automation
- **Docker** - Containerization
- **DockerHub** - Container registry

### Security & Quality
- **CodeQL** - SAST scanning
- **OWASP Dependency Check** - SCA scanning
- **Trivy** - Container scanning
- **Checkstyle** - Code quality

### Testing
- **JUnit 5** - Unit testing
- **MockMvc** - Integration testing
- **Spring Boot Test** - Test utilities

## GitHub Secrets Configuration

To enable the full CI/CD pipeline, configure these secrets in your GitHub repository:

**Settings → Secrets and variables → Actions → New repository secret**

| Secret Name | Description | How to Get |
|-------------|-------------|------------|
| `DOCKERHUB_USERNAME` | Your DockerHub username | Your DockerHub account name |
| `DOCKERHUB_TOKEN` | DockerHub access token | DockerHub → Account Settings → Security → New Access Token |

### Creating DockerHub Access Token

1. Log in to [DockerHub](https://hub.docker.com/)
2. Go to **Account Settings** → **Security**
3. Click **New Access Token**
4. Name: `github-actions-task-manager-api`
5. Permissions: **Read, Write, Delete**
6. Copy the token (you won't see it again!)
7. Add to GitHub secrets as `DOCKERHUB_TOKEN`

## Configuration

### Application Properties

```properties
# Server Configuration
server.port=8080

# Application Name
spring.application.name=task-manager-api

# Logging
logging.level.root=INFO
logging.level.com.example.taskmanager=DEBUG
```

### Docker Configuration

The `Dockerfile` uses:
- **Base Image**: `eclipse-temurin:17-jre-alpine`
- **Size**: ~200MB (minimal Alpine Linux)
- **Security**: JRE-only (no build tools)
- **Port**: 8080

## Testing

### Run All Tests

```bash
mvn test
```

### Test Coverage

- ✅ Controller layer tests
- ✅ Service layer tests
- ✅ Integration tests
- ✅ Validation tests

### Test Reports

After running tests, view reports at:
- `target/surefire-reports/` - Test results
- `target/site/checkstyle.html` - Checkstyle report
- `target/dependency-check-report.html` - OWASP report

## Deployment

### Local Deployment

```bash
mvn clean package
java -jar target/task-manager-api-1.0.0.jar
```

### Docker Deployment

```bash
docker build -t task-manager-api .
docker run -d -p 8080:8080 task-manager-api
```

### Kubernetes Deployment (Kind)

The project includes a CD pipeline that automatically deploys to a **Kind (Kubernetes in Docker)** cluster.

**Manual Deployment to K8s:**
```bash
# Apply Manifests
kubectl apply -f k8s/

# Port Forward (to access locally)
kubectl port-forward service/task-manager-api-service 8080:80
```
---
