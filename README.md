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

## Decisions and possible improvements

- In the specification, it was not clear how the tables should be allocated to the reservations. I decided to implement a simple algorithm that allocates the reservation to the table with the smallest capacity that fits the party size.
- The opening hours of the restaurant are not defined in the specification. I decided to consider that the restaurant is open from 8:00 to 14:00 (to avoid too much results in the availability checker). but this should be changed to a more realistic scenario.
- And related to the previous point, the opening hours should be considered when creating and updating a reservation, to avoid creating reservations outside the opening hours.
- When a reservation that is in the waitlist is promoted, some notification should be sent to the customer. This feature was not implemented and not defined in the specification.
- The repositories are using static lists to store the data. This should be changed to a database.
- When getting the availability of slots, is not specified if the slots should take care of the reservations that are in the wait list. I decided to consider only the reservations that are assigned to a table.
- In multiple places, I'm getting all the items from a list and filtering them. This could be improved by using a database query getting only the necessary data.
- Use some cache mechanism to store the most used data can also be a good improvement.
- Error handling should be put in place to handle exceptions and return a proper response to the user.