package co.edu.uniquindio.red_social.clases.usuarios;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRegistrado {

    private static List<Estudiante> usuariosRegistrados = new ArrayList<>();

    // Método para registrar un nuevo usuario
    public static void registrarUsuario(Estudiante usuario) {
        usuariosRegistrados.add(usuario);
    }

    // Método para buscar un usuario por su email
    public static Estudiante buscarUsuarioPorEmail(String correo) {
        for (Estudiante usuario : usuariosRegistrados) {
            if (usuario.getEmail().equals(correo)) {
                return usuario;
            }
        }
        return null;
    }

    // Método para obtener la lista de usuarios
    public static List<Estudiante> getUsuariosRegistrados() {
        return usuariosRegistrados;
    }
}
