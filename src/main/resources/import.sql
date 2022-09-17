INSERT INTO persona (nombres_persona, apellidos_persona, cedula_persona, email_persona) VALUES  ('Vinico', 'Sichiqui', '0107081580', 'vini@gmail.com');

INSERT INTO rol (descripcion_rol, nombre_rol) VALUES ('Administrador', 'ROLE_ADMINISTRADOR');
INSERT INTO rol (descripcion_rol, nombre_rol) VALUES ('Vendedor', 'ROLE_VENDEDOR');
INSERT INTO rol (descripcion_rol, nombre_rol) VALUES ('Inventario', 'ROLE_INVENTARIO');

INSERT INTO usuario (estado_usuario, password_usuario, id_persona) VALUES (true, '$2a$10$nau04Ps5X18uv9e/X0x1xOzXm9iV7yiGOdP57gtLalbP9YAA8g6k2', 1);
INSERT INTO usuario_rol (id_rol, id_usuario) VALUES (1, 1);