
package co.edu.uniquindio.red_social.data_base;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static co.edu.uniquindio.red_social.data_base.UtilSQL.getConnection;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/redsocial";
    private static final String USER = "root";
    private static final String PASSWORD = "StrongPassword1234*";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static List<Estudiante> obtenerEstudiantes() {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = "SELECT id, nombre, email, contrasena, programa, ruta_imagen FROM estudiantes";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String contrasena = rs.getString("contrasena");
                String programa = rs.getString("programa");
                String rutaImagen = rs.getString("ruta_imagen");

                File imagen = new File(rutaImagen != null ? rutaImagen : "src/main/resources/co/edu/uniquindio/red_social/imagenes/imagePerfil.png");

                Estudiante estudiante = new Estudiante(id, nombre, email, contrasena, programa, imagen);
                estudiantes.add(estudiante);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estudiantes;
    }


}
