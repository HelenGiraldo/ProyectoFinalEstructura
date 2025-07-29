CREATE DATABASE IF NOT EXISTS red_social;
USE red_social;

-- Tabla para Usuarios (Estudiantes)
CREATE TABLE IF NOT EXISTS usuarios (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    imagenPerfil VARCHAR(255) NOT NULL
    );

-- Tabla para Administradores
CREATE TABLE IF NOT EXISTS admins (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    imagenPerfil VARCHAR(255) NOT NULL
    );

-- Tabla para Grupos
CREATE TABLE IF NOT EXISTS grupos (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    publico TINYINT NOT NULL
    );

-- Tabla de unión para la relación N:M entre Usuarios y Grupos (Miembros del grupo)
CREATE TABLE IF NOT EXISTS user_group (
                                          id_user INT NOT NULL,
                                          id_group INT NOT NULL,
                                          PRIMARY KEY (id_user, id_group),
    FOREIGN KEY (id_user) REFERENCES usuarios(id) ON DELETE CASCADE, -- Añadido ON DELETE CASCADE
    FOREIGN KEY (id_group) REFERENCES grupos(id) ON DELETE CASCADE  -- Añadido ON DELETE CASCADE
    );

-- Tabla para Publicaciones (Contenidos)
CREATE TABLE IF NOT EXISTS publicaciones (
                                             id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                             tipo_contenido VARCHAR(50) NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    tema VARCHAR(100),
    descripcion TEXT,
    contenido TEXT NOT NULL, -- Almacena la ruta del archivo de contenido
    id_autor INT NOT NULL,
    id_grupo INT,
    FOREIGN KEY (id_autor) REFERENCES usuarios(id) ON DELETE CASCADE, -- Añadido ON DELETE CASCADE
    FOREIGN KEY (id_grupo) REFERENCES grupos(id) ON DELETE SET NULL
    );

-- Tabla para Calificaciones de Publicaciones
CREATE TABLE IF NOT EXISTS calificaciones (
                                              id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                              valoracion INT NOT NULL,
                                              autor INT NOT NULL, -- Referencia al usuario que califica
                                              id_publicacion INT NOT NULL,
                                              FOREIGN KEY (autor) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (id_publicacion) REFERENCES publicaciones(id) ON DELETE CASCADE
    );

-- Tabla para Chats entre usuarios
CREATE TABLE IF NOT EXISTS chat (
                                    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                    id_usuario1 INT NOT NULL,
                                    id_usuario2 INT NOT NULL,
                                    FOREIGN KEY (id_usuario1) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (id_usuario2) REFERENCES usuarios(id) ON DELETE CASCADE,
    UNIQUE (id_usuario1, id_usuario2) -- Asegura que no haya chats duplicados entre los mismos dos usuarios
    );

-- Tabla para Mensajes dentro de un Chat
CREATE TABLE IF NOT EXISTS mensaje (
                                       id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       mensaje TEXT NOT NULL, -- Se cambió a TEXT para mensajes más largos
                                       id_remitente INT NOT NULL,
                                       fecha_evento DATETIME NOT NULL,
                                       chat INT NOT NULL,
                                       FOREIGN KEY (chat) REFERENCES chat(id) ON DELETE CASCADE,
    FOREIGN KEY (id_remitente) REFERENCES usuarios(id) ON DELETE CASCADE
    );

-- Tabla para Relaciones de Amistad
CREATE TABLE IF NOT EXISTS amistades (
                                         id_user1 INT NOT NULL,
                                         id_user2 INT NOT NULL,
                                         estado VARCHAR(45) NOT NULL, -- Ej: 'pendiente', 'confirmado', 'rechazado'
    id_solicitante INT, -- El ID del usuario que envió la solicitud
    PRIMARY KEY (id_user1, id_user2),
    FOREIGN KEY (id_user1) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (id_user2) REFERENCES usuarios(id) ON DELETE CASCADE
    );

-- Tabla para Solicitudes de Ayuda
CREATE TABLE IF NOT EXISTS solicitudes_ayuda (
                                                 id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                                 id_user INT NOT NULL,
                                                 estado VARCHAR(45) NOT NULL, -- Ej: 'abierto', 'en progreso', 'cerrado'
    mensaje TEXT NOT NULL, -- Se cambió a TEXT para mensajes más largos
    titulo VARCHAR(100) NOT NULL, -- Aumentado a 100 caracteres
    prioridad VARCHAR(30) NOT NULL, -- Ej: 'alta', 'media', 'baja'
    FOREIGN KEY (id_user) REFERENCES usuarios(id) ON DELETE CASCADE
    );

-- Tabla para Soluciones (asociadas a Solicitudes de Ayuda, aunque el código solo las relaciona con un usuario)
CREATE TABLE IF NOT EXISTS soluciones (
                                          id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                          texto TEXT NOT NULL,
                                          id_usuario INT NOT NULL, -- El usuario que proporcionó la solución
                                          FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE
    );

-- Tabla para Preferencias del Usuario
CREATE TABLE IF NOT EXISTS preferencias (
                                            id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                            texto TEXT NOT NULL, -- La preferencia en sí
                                            id_usuario INT NOT NULL,
                                            FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE
    );