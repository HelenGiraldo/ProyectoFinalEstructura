package co.edu.uniquindio.red_social.clases.interfaces;

import co.edu.uniquindio.red_social.clases.usuarios.Administrador;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;

import java.io.File;

public interface AdministracionAdministrador {

    public boolean crearAdministrador(String nombre, String correo, String contrasena, File fotoPerfil);
    public boolean eliminarAdministrador(Administrador administrador);
    public boolean modificarEstudiante(Estudiante usuarioAntiguo, Estudiante usuarioNuevo);
    public Estudiante estudianteExisteCorreo(String correo);
}
