DROP DATABASE IF EXISTS tesla_tech_db;

CREATE DATABASE tesla_tech_db;

USE tesla_tech_db;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    question VARCHAR(255) NOT NULL,
    answer VARCHAR(255) NOT NULL,
    date DATE NOT NULL
);

DROP TABLE IF EXISTS product;
CREATE TABLE product(
    id INT AUTO_INCREMENT PRIMARY KEY,
    prod_id VARCHAR(255) NOT NULL,
    prod_name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    price DOUBLE NOT NULL,
    status VARCHAR(255) NOT NULL,
    image VARCHAR(500),
    date DATE NOT NULL
);

DROP TABLE IF EXISTS customer;
CREATE TABLE customer(
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    prod_id VARCHAR(255) NOT NULL,
    prod_name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    price DOUBLE NOT NULL,
    date DATE NOT NULL,
    image VARCHAR(255),
    em_username VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS receipt;
CREATE TABLE receipt(
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    total DOUBLE NOT NULL,
    date DATE NOT NULL,
    em_username VARCHAR(255) NOT NULL
);
