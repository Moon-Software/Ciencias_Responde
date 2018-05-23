-- Script para crear las tablas necesarias de esta iteración.

CREATE TABLE usuario(
id_usuario SERIAL PRIMARY KEY,
correo VARCHAR(50) NOT NULL,
nombre VARCHAR(50) NOT NULL,
foto BYTEA,
contrasenia VARCHAR(25) NOT NULL,
f_registro TIMESTAMP NOT NULL,
es_admin BOOLEAN NOT NULL,
sesion BOOLEAN
);

CREATE TABLE tema(
nombre VARCHAR(25) PRIMARY KEY,
descripcion VARCHAR(200)
);


CREATE TABLE pregunta(
id_pregunta SERIAL PRIMARY KEY,
id_usuario INTEGER CONSTRAINT usuario_c REFERENCES usuario(id_usuario),
descripcion VARCHAR(2000) NOT NULL,
titulo VARCHAR(100) NOT NULL,
tema VARCHAR(25) CONSTRAINT tema_c REFERENCES tema(nombre),
fecha TIMESTAMP NOT NULL
);

CREATE TABLE comentario(
id_comentario SERIAL PRIMARY KEY,
id_usuario INTEGER CONSTRAINT usuario_c REFERENCES usuario(id_usuario),
id_pregunta INTEGER CONSTRAINT pregunta_c REFERENCES pregunta(id_pregunta),
contenido VARCHAR(1000) NOT NULL,
votos INTEGER NOT NULL,
fecha TIMESTAMP NOT NULL
);

