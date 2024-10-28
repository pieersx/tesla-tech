DROP DATABASE IF EXISTS tesla_tech_db;

CREATE DATABASE tesla_tech_db;

USE tesla_tech_db;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    question VARCHAR(100) NOT NULL,
    answer VARCHAR(100) NOT NULL,
    date DATE NOT NULL
);
