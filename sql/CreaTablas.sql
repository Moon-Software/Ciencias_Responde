-- Script para crear las tablas necesarias de esta iteración.

CREATE TABLE USUARIO(
ID_Usuario INTEGER PRIMARY KEY,
Correo VARCHAR(50) NOT NULL,
Nombre VARCHAR(50) NOT NULL,
Foto OID,
Contrasenia VARCHAR(15) NOT NULL,
F_Registro VARCHAR(50) NOT NULL,
Es_Admin BOOLEAN NOT NULL,
Sesion VARCHAR(50)
);

CREATE TABLE PREGUNTA(
ID_Pregunta INTEGER PRIMARY KEY,
ID_Usuario INTEGER CONSTRAINT Usuario_P REFERENCES Usuario(ID_Usuario),
Descripcion VARCHAR(500) NOT NULL,
Titulo VARCHAR(100) NOT NULL,
Tema VARCHAR(50) NOT NULL,
Fecha DATE NOT NULL
);

CREATE TABLE COMENTARIO(
ID_Com INTEGER PRIMARY KEY,
ID_Usuario INTEGER CONSTRAINT Usuario_C REFERENCES Usuario(ID_Usuario),
ID_Pregunta INTEGER CONSTRAINT Pregunta_C REFERENCES Pregunta(ID_Pregunta),
Contenido VARCHAR(400) NOT NULL,
VOTOS INTEGER NOT NULL,
Fecha DATE NOT NULL
);

