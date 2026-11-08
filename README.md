
# Student Registration and Management System

## 1. Project Description

The Student Registration and Management System is a web application developed using Java, Spring Boot, Spring MVC, Thymeleaf, JDBC, and PostgreSQL.

The application allows users to:

- Register new students through an HTML form.
- Store student information in a PostgreSQL database.
- View all registered students.
- Search for a specific student using their ID.
- Validate student information before registration.

The system demonstrates Spring Beans, Inversion of Control (IoC), Dependency Injection (DI), Spring MVC, and JDBC database operations.

## 2. Technologies Used

- Java
- Spring Boot
- Spring MVC
- Thymeleaf
- PostgreSQL
- JDBC
- HTML5
- CSS3
- Maven
- IntelliJ IDEA
- Git and GitHub

## 3. Project Structure

```text
student-management
│
├── src
│   └── main
│       ├── java
│       │   └── com.example.studentmanagement
│       │       ├── StudentManagementApplication.java
│       │       │
│       │       ├── model
│       │       │   └── Student.java
│       │       │
│       │       ├── controller
│       │       │   └── StudentController.java
│       │       │
│       │       ├── service
│       │       │   └── StudentService.java
│       │       │
│       │       ├── repository
│       │       │   └── StudentRepository.java
│       │       │
│       │       └── config
│       │           └── DatabaseConfig.java
│       │
│       └── resources
│           ├── templates
│           │   ├── register.html
│           │   └── students.html
│           │
│           ├── static
│           │   └── css
│           │       └── style.css
│           │
│           └── application.properties
│
├── screenshots
│   ├── registration.png
│   ├── student-list.png
│   └── database-records.png
│
├── .gitignore
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## 4. Database Design and Configuration

The application uses PostgreSQL as its database management system.

### Database Creation

Create a database named:

```sql
CREATE DATABASE student_management;
```

Connect to the student_management database and create the students table:

```sql
CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(150) NOT NULL,
    age INTEGER CHECK (age > 18),
    email VARCHAR(150) UNIQUE NOT NULL
);
```

### Database Columns

| Column | Data Type | Description |
|---|---|---|
| id | SERIAL | Primary key, automatically generated |
| full_name | VARCHAR(150) | Stores the student's full name |
| age | INTEGER | Stores student age, greater than 18 |
| email | VARCHAR(150) | Unique student email |

The registration form collects first name and last name separately. Before inserting the record into PostgreSQL, both names are combined into one full_name value.

### Database Connection

The application connects to PostgreSQL using Spring Boot's DataSource configuration.

The database settings are stored in application.properties:

```properties
spring.application.name=student-management

spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5433/student_management}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD}

spring.datasource.driver-class-name=org.postgresql.Driver

spring.thymeleaf.cache=false
```

Database credentials are provided through environment variables rather than being stored directly in the source code.

## 5. Application Architecture

The project follows a layered architecture.

### Model Layer

The Student class represents student information.

It contains:

- ID
- First name
- Last name
- Email
- Age

It includes constructors, getters, setters, and validation annotations.

### Controller Layer

StudentController handles HTTP requests from the browser.

It communicates with the service layer and returns the appropriate Thymeleaf views.

### Service Layer

StudentService handles the application's business logic.

It communicates with StudentRepository through constructor-based dependency injection.

### Repository Layer

StudentRepository handles communication with the PostgreSQL database.

It uses JDBC components:

- Connection
- PreparedStatement
- ResultSet

It implements manually written SQL queries to insert and retrieve student records.

## 6. Spring IoC, Beans and Dependency Injection

### Inversion of Control (IoC)

Inversion of Control is a principle where Spring manages the creation and lifecycle of application objects instead of the programmer manually creating them.

Spring's IoC container creates and manages the application components as Spring Beans.

### Spring Beans

The project uses Spring annotations to register application classes as beans:

- `@Controller` registers StudentController.
- `@Service` registers StudentService.
- `@Repository` registers StudentRepository.

### Constructor-Based Dependency Injection

The application uses constructor-based dependency injection to connect the different layers.

StudentController receives StudentService through its constructor.

StudentService receives StudentRepository through its constructor.

Spring automatically creates these objects and injects their dependencies.

This avoids manually creating service and repository objects using the `new` keyword.

It also promotes loose coupling and makes the application easier to maintain.

## 7. Spring MVC Controller Handler Mappings

The application uses one StudentController to handle multiple HTTP requests.

The controller uses:

//```java
@RequestMapping("/student")
```

to define the common URL path.

### Implemented Endpoints

| HTTP Method | Endpoint | Expected Behaviour |
|---|---|---|
| GET | /student/register | Displays the registration form |
| POST | /student/register | Receives and saves student information |
| GET | /student/all | Displays all registered students |
| GET | /student/{id} | Displays a specific student by ID |

### Spring MVC Annotations

- `@Controller` defines the controller class.
- `@RequestMapping` defines the common URL.
- `@GetMapping` handles GET requests.
- `@PostMapping` handles POST requests.
- `@ModelAttribute` receives submitted form information.
- `@PathVariable` retrieves the student ID from the URL.
- `Model` passes information from the controller to Thymeleaf views.

The controller returns different HTML views depending on the requested endpoint.

## 8. Thymeleaf HTML Pages

The application contains two Thymeleaf HTML pages.

### Registration Page (register.html)

The registration page allows users to enter:

- First Name
- Last Name
- Email
- Age

It uses the following Thymeleaf attributes:

- `th:object` to bind the form to a Student object.
- `th:field` to bind individual form fields.
- `th:action` to specify the form submission URL.
- `th:errors` to display validation errors.

All fields are required. Email format is validated, and students must be older than 18.

A success message is displayed after successful registration.

### Student List Page (students.html)

The student list page displays registered students in an HTML table.

It uses `th:each` to dynamically iterate through student records retrieved from PostgreSQL.

The table displays:

- ID
- First Name
- Last Name
- Email
- Age

The page also displays a suitable message when no students are registered or when a requested student ID does not exist.

Both pages use responsive CSS styling.

## 9. JDBC Database Operations

The application uses manually written SQL queries through JDBC.

It does not use Spring Data JPA or Hibernate.

### INSERT Operation

The repository combines firstName and lastName into full_name before inserting the student into the database.

A PreparedStatement is used to execute the INSERT query safely.

### SELECT Operation

The repository uses a SELECT query to retrieve all registered students.

The retrieved records are converted into Student objects using ResultSet.

### Search Student by ID

The bonus feature uses the following SQL query:

//```sql
SELECT id, full_name, age, email
FROM students
WHERE id = ?;
```

PreparedStatement supplies the requested student ID.

Optional<Student> is used to handle situations where the requested student does not exist.

Database resources are managed using try-with-resources.

## 10. How to Run the Application

### Prerequisites

- Java JDK installed.
- IntelliJ IDEA installed.
- PostgreSQL installed and running.
- Maven.
- Git.

### Steps

1. Clone the GitHub repository.
2. Open the project in IntelliJ IDEA.
3. Create the student_management PostgreSQL database.
4. Create the students table using the SQL schema provided above.
5. Configure the database environment variables in IntelliJ Run Configurations.
6. Ensure PostgreSQL is running on port 5433.
7. Open StudentManagementApplication.java.
8. Run the Spring Boot application.
9. Open a web browser and access the application.

### Environment Variables

Configure the following variables in IntelliJ:

| Variable | Value |
|---|---|
| DB_URL | jdbc:postgresql://localhost:5433/student_management |
| DB_USERNAME | Your PostgreSQL username |
| DB_PASSWORD | Your PostgreSQL password |

Do not upload database passwords to GitHub.

### Application URLs

Registration page:

http://localhost:8080/student/register

All registered students:

http://localhost:8080/student/all

Search for a specific student:

http://localhost:8080/student/1

Replace 1 with an existing student ID.

## 11. Application Screenshots

### Student Registration Page

![Student Registration Page](screenshots/RegistrationForm.png)

### Registered Students Page

![Registered Students](screenshots/All Registered.png)

### PostgreSQL Database Records

![PostgreSQL Database Records](screenshots/Database.png)

## 12. Bonus Feature: Display Student by ID

The application implements the bonus requirement of displaying a specific student using their database ID.

The implementation uses:

- GET `/student/{id}`
- `@PathVariable`
- JDBC SELECT query with a WHERE condition.
- PreparedStatement.
- Optional<Student> to handle missing records.

The existing students.html template is reused to display the selected student or a suitable message when the student does not exist.

## 13. Conclusion

The Student Registration and Management System demonstrates the practical implementation of Spring Boot, Spring MVC, Thymeleaf, Spring Beans, Inversion of Control, Dependency Injection, PostgreSQL, and JDBC.

The application provides student registration, data validation, database persistence, dynamic student listing, and individual student searching.

It demonstrates how different Spring application layers work together to build a functional web application.
