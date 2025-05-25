package co.edu.uniquindio.red_social.clases.usuarios;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.interfaces.AdministracionGrupo;
import co.edu.uniquindio.red_social.clases.interfaces.Reporte;
import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;
import co.edu.uniquindio.red_social.clases.contenidos.Contenido;

import java.io.File;


/**
 * Clase Administrador
 * Esta clase representa a un administrador de la red social.
 * Extiende la clase Usuario e implementa las interfaces Reporte y AdministracionGrupo.
 */
public class Administrador extends Usuario implements Reporte, AdministracionGrupo {
    private String id;
    RedSocial redSocial = RedSocial.getInstance();

    public Administrador(String nombre, String apellido, String email, String contrasena, File imagenPerfil) {
        super(nombre, apellido, email, contrasena, imagenPerfil);
    }
    public Administrador(String id, String nombre, String apellido, String email, String contrasena, File imagenPerfil) {
        super(nombre, apellido, email, contrasena, imagenPerfil);
        this.id = id;
    }

    /**
     * Método para crear un grupo
     * @param nombreGrupo Nombre del grupo
     * @param descripcion Descripción del grupo
     * @param tipoGrupo Tipo de grupo (público o privado)
     * @param publico Indica si el grupo es público o privado
     * @return true si se crea el grupo, false en caso contrario
     */
    @Override
    public Grupo crearGrupo(String nombreGrupo, String descripcion, String tipoGrupo, boolean publico) {
        return redSocial.crearGrupo(nombreGrupo, descripcion, tipoGrupo, publico);
    }

    /**
     * Método para eliminar un grupo
     * @param nombreGrupo Nombre del grupo a eliminar
     * @return true si se elimina el grupo, false en caso contrario
     */
    @Override
    public boolean eliminarGrupo(Grupo nombreGrupo) {
        return redSocial.eliminarGrupo(nombreGrupo);
    }

    /**
     * Método para modificar un grupo
     * @param grupoAntiguo Grupo antiguo a modificar
     * @param grupoNuevo Grupo nuevo con los cambios
     * @return true si se modifica el grupo, false en caso contrario
     */
    @Override
    public boolean modificarGrupo(Grupo grupoAntiguo, Grupo grupoNuevo) {
        return redSocial.modificarGrupo(grupoAntiguo, grupoNuevo);
    }

    /**
     * Método para agregar un miembro a un grupo
     * @param grupo Grupo al que se va a agregar el miembro
     * @param miembro Miembro a agregar
     * @return true si se agrega el miembro, false en caso contrario
     */
    @Override
    public boolean agregarMiembro(Grupo grupo, Estudiante miembro) {

        return redSocial.agregarMiembro(grupo, miembro);
    }

    /**
     * Método para eliminar un miembro de un grupo
     * @param grupo Grupo del que se va a eliminar el miembro
     * @param miembro Miembro a eliminar
     * @return true si se elimina el miembro, false en caso contrario
     */
    @Override
    public boolean eliminarMiembro(Grupo grupo, Estudiante miembro) {
        return redSocial.eliminarMiembro(grupo, miembro);
    }


    /**
     * Metodo para obtener los contenidos mas valorados
     * @return ListaSimplementeEnlazada de contenidos mas valorados
     */
    @Override
    public ListaSimplementeEnlazada<Contenido> obtenerContenidosMasValorados() {
        return null;
    }

    /**
     * Metodo para obtener los estudiantes con mas conexiones
     * @return ListaSimplementeEnlazada de estudiantes con mas conexiones
     */
    @Override
    public ListaSimplementeEnlazada<Estudiante> obtenerEstudiantesConMasConexiones() {
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
