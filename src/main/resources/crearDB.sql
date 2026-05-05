
-- CREAR BASE DE DATOS

DROP DATABASE IF EXISTS projectroom;

-- Crear usuario
CREATE USER IF NOT EXISTS 'User'@'localhost' IDENTIFIED BY '1234';

-- Conceder permisos
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, CREATE VIEW ON projectroom.* TO 'User'@'localhost';

CREATE DATABASE projectroom;
USE projectroom;


-- TABLAS PRINCIPALES SIN DEPENDENCIAS


CREATE TABLE usuario (
    email VARCHAR(128) PRIMARY KEY,
    nombre VARCHAR(128) NOT NULL,
    apellido VARCHAR(128) NOT NULL,
    password VARCHAR(128) NOT NULL,
    foto VARCHAR(255)
);

CREATE TABLE prioridad (
    idPrioridad INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(128) NOT NULL,
    color VARCHAR(128) NOT NULL
);

CREATE TABLE estado (
    idEstado INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(128) NOT NULL,
    color VARCHAR(128) NOT NULL
);

CREATE TABLE etiqueta (
    idEtiqueta INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(128) NOT NULL,
    color VARCHAR(128) NOT NULL,
    creador VARCHAR(128),
    FOREIGN KEY (creador) REFERENCES usuario(email)
);

CREATE TABLE rol (
    idRol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(128) NOT NULL,
    color VARCHAR(128) NOT NULL,
    creador VARCHAR(128),
    FOREIGN KEY (creador) REFERENCES usuario(email)
);

CREATE TABLE permiso (
    idPermiso INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(128) NOT NULL
);


-- TABLAS QUE DEPENDEN DE OTRAS


CREATE TABLE proyecto (
    idProyecto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(128) NOT NULL,
    color VARCHAR(128) NOT NULL,
    tipo VARCHAR(128) NOT NULL,
    fecha_inicial DATE NOT NULL,
    fecha_final DATE NOT NULL,
    presupuesto INT,
    archivado BOOLEAN NOT NULL,
    idEtiqueta INT,
    FOREIGN KEY (idEtiqueta) REFERENCES etiqueta(idEtiqueta)
);

CREATE TABLE quatrimestre (
    idQuatrimestre INT AUTO_INCREMENT PRIMARY KEY,
    porcentaje INT NOT NULL,
    gasto INT NOT NULL,
    idProyecto INT,
    FOREIGN KEY (idProyecto) REFERENCES proyecto(idProyecto)
);

CREATE TABLE integrante (
    idIntegrante INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(128),
    idRol INT,
    idProyecto INT,
    FOREIGN KEY (email) REFERENCES usuario(email),
    FOREIGN KEY (idRol) REFERENCES rol(idRol),
    FOREIGN KEY (idProyecto) REFERENCES proyecto(idProyecto)
);

CREATE TABLE tarea (
    idTarea INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(128) NOT NULL,
    idEtiqueta INT,
    idProyecto INT,
    idPrioridad INT,
    idIntegrante INT,
    idEstado INT,
    FOREIGN KEY (idEtiqueta) REFERENCES etiqueta(idEtiqueta),
    FOREIGN KEY (idProyecto) REFERENCES proyecto(idProyecto),
    FOREIGN KEY (idPrioridad) REFERENCES prioridad(idPrioridad),
    FOREIGN KEY (idIntegrante) REFERENCES integrante(idIntegrante),
    FOREIGN KEY (idEstado) REFERENCES estado(idEstado)
);


-- TABLAS INTERMEDIAS (N:M)


CREATE TABLE detalle_integrante_permiso (
    idIntegrante INT,
    idPermiso INT,
    PRIMARY KEY (idIntegrante, idPermiso),
    FOREIGN KEY (idIntegrante) REFERENCES integrante(idIntegrante),
    FOREIGN KEY (idPermiso) REFERENCES permiso(idPermiso)
);

CREATE TABLE detalle_integrante_etiqueta (
    idIntegrante INT,
    idEtiqueta INT,
    PRIMARY KEY (idIntegrante, idEtiqueta),
    FOREIGN KEY (idIntegrante) REFERENCES integrante(idIntegrante),
    FOREIGN KEY (idEtiqueta) REFERENCES etiqueta(idEtiqueta)
);

CREATE TABLE detalle_rol_permiso (
    idRol INT,
    idPermiso INT,
    PRIMARY KEY (idRol, idPermiso),
    FOREIGN KEY (idRol) REFERENCES rol(idRol),
    FOREIGN KEY (idPermiso) REFERENCES permiso(idPermiso)
);

CREATE TABLE detalle_etiqueta_rol (
    idEtiqueta INT,
    idRol INT,
    PRIMARY KEY (idEtiqueta, idRol),
    FOREIGN KEY (idEtiqueta) REFERENCES etiqueta(idEtiqueta),
    FOREIGN KEY (idRol) REFERENCES rol(idRol)
);

CREATE TABLE detalle_proyecto_rol (
    idProyecto INT,
    idRol INT,
    PRIMARY KEY (idProyecto, idRol),
    FOREIGN KEY (idProyecto) REFERENCES proyecto(idProyecto),
    FOREIGN KEY (idRol) REFERENCES rol(idRol)
);

CREATE TABLE detalle_proyecto_etiqueta (
    idProyecto INT,
    idEtiqueta INT,
    PRIMARY KEY (idProyecto, idEtiqueta),
    FOREIGN KEY (idProyecto) REFERENCES proyecto(idProyecto),
    FOREIGN KEY (idEtiqueta) REFERENCES etiqueta(idEtiqueta)
);