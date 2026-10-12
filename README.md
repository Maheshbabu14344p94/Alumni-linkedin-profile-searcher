# Alumni LinkedIn Profile Searcher

A Java 17 and Spring Boot REST API for searching alumni profiles by university, current designation, and optional pass-out year. PostgreSQL stores alumni profiles, while Swagger UI provides interactive API documentation and testing.

> **Assignment scope:** The supplied assignment brief explicitly specifies “No UI” and requires REST APIs testable through Postman. This project is API-only; no frontend is included.

## Features

- Search alumni by university and current designation.
- Optionally filter results by pass-out year.
- Case-insensitive, partial-match searching.
- Retrieve all saved alumni profiles.
- Persist profile data in PostgreSQL.
- Validate API request inputs.
- Return structured JSON success and error responses.
- Explore and test endpoints using Swagger UI.
- Support Docker Compose for containerized execution.
- Include automated tests using JUnit 5, MockMvc, and Mockito.
- Provide optional PhantomBuster agent-launch integration through environment configuration.

## Technology Stack

| Category | Technology |
|---|---|
| Language | Java 17 |
| Backend Framework | Spring Boot 3.4.5 |
| REST API | Spring Web |
| Persistence | Spring Data JPA, Hibernate |
| Database | PostgreSQL 16 |
| Validation | Jakarta Bean Validation |
| API Documentation | springdoc-openapi, Swagger UI |
| Testing | JUnit 5, MockMvc, Mockito |
| Build Tool | Apache Maven |
| Containerization | Docker, Docker Compose |

## Prerequisites

Install the following before running the application:

- JDK 17 or later
- Apache Maven
- Docker Desktop with Docker Compose, or a local PostgreSQL 16 installation
- Postman or a web browser for API testing

Verify your installations:

```bash
java -version
mvn -version
docker --version
docker compose version
```

## Project Structure

```text
alumni-linkedin-profile-searcher/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/assignment/alumni/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── AlumniSearcherApplication.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       └── java/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── .env.example
└── README.md
```

*The tree illustrates the intended organization. Keep only directories and files that actually exist in your project.*

## Configuration

The application reads database configuration from environment variables, with local development defaults defined in `application.yml`.

| Variable | Default |
|---|---|
| `PORT` | `8080` |
| `DATABASE_URL` | `jdbc:postgresql://localhost:5432/alumni_db` |
| `DATABASE_USERNAME` | `alumni` |
| `DATABASE_PASSWORD` | `alumni_dev_password` |
| `PHANTOMBUSTER_API_KEY` | Empty |
| `PHANTOMBUSTER_AGENT_ID` | Empty |
| `PHANTOMBUSTER_LAUNCH_URL` | `https://api.phantombuster.com/api/v2/agents/launch` |

For local development, the default database settings can be used when PostgreSQL is configured accordingly.

For Docker Compose, the database URL must use the PostgreSQL service name and container port, rather than `localhost` from inside the API container.

**Security:** The default credentials are for local development only. Override them in other environments. Never commit real passwords, API keys, or other secrets to version control.

## Running Locally

### 1. Start PostgreSQL

From the project root:

```bash
docker compose up -d postgres
```

This starts the PostgreSQL service defined in the Compose configuration.

### 2. Start the Spring Boot application

```bash
mvn spring-boot:run
```

The default application port is `8080`, unless overridden by the `PORT` environment variable or another runtime configuration.

### 3. Open API documentation

Swagger UI:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

OpenAPI JSON:

[http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

Use Swagger UI to explore endpoints, submit requests, and inspect responses.

## Running with Docker Compose

To build and start the complete stack:

```bash
docker compose up --build -d
```

Check running containers:

```bash
docker compose ps
```

View application logs:

```bash
docker compose logs -f app
```

If the application service has a different name in your Compose configuration, replace `app` with the actual service name.

Stop the stack:

```bash
docker compose down
```

To remove the database volume as well, use the appropriate volume option only when you intend to delete the stored database data.

Once the containers are running, open:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## API Endpoints

### 1. Search Alumni

**Endpoint:** `POST /api/alumni/search`

**Description:** Searches saved alumni profiles by university and current designation, with an optional pass-out year filter.

**Request body:**

```json
{
  "university": "University of Example",
  "designation": "Software Engineer",
  "passoutYear": 2021
}
```

**Field descriptions:**

| Field | Type | Required | Description |
|---|---|---|---|
| `university` | String | Yes | University name or partial university name |
| `designation` | String | Yes | Current designation or partial designation |
| `passoutYear` | Integer | No | Graduation/pass-out year |

The pass-out year can be omitted or set to `null` to search across all years, provided the request validation and controller accept an absent year.

**Example success response:**

```json
{
  "status": "success",
  "data": [
    {
      "id": 1,
      "name": "Alex Kim",
      "currentRole": "Software Engineer",
      "university": "University of Example",
      "location": "Remote",
      "linkedinHeadline": "Software engineer focused on backend systems",
      "passoutYear": 2021,
      "linkedinUrl": "https://www.linkedin.com/in/example"
    }
  ]
}
```

The example above is illustrative. Actual results depend on the records stored in PostgreSQL.

**No matching results:**

```json
{
  "status": "success",
  "data": []
}
```

A successful search with no matches returns HTTP `200 OK` and an empty data array.

### 2. Retrieve All Alumni Profiles

**Endpoint:** `GET /api/alumni/all`

**Description:** Retrieves all saved alumni profiles from the database.

**Example request:**

```bash
curl http://localhost:8080/api/alumni/all
```

The response contains the saved alumni profiles in the API's configured response format.

## Error Handling

The API uses structured JSON responses for validation and server-side failures.

The intended HTTP status codes are:

| HTTP Status | Meaning |
|---|---|
| `200 OK` | Request succeeded |
| `400 Bad Request` | Invalid request or validation failure |
| `500 Internal Server Error` | Unexpected application error |
| `502 Bad Gateway` | Upstream integration failure |
| `503 Service Unavailable` | Database or service unavailable |

**Example validation error:**

```json
{
  "status": "error",
  "data": null,
  "message": "Validation failed",
  "errors": [
    {
      "field": "university",
      "message": "University is required"
    }
  ]
}
```

The exact status and response body depend on the application's exception-handling implementation. Confirm that each documented error mapping is implemented before treating it as verified behavior.

## PhantomBuster Integration

The application supports optional PhantomBuster agent-launch configuration.

Configure the following environment variables when using the integration:

```dotenv
PHANTOMBUSTER_API_KEY=your_api_key
PHANTOMBUSTER_AGENT_ID=your_agent_id
PHANTOMBUSTER_LAUNCH_URL=https://api.phantombuster.com/api/v2/agents/launch
```

For Docker Compose, configure these values through your environment or local `.env` file, using `.env.example` as a template when provided.

Do not commit `.env` or real credentials to Git.

**Integration limitation:** Launching a configured PhantomBuster agent does not necessarily retrieve its final output synchronously. This project searches alumni profiles already persisted in PostgreSQL. Automatically importing newly scraped results requires mapping the actual agent's output format and result-retrieval workflow to the `AlumniProfile` entity.

No API key was supplied with the assignment, so live scraping has not been verified.

## Database and Persistence

PostgreSQL stores alumni profiles in the `alumni_profiles` table.

The entity includes fields such as:

- `id`
- `name`
- `currentRole` (stored in the `current_designation` database column)
- `university`
- `location`
- `linkedinHeadline`
- `passoutYear`
- `linkedinUrl`
- `createdAt`

Hibernate schema generation is configured with `ddl-auto: update` for this assignment. Production systems should generally use versioned database migrations.

No fake alumni records are seeded automatically. Search results depend on profiles already present in the database, whether added through a controlled import or another implemented ingestion workflow.

## Running Tests

Run the automated tests:

```bash
mvn test
```

Run a clean build and install:

```bash
mvn clean install
```

If Maven requires a custom settings file in your environment, supply it with the `-s` option.

The controller test suite was executed successfully during development, with **2 tests passing, 0 failures, and 0 errors**. This result describes the test run performed and does not imply complete test coverage.

## Testing with Postman

To test the API using Postman:

1. Start the API and PostgreSQL database.
2. Create a `POST` request to `http://localhost:8080/api/alumni/search`.
3. Set the `Content-Type` header to `application/json`.
4. Select **Body → raw → JSON**.
5. Enter a search request and click **Send**.
6. Verify the HTTP status and returned alumni records.

Example:

```json
{
  "university": "JNTU Hyderabad",
  "designation": "Software Engineer",
  "passoutYear": 2023
}
```

## Deployment

The application can be deployed to a Java-compatible hosting platform with access to a managed PostgreSQL database.

Before deployment:

1. Configure the database connection and credentials securely.
2. Configure any required PhantomBuster secrets.
3. Build and test the application.
4. Deploy the application and verify its health and API endpoints.
5. Add the actual public deployment URL to the submission.
6. Record the required Loom demonstration and add its actual URL.

A public deployment URL and Loom demo URL are intentionally not included here because they must be generated from a real deployment and recording.

## Limitations

- The assignment is API-only; no frontend UI is included.
- Searches operate on profiles stored in PostgreSQL.
- Live PhantomBuster scraping and result ingestion have not been verified without valid credentials and an actual agent configuration.
- Sample responses in this README are illustrative unless explicitly identified as observed test results.
- Database schema updates are configured for assignment development rather than production migration management.

## License

Add the license required by your assignment or repository policy, if applicable.
