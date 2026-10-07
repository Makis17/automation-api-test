# Bookstore REST API Automation

API automation testing framework for the FakeRestAPI Online Bookstore.

The project was developed as part of an API Automation Testing Assessment and demonstrates automated REST API testing using Java, REST Assured, JUnit 5, Maven, Extent Reports, and GitHub Actions.

The main test coverage focuses on the **Books API**, while the **Authors API** is also implemented as a bonus extension.

## Tech Stack

- Java 17
- Maven
- JUnit 5.11.4
- REST Assured 5.5.1
- Jackson 2.18.2
- Extent Reports 5.1.2
- Maven Surefire 3.5.2
- GitHub Actions

## API Under Test

The automated tests run against FakeRestAPI:

`https://fakerestapi.azurewebsites.net`

The default base URL is configured in `pom.xml` and passed to the tests as a Java system property through Maven Surefire.

A different environment can be used by overriding the property:

```
mvn clean test -DbaseUrl=https://example-api-url.com
```

## Project Structure

```
automation-api-test/
├── .github/
│   └── workflows/
│       └── api-tests.yml
├── src/
│   └── test/
│       └── java/
│           └── com/example/bookstore/
│               ├── client/
│               ├── config/
│               ├── data/
│               ├── tests/
│               └── util/
├── test-report/
│   └── extent-report.html
├── pom.xml
└── README.md
```

The framework separates API communication, configuration, data models, reusable test data, test scenarios, assertions, and reporting to improve maintainability and reusability.

## API Coverage

### Books API

The following endpoints are covered:

- `GET /api/v1/Books`
- `GET /api/v1/Books/{id}`
- `POST /api/v1/Books`
- `PUT /api/v1/Books/{id}`
- `DELETE /api/v1/Books/{id}`

The Books test suite covers both happy-path and edge/negative scenarios, including:

- Retrieving all books
- Retrieving a book by ID
- Creating books
- Updating books
- Deleting books
- Response data validation
- Nullable field handling
- Invalid data types
- Invalid date-time values
- Integer boundary/overflow scenarios
- Malformed JSON payloads
- Unknown request fields
- Non-existing resources
- POST persistence verification
- PUT persistence verification
- DELETE state verification

### Authors API

The following endpoints are covered:

- `GET /api/v1/Authors`
- `GET /api/v1/Authors/{id}`
- `POST /api/v1/Authors`
- `PUT /api/v1/Authors/{id}`
- `DELETE /api/v1/Authors/{id}`

The Authors test suite also includes happy-path and edge/negative scenarios.

## Prerequisites

The following software is required to run the project locally:

- Java 17
- Maven
- Git

Verify the installations with:

```
java -version
mvn -version
git --version
```

## Setup

Clone the repository:

```
git clone https://github.com/Makis17/automation-api-test.git
```

Navigate to the project directory:

```
cd automation-api-test
```

Maven will automatically download the required dependencies defined in `pom.xml`.

## Running the Tests

Run the complete automated test suite with:

```
mvn clean test
```

This executes both the Books API tests and the Authors API bonus tests.

To run the tests against another base URL:

```
mvn clean test -DbaseUrl=https://example-api-url.com
```

## Framework Design

The framework uses a layered structure to keep responsibilities separated.

### API Clients

Client classes encapsulate HTTP operations for each API resource, keeping REST Assured request execution separate from test scenarios.

### Data Models

Java records are used to represent API request and response objects, including `Book` and `Author`.

### Test Data

Dedicated test data classes provide reusable test objects and randomized values where appropriate, reducing duplication and unnecessary hard-coded data.

### Tests

JUnit 5 test classes contain the individual API scenarios and assertions. Common setup is shared through the base test configuration.

### Utilities

Reusable utilities provide common functionality such as response assertions, random data generation, configuration, and Extent reporting.

## Test Strategy

The test suite covers successful API operations as well as edge and negative scenarios.

Happy-path tests validate successful HTTP responses and returned data.

Negative and edge-case scenarios cover cases such as:

- Invalid data types
- Integer values outside supported ranges
- Malformed JSON payloads
- Unknown request fields
- Invalid date-time values
- Non-existing resource IDs
- Nullable fields where supported by the API schema

Where the assessment or provided API specification does not explicitly define error responses for negative scenarios, expected behavior is based on common REST API conventions.

For example:

- Invalid or malformed requests are expected to return `400 Bad Request`.
- Requests targeting non-existing resources are expected to return `404 Not Found`.

These are deliberate test-design expectations and are not presented as error responses explicitly guaranteed by the provided FakeRestAPI specification.

If FakeRestAPI behaves differently, the failed assertion is retained and reported rather than changing the expectation solely to make the test pass.

## Persistence Verification

POST, PUT, and DELETE operations include additional state-verification scenarios.

Examples include:

- After creating a resource, a subsequent GET verifies whether the created resource can be retrieved.
- After updating a resource, a subsequent GET verifies whether the updated state was persisted.
- After deleting a resource, a subsequent GET verifies that the deleted resource is no longer retrievable.

FakeRestAPI is a demo API and does not always behave like a stateful production API.

As a result, persistence verification scenarios may fail even when the initial POST, PUT, or DELETE request returns a successful response.

These failures are intentionally retained as test findings.

## Observed API Behavior

During test execution, several scenarios expose differences between the production-like REST expectations used by the test suite and the actual behavior of FakeRestAPI.

Observed examples include:

- Created resources may not be persisted and therefore cannot be retrieved with a subsequent GET.
- PUT requests may return updated data while a subsequent GET returns the original resource.
- DELETE requests may return success without making the resource unavailable through a subsequent GET.
- PUT and DELETE operations against non-existing resources may return `200 OK` instead of the expected `404 Not Found`.
- Requests containing unknown fields may return `200 OK` instead of the expected `400 Bad Request`.

These failures are intentionally retained in the test report as observed API behavior.

## Test Reports

Test execution reporting is provided through both **Extent Reports** and **Maven Surefire**.

A generated Extent HTML report is included in the repository:

```
test-report/extent-report.html
```

Open the file in a web browser to review the detailed test execution results.

Maven Surefire also generates JUnit-compatible results under:

```
target/surefire-reports/
```

The `target` directory is generated during execution and is intentionally excluded from version control.

## CI/CD

Continuous integration is implemented using GitHub Actions.

The workflow runs on:

- Pushes to the `main` or `master` branch
- Pull requests
- Manual execution through `workflow_dispatch`

The CI environment uses Ubuntu and Java 17 with the Temurin distribution.

Tests are executed with:

```
mvn -B clean test
```

After execution, GitHub Actions uploads the following reports as an artifact:

```
target/surefire-reports/
test-report/extent-report.html
```

The artifact is named:

```
bookstore-api-test-report
```

Report upload uses `if: always()`, ensuring that test results remain available even when one or more tests fail.

JUnit XML results generated by Maven Surefire are also published directly in GitHub Actions for easier review of passed and failed test cases.

## Current Test Execution

The latest local execution completed with:

```
Tests run: 49
Failures: 12
Errors: 0
Skipped: 0
```

The reported failures are primarily related to persistence behavior, handling of non-existing resources, and acceptance of unknown fields by FakeRestAPI.

The test framework itself completes execution without runtime or configuration errors.

## Assessment Deliverables

This repository contains:

- Complete API automation source code
- Books API automated test coverage
- Authors API bonus test coverage
- Happy-path and edge/negative test scenarios
- Reusable API clients and test data
- Maven project configuration
- Generated Extent HTML test report
- Maven Surefire execution results
- GitHub Actions CI/CD configuration
- CI report artifacts
- Setup and execution documentation