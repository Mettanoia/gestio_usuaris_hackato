# User Management Application

This project is a user and task management application built with Spring Boot and R2DBC (Reactive Relational Database Connectivity). It allows you to perform CRUD operations on users and tasks, as well as enroll users in tasks.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Database Setup](#database-setup)
- [Running the Application](#running-the-application)
- [API Usage](#api-usage)
  - [User Endpoints](#user-endpoints)
  - [Task Endpoints](#task-endpoints)

## Prerequisites

- **Java 11** or higher
- **Maven** installed on your system
- An HTTP client like **Postman** or **cURL** for testing the API

## Database Setup

This application uses an embedded H2 database configured with R2DBC for reactive database access. Follow the steps below to set up the database schema using the provided `schema.sql` file.

1. **Place the `schema.sql` File**

   Ensure that the `schema.sql` file is located in the `src/main/resources` directory of the project. This file contains the SQL statements needed to create the necessary database schema.

2. **Verify Configuration**

   The application is configured using the `application.properties` file. Make sure it contains the following settings:

   ```properties
   spring.application.name=gestio_usuaris_hackato

   # Enable the H2 console for debugging (optional)
   spring.h2.console.enabled=true
   spring.h2.console.path=/h2-console

   # R2DBC H2 Configuration
   spring.r2dbc.url=r2dbc:h2:file:///./src/main/resources/test_db
   spring.r2dbc.username=sa
   spring.r2dbc.password=
   spring.r2dbc.pool.enabled=true

   # Optionally configure logging for debugging SQL queries
   logging.level.io.r2dbc=DEBUG
   logging.level.org.springframework.r2dbc=DEBUG
   ```

   - **spring.r2dbc.url**: Specifies the URL for the H2 database.
   - **spring.h2.console.enabled**: Enables the H2 console for debugging purposes.
   - **logging.level**: Sets the logging level for R2DBC to help debug SQL queries.

3. **Database Initialization**

   Use the `schema.sql` file to set up the database schema, the ApplicationRunner will fill it with some demo data after the first run.

## API Usage

The application exposes several RESTful endpoints for managing users and tasks. Below are examples of how to use these endpoints.

### User Endpoints

#### Create a New User

- **Endpoint**: `POST http://localhost:8080/appActivitats/user`
- **Headers**: `Content-Type: application/json`
- **Body**:

  ```json
  {
      "id": null,
      "name": "Miguel",
      "surname": "Lozano",
      "email": "miguelsunnata@gmail.com"
  }
  ```

#### Update an Existing User

- **Endpoint**: `PUT http://localhost:8080/appActivitats/users/{{id}}`
  - Replace `{{id}}` with the user's ID (e.g., `8`).
- **Headers**: `Content-Type: application/json`
- **Body**:

  ```json
  {
    "id": null,
    "name": "Miguel",
    "surname": "Lozano",
    "email": "lmpy@gmail.com"
  }
  ```

#### Delete a User

- **Endpoint**: `DELETE http://localhost:8080/appActivitats/users/{{id}}`
  - Replace `{{id}}` with the user's ID.

#### Retrieve a User by ID

- **Endpoint**: `GET http://localhost:8080/appActivitats/users/1`
  - Replace `1` with the user's ID.

### Task Endpoints

#### Create a New Task

- **Endpoint**: `POST http://localhost:8080/appActivitats/task`
- **Headers**: `Content-Type: application/json`
- **Body**:

  ```json
  {
      "id": null,
      "name": "Task Name",
      "description": "This is the task description.",
      "maxCapacity": 30
  }
  ```

#### Retrieve a Task by ID

- **Endpoint**: `GET http://localhost:8080/appActivitats/task/3`
  - Replace `3` with the task's ID.

#### Enroll a User in a Task

- **Endpoint**: `POST http://localhost:8080/appActivitats/task/3/enroll`
  - Replace `3` with the task's ID.
- **Headers**: `Content-Type: application/json`
- **Body**:

  ```json
  {
      "id": 18,
      "name": "Miguel",
      "surname": "Lozano",
      "email": "miguelsunnata@gmail.com"
  }
  ```

#### Retrieve All Tasks

- **Endpoint**: `GET http://localhost:8080/appActivitats/tasks`

#### Create Multiple Tasks

- **Endpoint**: `POST http://localhost:8080/appActivitats/tasks`
- **Headers**: `Content-Type: application/json`
- **Body**:

  ```json
  {
    "tasks": [
      {
        "id": null,
        "name": "Anti-patterns",
        "description": "Learn how to do things terribly wrong.",
        "maxCapacity": 30
      },
      {
        "id": null,
        "name": "Pythonic philosophy",
        "description": "Master the philosophy of the pythonistas.",
        "maxCapacity": 25
      },
      {
        "id": null,
        "name": "Task Name 2",
        "description": "This is the task description.",
        "maxCapacity": 30
      }
    ]
  }
  ```
