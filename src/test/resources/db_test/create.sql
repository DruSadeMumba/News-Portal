CREATE DATABASE news_portal_test;
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
content INT,
createdat BIGINT
);