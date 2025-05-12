package co.edu.uniquindio.red_social.clases.interfaces;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;

import java.io.File;

public interface AdministracionEstudiante {
    boolean crearEstudiante(String nombre, String apellido, String correo, String contrasena, File fotoPerfil);
    boolean eliminarEstudiante(Estudiante estudiante);
    boolean modificarEstudiante(Estudiante usuarioAntiguo, Estudiante usuarioNuevo);
    Estudiante estudianteExisteCorreo(String correo);
}