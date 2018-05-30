INSERT INTO usuario (correo, nombre, contrasenia, f_registro, es_admin) VALUES ('ad@mail.com', 'carlos', '1234', '01/01/98', True);
INSERT INTO usuario (correo, nombre, contrasenia, f_registro, es_admin) VALUES ('ad2@mail.com', 'juan', '1234', '01/01/98', False);


INSERT INTO tema (nombre, descripcion) VALUES ('Horarios', 'Todo lo referente a los horarios.');
INSERT INTO tema (nombre, descripcion) VALUES ('Inscripciones', 'ekocnvkwnvonvownv wevo wjvwo v');
INSERT INTO tema (nombre, descripcion) VALUES ('Servicio Social', 'anjofa jaej aeofa j faejo fj a');
INSERT INTO tema (nombre, descripcion) VALUES ('Titulación', 'aeof aefjo afjo e  efjo aejf ajef');
INSERT INTO tema (nombre, descripcion) VALUES ('Posgrado', 'aeof j efoja foje fefhe ahv aov');
INSERT INTO tema (nombre, descripcion) VALUES ('Becas', 'e ajf efj aejfo aejfo ajf aefja foea');

INSERT INTO pregunta (id_usuario, descripcion, titulo, tema, fecha) VALUES (1, 'esto es una prueba', '¿Prueba Inscripciones?', 'Inscripciones', '03/12/98');
INSERT INTO pregunta (id_usuario, descripcion, titulo, tema, fecha) VALUES (1, 'esto es una prueba1', '¿Prueba Servicio Social?', 'Servicio Social', '03/12/98');
INSERT INTO pregunta (id_usuario, descripcion, titulo, tema, fecha) VALUES (1, 'esto es una prueba2', '¿Prueba Horarios?', 'Horarios', '03/12/98');
INSERT INTO pregunta (id_usuario, descripcion, titulo, tema, fecha) VALUES (1, 'esto es una prueba3', '¿Prueba Titulación?', 'Titulación', '03/12/98');
INSERT INTO pregunta (id_usuario, descripcion, titulo, tema, fecha) VALUES (1, 'esto es una prueba4', '¿Prueba Posgrado?', 'Posgrado', '03/12/98');
INSERT INTO pregunta (id_usuario, descripcion, titulo, tema, fecha) VALUES (1, 'esto es una prueba5', '¿Prueba Becas?', 'Becas', '03/12/98');
