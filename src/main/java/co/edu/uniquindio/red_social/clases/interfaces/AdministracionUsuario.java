package co.edu.uniquindio.red_social.clases.interfaces;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.Usuario;

import java.io.File;

public interface AdministracionUsuario {
    public boolean crearUsuario(String nombre, String correo, String contrasena, String fechaNacimiento, File fotoPerfil);
    public boolean eliminarUsuario(String correo);
    public boolean modificarUsuario(Estudiante usuarioAntiguo, Estudiante usuarioNuevo);
    public Estudiante usuarioExiste(String correo);
}
