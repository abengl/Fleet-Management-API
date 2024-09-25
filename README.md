# Fleet Management API

This project is a REST API for managing a fleet of taxis and their corresponding trajectories, built using Java, Spring Boot, Hibernate, and PostgreSQL. The API allows CRUD operations for taxis and provides endpoints to query the latest taxi trajectories. It includes unit tests and follows a layered architecture for structuring a Spring Boot project.

## Technologies Used
- **Java 21**
- **Spring Boot 3**
- **Hibernate (JPA)**
- **PostgreSQL**
- **JUnit5** for unit testing
- **Mockito** for mocking in tests
- **Postman/Newman** for API testing

---

## Getting Started

### Project Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/fleet-management-api.git
   cd fleet-management-api
   ```

2. **Run the project**:
   Use Maven to start the application.
   ```bash
   mvn spring-boot:run
   ```

5. **Access the API**:
   The API will be available at `http://localhost:8080`. You can start making requests to the endpoints defined in the API (e.g., `/taxis`, `/trajectories`).

---

## Running Tests

### Unit Tests
Run the unit tests using Maven:
```bash
mvn test
```
---

### Postman/Newman Tests

1. **Install Newman**:
   If you haven't installed Newman yet, you can do so globally:
   ```bash
   npm install -g newman
   ```

2. **Run Postman Tests**:
   Use the following command to run the Postman tests through Newman:
   ```bash
   newman run postman/collection.json -e postman/environment.json
   ```

---

## Future Development
- Optimized Response Times via indexing database.
- Increase data source for the API.

---
