# energy-viridis-exercise

### Requirements
* Java 8
* Git
* Maven
* Docker
### Technologies and Frameworks used
* Java 8
* Git
* Maven
* Spring Boot
* Postgre SQL
* Docker
* Swagger

### Running the database

    $ docker pull postgres

    $ docker run --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres
  

### Compiling the project and installing dependencies
Run the following command on the root folder.

    $ mvn clean install

### Running the project
Run the following command on the root folder

    $ mvn spring-boot:run

The project will be executed at the link: http://localhost:8080

### Running tests
Run the following command on the root folder

    $ mvn test

### API Documentation
API documentation can be accessed at the link: http://localhost:8080/swagger-ui.html

### Authentication Endpoint
POST request for link http://localhost:8080/api/login
{"username":"admin","password":"123"}

