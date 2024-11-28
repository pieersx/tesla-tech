DROP DATABASE IF EXISTS tesla_tech_db;

CREATE DATABASE tesla_tech_db;

USE tesla_tech_db;

DROP TABLE IF EXISTS usuario;
CREATE TABLE usuario(
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(255) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    pregunta VARCHAR(255) NOT NULL,
    respuesta VARCHAR(255) NOT NULL,
    fecha DATE NOT NULL
);

DROP TABLE IF EXISTS producto;
CREATE TABLE producto(
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_producto VARCHAR(255) NOT NULL,
    nombre_producto VARCHAR(255) NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    precio DOUBLE NOT NULL,
    estado VARCHAR(255) NOT NULL,
    imagen VARCHAR(500),
    fecha DATE NOT NULL
);

DROP TABLE IF EXISTS cliente;
CREATE TABLE cliente(
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_producto VARCHAR(255) NOT NULL,
    nombre_producto VARCHAR(255) NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    cantidad INT NOT NULL,
    precio DOUBLE NOT NULL,
    fecha DATE NOT NULL,
    imagen VARCHAR(500),
    usuario_empleado VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS recibo;
CREATE TABLE recibo(
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    total DOUBLE NOT NULL,
    fecha DATE NOT NULL,
    usuario_empleado VARCHAR(255) NOT NULL
);
