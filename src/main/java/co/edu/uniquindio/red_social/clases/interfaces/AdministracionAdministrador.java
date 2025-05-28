package co.edu.uniquindio.red_social.clases.interfaces;

import co.edu.uniquindio.red_social.clases.usuarios.Administrador;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;

import java.io.File;

public interface AdministracionAdministrador {

    public Administrador crearAdministrador(String nombre, String apellido, String correo, String contrasena, File fotoPerfil);
    public boolean eliminarAdministrador(Administrador administrador);
    public boolean modificarAdministrador(Administrador usuarioAntiguo, Administrador usuarioNuevo);
    public Administrador administradorExisteCorreo(String correo);
}
