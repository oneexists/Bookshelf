# Bookshelf Application

Spring web application for tracking personal reading habits

### Technologies

- [Spring Data JPA](https://spring.io/projects/spring-data-jpa),
  [Hibernate](https://hibernate.org/)
- [Spring Data REST](https://spring.io/projects/spring-data-rest)
- [Spring Security](https://spring.io/projects/spring-security)
- [MySQL](https://www.mysql.com/)
- [JUnit Jupiter](https://junit.org/junit5/docs/current/user-guide/),
  [Mockito](https://site.mockito.org/)

### Functional Requirements

- RESTful API with JWT web token authentication

## Project Setup

### MySQL:

To run tests, a schema can be found in the directory: bookshelf/sql/schema.sql

### Running Application:

1. [Install MySQL](https://dev.mysql.com/downloads/workbench/) and
    [Java 17](https://www.oracle.com/java/technologies/downloads/#java17)
2. [Configure Environment Variables](https://www.codejava.net/java-core/how-to-set-environment-variables-for-java-using-command-line)
    - DB_URL
    - DB_USERNAME
    - DB_PASSWORD
3. [Create a JAR](https://docs.oracle.com/javase/tutorial/deployment/jar/build.htm)

## Resources

- [Exception Handling in Spring Security](https://www.devglan.com/spring-security/exception-handling-in-spring-security): Add JwtAccessDeniedHandler to configuration and HttpResponse to security package
