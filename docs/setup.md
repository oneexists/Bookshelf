# Project Setup

## Docker Containerization Configuration

### 1. Initialize MySQL database

  - create Docker volumes:
    ```bash
    docker volume create mysql_data
    docker volume create mysql_config
    ```
  - create Docker network:
    ```bash
    docker network create mysqlnet
    ```
  - create MySQL container, replacing `password` with database password of
    choice:
    ```bash
    docker run -it -d -v mysql_data:/var/lib/mysql -v mysql_config:/etc/mysql/conf.d --network mysqlnet --name mysqlserver -e MYSQL_ROOT_PASSWORD=password -p 3306:3306 mysql
    ```
  - sign into MySQL and execute command to create the database:
    ```
    create database bujo;
    ```
    - if interested in running tests, a schema for the test database can be
      found in `sql/schema.sql` that can be added to initialize the testing
      database

### 2. Environment setup

  - to install the JDK, execute the following commands:
  ```bash
  sudo apt update
  sudo apt install openjdk-17-jdk
  sudo update-alternatives --config java
  ```
  - the response should be:
    ```
    There is only one alternative in link group java (providing /usr/bin/java): /usr/lib/jvm/java-17-openjdk-amd64/bin/java
    Nothing to configure.
    ```
  - add the following Java and application configuration to the environment,
    ignoring the first line if adding to `~/.bashrc` or if `~/.bash_profile`
    already exists
    - create a file: `~/.bash_profile` (can be done in the terminal using
      `nano ~/.bash_profile`, copying and pasting or using a Windows system may
      create invalid line breaks)
    - replace `password` with the password used to create the database
      ```bash
      source ~/.bashrc

      # JDK 17 CONFIGURATION
      export PATH="/usr/lib/jvm/java-17-openjdk-amd64/bin/java:$PATH"
      export JAVA_HOME="/usr/lib/jvm/java-17-openjdk-amd64"

      # BOOKSHELF API CONFIGURATION
      export DB_URL=jdbc:mysql://localhost:3306/containers
      export DB_USERNAME=root
      export DB_PASSWORD=password
      export ALLOWED_ORIGINS="http://localhost:3000"
      ```

### 3. Running the application

  - navigate to the `scripts` directory
  - to run the first time: `./run.sh -ur`
    - creates API image using `-u` option
    - starts React front-end using `-r` option
  - starting the API: `./run.sh`
  - starting the API and React front-end: `./run.sh -r`
    - further information for using React can be found [here](../react-ui/README.md)
  - help menu with usage instructions: `./run.sh -h`
