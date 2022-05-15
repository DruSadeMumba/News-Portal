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
name VARCHAR
);

CREATE TABLE posts (
id SERIAL PRIMARY KEY,
userid INT,
createdby VARCHAR,
content VARCHAR,
createdat BIGINT,
type VARCHAR
);