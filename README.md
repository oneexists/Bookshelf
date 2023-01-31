# Bookshelf Application

Add your books to your bookshelf, tracking when books were read, and collect all
your notes from each book. Provides a single location for keeping track of reading
and note taking.

### Links

- [Project Overview](https://oneexists.github.io/bookshelf-project)
- [Setup](./docs/setup.md)
- [Requirements Documentation](./docs/requirements.md)

### Technologies

#### Back-End

- [MySQL](https://www.mysql.com/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa),
  [Hibernate](https://hibernate.org/)
- [Spring Security](https://spring.io/projects/spring-security)
- [JJWT](https://github.com/jwtk/jjwt)
- [Spring Data REST](https://spring.io/projects/spring-data-rest)
- [JUnit Jupiter](https://junit.org/junit5/docs/current/user-guide/),
  [Mockito](https://site.mockito.org/)
- [Maven](https://maven.apache.org/),
  [Google Jib](https://cloud.google.com/java/getting-started/jib)

#### Front-End

- [HTML5](https://developer.mozilla.org/en-US/docs/Glossary/HTML5)
- [React](https://reactjs.org/)
- [Bootstrap 5](https://getbootstrap.com/)
- [CSS3](https://www.css3.info/)

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


- Add a [Spring Data REST Projection](https://docs.spring.io/spring-data/rest/docs/current/reference/html/#projections-excerpts.projections)
  to display author information within book query to simplify and reduce API
  queries
  ```
  "language": "English",
  "bookId": 1,
  "title": "War and Peace",
  "author": {
    "authorId": 1,
    "name": "Leo Tolstoy"
  },
  "pages": 1296
  ```


- Utilize React design patterns
  - Layout components
    - [Background](./react-ui/src/components/layouts/Background/index.js)
    - [ComponentList](./react-ui/src/components/layouts/ComponentList.js)
    - [SplitScreen](./react-ui/src/components/layouts/SplitScreen.js)
  - Custom hooks
    - [useDataSource](./react-ui/src/hooks/useDataSource.js)
    - [useInput](./react-ui/src/hooks/useInput.js)
  - Higher Order Components
    - [withAuthor](./react-ui/src/features/hoc/withAuthor.js)
    - [withBooks](./react-ui/src/features/hoc/withBooks.js)


- Add styling using React `styled-components` and CSS Modules
- Parse reading log dates from JSON string values in JavaScript for date formatting
  - date appeared to be one day off due to time zone resolution while creating a new
    Date in JavaScript
  - used a regular expression as suggested
    [here](https://stackoverflow.com/questions/7556591/is-the-javascript-date-object-always-one-day-off)
    to convert dates from `YYYY-MM-DD` to `YYYY/MM/DD` before creating a new Date to
    for date formatting
  - example of parsing and formatting date:
    ```javascript
    new Date("2022-12-13".replace(/-/g, '/')).toLocaleDateString()
    ```


- Simplify `AppUserDetails` implementation by extending Spring Security `User`
  - [Reference](https://stackoverflow.com/questions/20349594/adding-additional-details-to-principal-object-stored-in-spring-security-context)


- Create Docker [build script](./run.sh)


- Remove arrow function from delete button to prevent excess re-rendering
  - [Updated delete button](./react-ui/src/features/Bookshelf/components/BookView/BookViewButtonBar.js)
    ```javascript
    const DeleteButton = memo(({ onClick }) => (
        <DangerButton text="Delete Book" handleClick={onClick} />
    ));
    ```
    ```javascript
    const handleDelete = useCallback(() => {
        deleteBookById(bookId).then(navigate("/"));
    }, [bookId, navigate]);
    ```
  - [Function Components with inner callbacks](https://stackoverflow.com/questions/36677733/why-shouldnt-jsx-props-use-arrow-functions-or-bind)

## Target Process

- Provide reading statistics (yearly and overall)
  - total books and pages read
  - books and pages read by language
- Add quotes to books
- Add notes to books
  - optionally tag note with topics

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
- Docker:
  - [Introduction to Docker for Java Developers](https://www.linkedin.com/learning/introduction-to-docker-for-java-developers)
- JUnit:
  - [Java: Testing with JUnit](https://www.linkedin.com/learning/java-testing-with-junit-14267963)