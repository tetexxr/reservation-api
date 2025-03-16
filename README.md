# Restaurant Reservation API

This project was created using [spring initializr](https://start.spring.io/).

## Test

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

## Run

To run the project, execute the following command:
```bash
./gradlew bootRun
```

To use the API, access the following URL: http://localhost:8080/swagger-ui.html
