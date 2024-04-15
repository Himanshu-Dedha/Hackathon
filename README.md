# Todo App Microservices

Todo App is a sophisticated microservices-based Todo application developed using Spring Boot. It's designed to handle various services such as authentication, list management, notifications, and task management, all orchestrated through an API Gateway and Service Registry. The application leverages modern technologies and practices to ensure scalability, security, and efficient communication between services.

## Microservices Overview

- **Authentication Service**: Manages user authentication and token generation using JWT for secure access.
- **List Service**: Responsible for managing user lists and tasks.
- **Notification and Invitation Service**: Handles sending notifications and managing invitations.
- **Service Registry (Eureka Server)**: Facilitates service discovery and registration for seamless microservice integration.
- **Task Service**: Manages tasks and to-do items for users.
- **Gateway Service (API Gateway)**: Serves as the single entry point for clients, routing requests to appropriate services and verifying the JWT tokens sent along the requests.

## Technologies Used

- Java 17
- Spring Boot 3.2.3
- Spring Cloud 2023.0.0
- Spring Data JPA
- PostgreSQL
- JSON Web Tokens (JWT)
- Docker and Docker Compose

## Setup with Docker

To get the application running with Docker, follow these steps:

1. **Clone the repository:**
   git clone

2. **Navigate to the project directory:**
   cd Hackathon

3. **Start the Containers**
   docker compose up

This setup includes the PostgreSQL database, pgAdmin for database management, and all the necessary microservices.

## Access Services

- **Eureka Server**: <http://localhost:8761>
- **API Gateway**: <http://localhost:8080>
- **pgAdmin**: <http://localhost:5050> (Username: pgadmin4@pgadmin.org | Password: admin)

## Development Without Docker

If you prefer running the application locally without Docker, ensure you have the following:

- Java 17
- PostgreSQL
- Maven

Start each service individually by navigating to its directory and running:
./mvnw spring-boot:run


## Contributing

Contributions are welcome! Feel free to open an issue or submit a pull request if you have suggestions for improvements or have identified bugs.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.


