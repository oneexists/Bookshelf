# Project Setup

1. The backend API (Spring Boot and MySQL) can be set up following the general
   instructions given in my
   [Docker Containerization guide](https://oneexists.github.io/containerize-api/)
   - if interested in running tests, a test database schema can be found
     [here](../sql/schema.sql).
2. The environment variables needed to configure the Spring Boot application's
   connection to the database are:
    - DB_URL
    - DB_USERNAME
    - DB_PASSWORD
3. To use frontend, follow [React instructions](../react-ui/README.md) to create
  the local environment and install npm if needed
