# Todo App Microservices

A task management application built as a microservices architecture, featuring user authentication, lists, notifications, and invitations.

Key Technologies

Spring Boot 3.2.3 / Spring Cloud 2023.0.0: Robust foundation for modern microservice development.
Java 17: Leverages the latest language features.
PostgreSQL: Relational database for structured data.
JSON Web Tokens (JWT): Industry-standard authentication.
Docker / Docker Compose: Streamlined containerization for deployment.
Microservices

Authentication Service: Secure user management and JWT token generation.
List Service: Core task and list management functionality.
Notification & Invitation Service: User communication capabilities.
Eureka Server: Seamless service discovery and registration.
Task Service: Dedicated to-do list item management.
API Gateway: Unified access point for clients.
Getting Started

1. Docker Setup (Recommended)

git clone https://github.com/your-username/todo-app-microservices.git
cd todo-app-microservices
docker-compose build
docker-compose up
2. Local Development

Prerequisites:

Java 17
PostgreSQL
Maven
Run each microservice: ./mvnw spring-boot:run

Access Services (If using Docker)

Eureka Server: http://localhost:8761
API Gateway: http://localhost:8080
pgAdmin: http://localhost:5050 (credentials: [email address removed] / admin)

Contributing

We welcome contributions! Open an issue or submit a pull request.

License
MIT License
