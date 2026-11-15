# Medicine Donation & Expiry Tracking System

Spring Boot + H2 demo database + HTML/CSS/JavaScript frontend.

## Run locally
Requirements: Java 21 and Maven (or use the included Maven wrapper if added).
1. Open this folder in IntelliJ IDEA or VS Code with Java extensions.
2. Run `mvn spring-boot:run`.
3. Open http://localhost:8080
4. H2 console: http://localhost:8080/h2-console
   JDBC URL: `jdbc:h2:file:./data/medicinedb`, username `sa`, blank password.

The app inserts fictional sample data on first startup.

## GitHub
Upload the contents of this folder to your GitHub repository. GitHub stores source code; it does not execute Spring Boot or host the H2/MySQL database. To make the app publicly accessible, deploy the backend to a Java hosting service and configure a hosted database.

## Optional MySQL profile
Set environment variables DB_URL, DB_USERNAME, DB_PASSWORD, then run with:
`mvn spring-boot:run -Dspring-boot.run.profiles=mysql`

## API endpoints
- GET /api/dashboard
- GET /api/medicines?search=ORS
- POST /api/medicines
- DELETE /api/medicines/{id}
- GET /api/requests
- POST /api/requests
- PUT /api/requests/{id}/approve
- PUT /api/requests/{id}/reject

## Safety
All seed data is fictional and for classroom software demonstration only. The application tracks expiry dates but does not verify medicine authenticity, packaging, storage history, recalls, prescription status, clinical suitability, or legal eligibility. It must not be used to authorize real medicine redistribution.
