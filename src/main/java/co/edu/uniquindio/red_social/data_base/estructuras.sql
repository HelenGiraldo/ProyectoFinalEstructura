CREATE DATABASE IF NOT EXISTS red_social;
USE red_social;

CREATE TABLE IF NOT EXISTS usuarios (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        nombre VARCHAR(50) NOT NULL,
                                        apellido VARCHAR(50) NOT NULL,
                                        correo VARCHAR(100) UNIQUE NOT NULL,
                                        contrasena VARCHAR(255) NOT NULL,
                                        imagenPerfil VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS admins (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      nombre VARCHAR(50) NOT NULL,
                                      apellido VARCHAR(50) NOT NULL,
                                      correo VARCHAR(100) UNIQUE NOT NULL,
                                      contrasena VARCHAR(255) NOT NULL,
                                      imagenPerfil VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS grupos (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      nombre VARCHAR(50) NOT NULL,
                                      descripcion VARCHAR(255) NOT NULL,
                                      tipo VARCHAR(50) NOT NULL,
                                      publico TINYINT NOT NULL
);



CREATE TABLE IF NOT EXISTS user_group (
                                          id_user INT NOT NULL,
                                          id_group INT NOT NULL,
                                          PRIMARY KEY (id_user, id_group),
                                          FOREIGN KEY (id_user) REFERENCES usuarios(id),
                                          FOREIGN KEY (id_group) REFERENCES grupos(id)
);

CREATE TABLE IF NOT EXISTS publicaciones (
                                             id INT NOT NULL AUTO_INCREMENT,
                                             tipo_contenido VARCHAR(50) NOT NULL,
                                             titulo VARCHAR(100) NOT NULL,
                                             tema VARCHAR(100),
                                             descripcion TEXT,
                                             contenido TEXT NOT NULL,
                                             id_autor INT NOT NULL,
                                             id_grupo INT,
                                             PRIMARY KEY (id),
                                             FOREIGN KEY (id_autor) REFERENCES usuarios(id),
                                             FOREIGN KEY (id_grupo) REFERENCES grupos(id) ON DELETE SET NULL
);


CREATE TABLE IF NOT EXISTS calificaciones (
                                              id INT NOT NULL AUTO_INCREMENT,
                                              valoracion INT NOT NULL,
                                              autor INT NOT NULL,
                                              id_publicacion INT NOT NULL,
                                              PRIMARY KEY (id),
                                              FOREIGN KEY (autor) REFERENCES usuarios(id),
                                              FOREIGN KEY (id_publicacion) REFERENCES publicaciones(id) ON DELETE CASCADE
);



CREATE TABLE IF NOT EXISTS chat (
                                    id INT NOT NULL AUTO_INCREMENT,
                                    id_usuario1 INT NOT NULL,
                                    id_usuario2 INT NOT NULL,
                                    PRIMARY KEY (id),
                                    FOREIGN KEY (id_usuario1) REFERENCES usuarios(id),
                                    FOREIGN KEY (id_usuario2) REFERENCES usuarios(id)
);


CREATE TABLE IF NOT EXISTS chat_miembros (
                                             id_chat INT NOT NULL,
                                             id_usuario INT NOT NULL,
                                             PRIMARY KEY (id_chat, id_usuario),
                                             FOREIGN KEY (id_chat) REFERENCES chat(id) ON DELETE CASCADE,
                                             FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS mensaje (
                                       id INT NOT NULL AUTO_INCREMENT,
                                       mensaje VARCHAR(255) NOT NULL,
                                       id_remitente INT NOT NULL,
                                       fecha_evento DATETIME NOT NULL,
                                       chat INT NOT NULL,
                                       PRIMARY KEY (id),
                                       FOREIGN KEY (chat) REFERENCES chat(id),
                                       FOREIGN KEY (id_remitente) REFERENCES usuarios(id)

);

CREATE TABLE IF NOT EXISTS amistades (
                                         id_user1 INT NOT NULL,
                                         id_user2 INT NOT NULL,
                                         estado VARCHAR(45) NOT NULL,
                                         id_solicitante INT,
                                         PRIMARY KEY (id_user1, id_user2),
                                         FOREIGN KEY (id_user1) REFERENCES usuarios(id),
                                         FOREIGN KEY (id_user2) REFERENCES usuarios(id)
);


CREATE TABLE IF NOT EXISTS solicitudes_ayuda (
                                                 id INT NOT NULL AUTO_INCREMENT,
                                                 id_user INT NOT NULL,
                                                 estado VARCHAR(45) NOT NULL,
                                                 mensaje VARCHAR(255) NOT NULL,
                                                 titulo VARCHAR(45) NOT NULL,
                                                 prioridad VARCHAR(30) NOT NULL,
                                                 PRIMARY KEY (id),
                                                 FOREIGN KEY (id_user) REFERENCES usuarios(id)
);