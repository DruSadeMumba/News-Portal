CREATE DATABASE news_portal_test;
\c news_portal_test;

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