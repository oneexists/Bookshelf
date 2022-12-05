# Bookshelf Application

Spring RESTful web application with React UI for tracking personal reading habits.

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
  - Login/Logout and account registration
  - Username length between 3 and 100 characters
  - Password validation: 8 character length with at least one letter, one digit and one special character

### Non-Functional Requirements

#### User Interface

- React front-end interface
- Bootstrap/CSS module styling

#### User Experience

- Integrate [WAI-ARIA specification](https://developer.mozilla.org/en-US/docs/Learn/Accessibility/WAI-ARIA_basics)
  to increase accessibility
  - Allows for screen reader and keyboard navigation
  - Semantic HTML elements
  - Assertive error display on forms to notify screen readers
  - Descriptions of form field requirements

## Project Setup

### MySQL:

To run tests, a schema can be found in the directory: bookshelf/sql/schema.sql

### Running Application:

1. The back end (Spring Boot and MySQL) can be set up following the general
   instructions given in my
   [Docker Containerization guide](https://oneexists.github.io/containerize-api/)
2. The environment variables needed to configure the Spring Boot application's
   connection to the database are:
    - DB_URL
    - DB_USERNAME
    - DB_PASSWORD
3. To use front end, follow [React instructions](/react-ui/README.md) to install and set environment

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
  - [Reference Guide](https://docs.spring.io/spring-data/rest/docs/current/reference/html/)
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
