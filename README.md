# Alumni LinkedIn Profile Searcher

A Java 17 and Spring Boot REST API for searching alumni profiles by university, current designation, and optional pass-out year. PostgreSQL stores alumni profiles, while Swagger UI provides interactive API documentation and testing.

## Project Links

- **Live Application:** https://alumni-linkedin-profile-searcher.onrender.com/
- **Swagger UI / API Documentation:** https://alumni-linkedin-profile-searcher.onrender.com/swagger-ui/index.html
- **GitHub Repository:** https://github.com/Maheshbabu14344p94/Alumni-linkedin-profile-searcher.git

## Features

- Search alumni by university and current designation.
- Optionally filter by pass-out year.
- Case-insensitive, partial-match searching.
- Retrieve all saved alumni profiles.
- Store profile data in PostgreSQL.
- Validate API request inputs.
- Return structured JSON responses.
- Explore and test endpoints using Swagger UI.
- Support Docker and Docker Compose.
- Include automated tests using JUnit 5, MockMvc, and Mockito.

## Technology Stack

| Category | Technology |
|---|---|
| Language | Java 17 |
| Backend | Spring Boot 3.4.5 |
| Database | PostgreSQL |
| Persistence | Spring Data JPA, Hibernate |
| API Documentation | Swagger UI, OpenAPI |
| Testing | JUnit 5, MockMvc, Mockito |
| Build Tool | Maven |
| Containerization | Docker, Docker Compose |

## Live API Endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/` | Application status |
| POST | `/api/alumni/search` | Search alumni profiles |
| GET | `/api/alumni/all` | Retrieve all saved profiles |

## Deployment

The application is deployed on Render and uses a hosted PostgreSQL database.

- **Application:** https://alumni-linkedin-profile-searcher.onrender.com/
- **API Documentation:** https://alumni-linkedin-profile-searcher.onrender.com/swagger-ui/index.html
- **Source Code:** https://github.com/Maheshbabu14344p94/Alumni-linkedin-profile-searcher.git

## Testing

The application was tested successfully with Maven:

- Tests run: 2
- Failures: 0
- Errors: 0

The deployed search endpoint was also tested successfully using Swagger UI.

## Important Notes

- This is a REST API project; it does not include a separate frontend.
- Search results depend on profiles stored in PostgreSQL.
- The sample alumni profiles and LinkedIn URLs used for testing are fictional placeholders.
- Configure credentials and API keys using environment variables. Never commit secrets to GitHub.

## License

Add the license required by your assignment or repository policy, if applicable.