
package co.edu.uniquindio.red_social.data_base;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/redsocial";
    private static final String USER = "root";
    private static final String PASSWORD = "StrongPassword1234*";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


}
