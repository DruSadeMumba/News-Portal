# NEWS PORTAL
#### A news portal rest-api, 1 May 2022.
#### By **Drucilla Mumba**
## Description
This is a news portal rest-api. A user can test the routes and add data to the database.
## Behaviour
* Routes can be tested on postman.
## Setup/Installation Requirements
* Install IntelliJ on your computer.
* Install Gradle.
* Install Postgres.
* Install Maven.
* Install Postman
* Follow the link provided and fork then clone the git repository
* Run the application on IntelliJ.
* Test the routes on postman.
## Database creation
CREATE DATABASE news_portal;
\c news_portal;

CREATE TABLE users (
id SERIAL PRIMARY KEY,
name VARCHAR,
position VARCHAR,
role VARCHAR,
departmentid INT
);

CREATE TABLE departments (
id SERIAL PRIMARY KEY,
name VARCHAR,
description VARCHAR,
number INT
);

CREATE TABLE posts (
id SERIAL PRIMARY KEY,
userid INT,
createdby VARCHAR,
content VARCHAR,
createdat BIGINT,
type VARCHAR,
departmentid INT
);
## Known Bugs
To date, there are no known bugs.
## Technologies Used
* Java
* Gradle
* Spark
* Maven
* JUnit
* Postman
## Support and contact details
drucilla.mumba@student.moringaschool.com
### License
(c) 2022 DruSadeMumba, Moringa School.
Licensed under [MIT License](LICENSE)
