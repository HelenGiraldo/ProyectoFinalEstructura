package co.edu.uniquindio.red_social.clases.interfaces;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;

import java.io.File;
import java.time.LocalDate;

public interface AdministracionEstudiante {
    public boolean crearEstudiante(String nombre, String correo, String contrasena, File fotoPerfil);
    public boolean eliminarEstudiante(String correo);
    public boolean modificarEstudiante(Estudiante usuarioAntiguo, Estudiante usuarioNuevo);
    public Estudiante estudianteExisteCorreo(String correo);
}
