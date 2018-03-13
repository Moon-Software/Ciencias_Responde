/*
 * Se creará aquí tablas, relaciones, etc de acuerdo a los casos de usos
 * que le tocó hacer a cada integrante.
 */

/*
 * Avistar
 */
CREATE TABLE BUSCAR(
ID_USUARIO INTEGER CONSTRAINT UsuarioKey REFERENCES USUARIO(ID_USUARIO),
ID_ADMIN INTEGER CONSTRAINT AdminKey REFERENCES ADMINISTRADOR(ID_ADMIN)	
);

/*
 * Registrar
 */
-- Aquí va tu código.

/*
 * Iniciar sesión
 */-- Aquí va tu código.

/*
 * Cerrar sesión
 */
CREATE TABLE CERRAR_SESION(
ID_USUARIO INTEGER CONSTRAINT UsuarioKeyCerrar REFERENCES USUARIO(ID_USUARIO),
ID_ADMIN INTEGER CONSTRAINT AdminKeyCerrar REFERENCES ADMINISTRADOR(ID_ADMIN)	
);


/*
 * Crear comentarios
 */-- Aquí va tu código.

/*
 * Realizar pregunta
 */-- Aquí va tu código.

