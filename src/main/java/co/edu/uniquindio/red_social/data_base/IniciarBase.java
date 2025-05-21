package co.edu.uniquindio.red_social.data_base;
import java.nio.file.*;
import java.sql.*;
import java.util.ResourceBundle;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

        public class IniciarBase {
            static ResourceBundle bundle = ResourceBundle.getBundle("sql");
            static String url = bundle.getString("url");
            static String user = bundle.getString("user");
            static String password = bundle.getString("password");

            public static void main(String[] args) {


                String sql = """
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
                        
                                                               CREATE TABLE IF NOT EXISTS publicaciones (
                                                                   id INT AUTO_INCREMENT PRIMARY KEY,
                                                                   id_usuario INT NOT NULL,
                                                                   contenido TEXT NOT NULL,
                                                                   fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                                   FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
                                                               );
                        
                                                               CREATE TABLE IF NOT EXISTS user_group (
                                                                   id_user INT NOT NULL,
                                                                   id_group INT NOT NULL,
                                                                   PRIMARY KEY (id_user, id_group),
                                                                   FOREIGN KEY (id_user) REFERENCES usuarios(id),
                                                                   FOREIGN KEY (id_group) REFERENCES grupos(id)
                                                               );
                        
                                                               CREATE TABLE IF NOT EXISTS contenidos (
                                                                   id INT NOT NULL AUTO_INCREMENT,
                                                                   tipo_contenido VARCHAR(45) NOT NULL,
                                                                   contenido VARCHAR(225) NOT NULL,
                                                                   titulo VARCHAR(45) NOT NULL,
                                                                   tema VARCHAR(45) NOT NULL,
                                                                   descripcion VARCHAR(225) NOT NULL,
                                                                   autor VARCHAR(45) NOT NULL,
                                                                   grupo INT,
                                                                   PRIMARY KEY (id),
                                                                   FOREIGN KEY (grupo) REFERENCES grupos(id)
                                                               );
                        
                                                               CREATE TABLE IF NOT EXISTS calificaciones (
                                                                   id INT NOT NULL AUTO_INCREMENT,
                                                                   valoracion INT NOT NULL,
                                                                   autor VARCHAR(100) NOT NULL,
                                                                   PRIMARY KEY (id),
                                                                   FOREIGN KEY (autor) REFERENCES usuarios(correo)
                                                               );
                        
                        
                                                               CREATE TABLE IF NOT EXISTS chat (
                                                                   id INT NOT NULL AUTO_INCREMENT,
                                                                   PRIMARY KEY (id)
                                                               );
                        
                                                               CREATE TABLE IF NOT EXISTS mensaje (
                                                                   id INT NOT NULL AUTO_INCREMENT,
                                                                   mensaje VARCHAR(255) NOT NULL,
                                                                   chat INT NOT NULL,
                                                                   PRIMARY KEY (id),
                                                                   FOREIGN KEY (chat) REFERENCES chat(id)
                                                               );
                        
                                                               CREATE TABLE IF NOT EXISTS amistades (
                                                                   id_user1 INT NOT NULL,
                                                                   id_user2 INT NOT NULL,
                                                                   estado VARCHAR(45) NOT NULL,
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
        """;

                try (Connection conn = DriverManager.getConnection(url, user, password);
                     Statement stmt = conn.createStatement()) {

                    for (String s : sql.split(";")) {
                        if (!s.trim().isEmpty()) {
                            stmt.execute(s);
                        }
                    }

                    System.out.println("Script ejecutado correctamente.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

