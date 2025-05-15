package co.edu.uniquindio.red_social.clases.usuarios;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.interfaces.AdministracionGrupo;
import co.edu.uniquindio.red_social.clases.interfaces.Reporte;
import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;
import co.edu.uniquindio.red_social.clases.contenidos.Contenido;

import java.io.File;

public class Administrador extends Usuario implements Reporte, AdministracionGrupo {

    RedSocial redSocial = RedSocial.getInstance();

    public Administrador(String nombre, String apellido, String email, String contrasena, File imagenPerfil) {
        super(nombre, apellido, email, contrasena, imagenPerfil);
    }

    @Override
    public boolean crearGrupo(String nombreGrupo, String descripcion, String tipoGrupo, boolean publico) {
        return redSocial.crearGrupo(nombreGrupo, descripcion, tipoGrupo, publico);
    }

    @Override
    public boolean eliminarGrupo(Grupo nombreGrupo) {
        return redSocial.eliminarGrupo(nombreGrupo);
    }

    @Override
    public boolean modificarGrupo(Grupo grupoAntiguo, Grupo grupoNuevo) {
        return redSocial.modificarGrupo(grupoAntiguo, grupoNuevo);
    }

    @Override
    public boolean agregarMiembro(Grupo grupo, Estudiante miembro) {

        return redSocial.agregarMiembro(grupo, miembro);
    }

    @Override
    public boolean eliminarMiembro(Grupo grupo, Estudiante miembro) {
        return redSocial.eliminarMiembro(grupo, miembro);
    }

    @Override
    public ListaSimplementeEnlazada<Contenido> obtenerContenidosMasValorados() {
        return null;
    }

    @Override
    public ListaSimplementeEnlazada<Estudiante> obtenerEstudiantesConMasConexiones() {
        return null;
    }
}
