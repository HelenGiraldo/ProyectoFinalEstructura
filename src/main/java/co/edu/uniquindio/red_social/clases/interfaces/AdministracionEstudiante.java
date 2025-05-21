package co.edu.uniquindio.red_social.clases.interfaces;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;

import java.io.File;


public interface AdministracionEstudiante {
    public Estudiante crearEstudiante(String nombre, String apellidp, String correo, String contrasena, File fotoPerfil);
    public boolean eliminarEstudiante(Estudiante estudiante);
    public boolean modificarEstudiante(Estudiante usuarioAntiguo, Estudiante usuarioNuevo);
    public Estudiante estudianteExisteCorreo(String correo);
}
