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
- [React](https://reactjs.org/)
- [Bootstrap 5](https://getbootstrap.com/)

### Functional Requirements

- RESTful API with JWT web token authentication

### Non-Functional Requirements

- UI/UX
  - React front-end interface
  - Bootstrap styling
  - integrate [WAI-ARIA specification](https://developer.mozilla.org/en-US/docs/Learn/Accessibility/WAI-ARIA_basics)
    to increase accessibility

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

## Process Analysis

- Handle Authentication and Authorization Exceptions:
  - [Exception Handling in Spring Security](https://www.devglan.com/spring-security/exception-handling-in-spring-security):
  Add JwtAccessDeniedHandler to configuration and HttpResponse to security package
  - [Authentication Entry Point](https://stackoverflow.com/questions/37080590/spring-controlleradvice-and-authentication-authorization-exception-handling)
  and [Authentication Exception](https://stackoverflow.com/questions/19767267/handle-spring-security-authentication-exceptions-with-exceptionhandler):
  Clear context holder and set status to forbidden in AuthenticationController, add JwtAuthenticationEntryPoint and register with security configuration


- Create authentication hook:
  - [useHooks hook for useAuth](https://usehooks.com/useAuth/)
  - [useAuth hook example](https://hhpendleton.medium.com/useauth-265512bbde3c)


## Resources

- Spring Data REST:
  - [Tutorial](https://spring.io/guides/tutorials/rest/)
  - [Validators](https://www.baeldung.com/spring-data-rest-validators)
  - [Validator Testing](https://stackoverflow.com/questions/9744988/writing-junit-tests-for-spring-validator-implementation)
  - [CORS Configuration](https://www.amitph.com/spring-data-rest-cors/)
- React:
  - [React Router](https://reactrouter.com/en/main)
  - [React styled-components](https://styled-components.com/docs)
  - LinkedIn Learning Courses:
    - [React: Building Styles with CSS Modules](https://www.linkedin.com/learning/react-building-styles-with-css-modules-9222678/building-a-react-site-with-css-modules)
    - [React.js Essential Training](https://www.linkedin.com/learning/react-js-essential-training-14836121/building-modern-user-interfaces-with-react)
    - [React: Accessibility](https://www.linkedin.com/learning/react-accessibility/accessibility-in-react)
    - [React: Design Patterns](https://www.linkedin.com/learning/react-design-patterns/take-your-react-skills-to-the-next-level)
    - [React Hooks](https://www.linkedin.com/learning/react-hooks/understanding-modern-react)
    - [Building Modern UIs with React Router v6](https://www.linkedin.com/learning/building-modern-uis-with-react-router-v6/building-modern-uis)
