/*
 * Se creará aquí tablas, relaciones, etc de acuerdo a los casos de usos
 * que le tocó hacer a cada integrante.
 */

/*
 * Avistar
 */
-- Aquí va tu código.

/*
 * Registrar
 */
-- Aquí va tu código.

/*
 * Iniciar sesión
 */-- Aquí va tu código.

/*
 * Cerrar sesión
 */-- Aquí va tu código.

/*
 * Caso de uso: Crear comentarios
 */
CREATE TABLE Comentario {
	Id_comentario	INTEGER,
	Id_usuario		INTEGER,
	Id_pregunta		INTEGER,
	contenido		VARCHAR(1000) NOT NULL,
	f_comentario 	DATE NOT NULL,
	CONSTRAINT coment_key PRIMARY KEY (Id_comentario),
	CONSTRAINT coment_usr FOREIGN KEY (Id_usuario) REFERENCES Usuario (ID),
	CONSTRAINT coment_q FOREIGN KEY (Id_pregunta) REFERENCES Pregunta (Id_pregunta)
};


/*
 * Caso de uso: Realizar pregunta
 */
CREATE TABLE Pregunta {
	Id_pregunta		INTEGER,
	Id_usuario		INTEGER,
	titulo			VARCHAR(40) NOT NULL,
	descripcion		VARCHAR(1000) NOT NULL,
	f_pregunta	 	DATE NOT NULL,
	CONSTRAINT question_key PRIMARY KEY (Id_pregunta),
	CONSTRAINT question_usr FOREIGN KEY (Id_usuario) REFERENCES Usuario (ID)
};
