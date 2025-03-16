[![Spring Boot CI](https://github.com/tetexxr/reservation-api/actions/workflows/pipeline.yml/badge.svg)](https://github.com/tetexxr/reservation-api/actions/workflows/pipeline.yml)

# Restaurant Reservation API

## Objective
Design and implement a backend API for a restaurant reservation system that manages tables and seating availability.

## API Design and Features

### Reservations
Build endpoints to create, update, cancel, and retrieve reservations.
- Each reservation should include fields like:
    - Date
    - Time
    - Customer details
    - Party size
- Assume each reservation has a duration of **45 minutes**.
- Maintain a list of tables, each with a maximum seating capacity (e.g., a table for 2, 4, 6, etc.).
    - This can be just a static list, no need to make it editable.
- Check table availability based on **party size and reservation time**, ensuring that reservations are allocated to specific tables.

### Availability Checker
Implement an endpoint to get all the slots available for a given day and party size.
- Should return a list of all the slots of **15 minutes available in the entire day**.

### Waitlist
If no tables are available, add the customer to a **waitlist**.
- Automatically promote waitlisted reservations if a slot becomes available.

### Send a Notification
Send a **notification 1 hour before** the reservation to the customer.
- Could be just a log in the console, no need to use a real SMS or push notification.

## Things We Value
- Tests
- Performance
- Code design

---

## Implementation

This project was created using [spring initializr](https://start.spring.io/).

### Test

In this project the tests are separated into two categories: unit tests and integration tests.  
Unit tests are located in the `src/test` package and integration tests are located in the `src/integrationTest` package.

To run the tests, execute the following command:
```bash
./gradlew test integrationTest
```

For a complete built and to force all the tasks to run, execute the following command:
```bash
./gradlew build --rerun-tasks
```

### Run

To run the project, execute the following command:
```bash
./gradlew bootRun
```

To use the API, access the following URL: http://localhost:8080/swagger-ui.html
