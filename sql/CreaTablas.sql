-- Script para crear las tablas necesarias de esta iteración.

CREATE TABLE PREGUNTA(
ID_Pregunta INTEGER PRIMARY KEY,
Descripcion VARCHAR2(50) NOT NULL,
Titulo VARCHAR2(50) NOT NULL,
Tema VARCHAR2(50) NOT NULL
);

CREATE TABLE COMENTARIO(
ID_Com INTEGER PRIMARY KEY,
Contenido VARCHAR2(50) NOT NULL,
VOTOS INTEGER NOT NULL
);

CREATE TABLE TENER(
ID_Pregunta INTEGER CONSTRAINT FK_Pregunta_T REFERENCES Pregunta(ID_Pregunta),
ID_Com INTEGER CONSTRAINT FK_Com_T REFERENCES Comentario(ID_Com)
);

CREATE TABLE USUARIO(
ID_Usuario INTEGER PRIMARY KEY,
Correo VARCHAR2(50) NOT NULL,
Nombre VARCHAR2(50) NOT NULL,
Foto VARCHAR2(50) NOT NULL, -- No estoy seguro del tipo de este atributo.
Contrasenia VARCHAR2(15) NOT NULL,
F_Registro VARCHAR2(50) NOT NULL,
Es_Admin INTEGER NOT NULL, -- 0 = False, 1 = True. (No sé si en PostgreSQL funcionen los booleanos)
Sesion VARCHAR2(50) -- Tampoco estoy seguro del tipo.
);

CREATE TABLE HACER(
ID_Usuario INTEGER CONSTRAINT FK_Usuario_H REFERENCES Usuario(ID_Usuario),	
ID_Pregunta INTEGER CONSTRAINT FK_Pregunta_H REFERENCES Pregunta(ID_Pregunta),
Fecha DATE NOT NULL
);

CREATE TABLE REALIZAR(
ID_Usuario INTEGER CONSTRAINT FK_Usuario_R REFERENCES Usuario(ID_Usuario),
ID_Com INTEGER CONSTRAINT FK_Com_R REFERENCES Comentario(ID_Com),
Fecha DATE NOT NULL
);
