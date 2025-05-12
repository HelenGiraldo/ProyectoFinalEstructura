package co.edu.uniquindio.red_social.clases.usuarios;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRegistrado {

    private static List<Usuario> usuariosRegistrados = new ArrayList<>();

    // Método para registrar un nuevo usuario
    public static void registrarUsuario(Usuario usuario) {
        usuariosRegistrados.add(usuario);
    }

    // Método para buscar un usuario por su email
    public static Usuario buscarUsuarioPorEmail(String correo) {
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.getCorreo().equals(correo)) {
                return usuario;
            }
        }
        return null;
    }

    // Método para obtener la lista de usuarios
    public static List<Usuario> getUsuariosRegistrados() {
        return usuariosRegistrados;
    }
}
