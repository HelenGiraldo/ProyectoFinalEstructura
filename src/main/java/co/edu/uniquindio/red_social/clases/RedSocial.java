package co.edu.uniquindio.red_social.clases;

import co.edu.uniquindio.red_social.clases.contenidos.Publicacion;
import co.edu.uniquindio.red_social.clases.interfaces.AdministracionEstudiante;
import co.edu.uniquindio.red_social.clases.interfaces.AdministracionGrupo;
import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.clases.usuarios.Administrador;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.estructuras.ArbolBinario;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;
import co.edu.uniquindio.red_social.util.EstudianteActual;

import java.io.File;
import java.util.Iterator;

public class RedSocial implements AdministracionEstudiante, AdministracionGrupo {
    private String nombre;
    private String claveAdministrador;
    private ListaSimplementeEnlazada<Estudiante> estudiantes;
    private ListaSimplementeEnlazada<Administrador> administradores;
    private ListaSimplementeEnlazada<Grupo> grupos;
    private ArbolBinario<Publicacion> publicaciones;


    private static RedSocial redSocial;


    private RedSocial(String nombre) {
        this.nombre = nombre;
        this.estudiantes = new ListaSimplementeEnlazada<>();
        this.administradores = new ListaSimplementeEnlazada<>();
        this.claveAdministrador = "admin123";
        this.grupos = new ListaSimplementeEnlazada<>();
        this.publicaciones = new ArbolBinario<>();
    }

    public static RedSocial getInstance(String nombre) {
        if (redSocial == null) {
            redSocial = new RedSocial(nombre);
        }
        return redSocial;
    }

    public static RedSocial getInstance() {
        return redSocial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ListaSimplementeEnlazada<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(ListaSimplementeEnlazada<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public ListaSimplementeEnlazada<Administrador> getAdministradores() {
        return administradores;
    }

    public void setAdministradores(ListaSimplementeEnlazada<Administrador> administradores) {
        this.administradores = administradores;
    }

    public static RedSocial getRedSocial() {
        return redSocial;
    }

    public static void setRedSocial(RedSocial redSocial) {
        RedSocial.redSocial = redSocial;
    }

    public ListaSimplementeEnlazada<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(ListaSimplementeEnlazada<Grupo> grupos) {
        this.grupos = grupos;
    }

    public String getClaveAdministrador() {
        return claveAdministrador;
    }

    public void setClaveAdministrador(String claveAdministrador) {
        this.claveAdministrador = claveAdministrador;
    }

    @Override
    public boolean crearEstudiante(String nombre, String apellido, String correo, String contrasena, File fotoPerfil) {
        Estudiante nuevoEstudiante = new Estudiante(nombre, apellido, correo, contrasena, fotoPerfil);
        if (estudianteExisteCorreo(correo) == null) {
            return estudiantes.add(nuevoEstudiante);
        }
        return false;
    }


    @Override
    public boolean eliminarEstudiante(Estudiante estudiante) {
        if (estudiantes.contains(estudiante)) {
            return estudiantes.remove(estudiante);
        }
        return false;
    }

    @Override
    public boolean modificarEstudiante(Estudiante usuarioAntiguo, Estudiante usuarioNuevo) {
        if (estudiantes.contains(usuarioAntiguo)) {
            usuarioAntiguo.setNombre(usuarioNuevo.getNombre());
            usuarioAntiguo.setEmail(usuarioNuevo.getEmail());
            usuarioAntiguo.setContrasena(usuarioNuevo.getContrasena());
            // Accede al método heredado de Usuario para obtener la imagen de perfil
            usuarioAntiguo.setImagenPerfil(usuarioNuevo.getImagenPerfil());

            return true;
        }
        return false;
    }



    @Override
    public Estudiante estudianteExisteCorreo(String correo) {
        Iterator<Estudiante> iterator = estudiantes.iterator();
        while (iterator.hasNext()) {
            Estudiante estudiante = iterator.next();
            if (estudiante.getEmail().equals(correo)) {
                return estudiante;
            }
        }
        return null;
    }


    @Override
    public boolean crearGrupo(String nombreGrupo, String descripcion, String tipoGrupo, boolean publico) {
        Grupo nuevoGrupo = new Grupo(nombreGrupo, descripcion, tipoGrupo, publico);
        if (grupos == null) {
            grupos = new ListaSimplementeEnlazada<>();
        }
        return grupos.add(nuevoGrupo);
    }

    @Override
    public boolean eliminarGrupo(Grupo grupo) {
        grupo.eliminarGrupo();
        return grupos.remove(grupo);
    }

    @Override
    public boolean modificarGrupo(Grupo grupoAntiguo, Grupo grupoNuevo) {
        if (grupos.contains(grupoAntiguo)) {
            grupoAntiguo.setNombre(grupoNuevo.getNombre());
            grupoAntiguo.setDescripcion(grupoNuevo.getDescripcion());
            return true;
        }
        return false;
    }

    @Override
    public boolean agregarMiembro(Grupo grupo, Estudiante miembro) {
        return grupo.agregarMiembro(miembro);
    }

    @Override
    public boolean eliminarMiembro(Grupo grupo, Estudiante miembro) {
        return grupo.eliminarMiembro(miembro);
    }

    public boolean iniciarSesion(String correo, String contrasena) {
        Estudiante estudiante = estudianteExisteCorreo(correo);
        if (estudiante != null && estudiante.getContrasena().equals(contrasena)) {
            EstudianteActual.setUsuarioActual(estudiante);
            return true;
        }
        return false;
    }

    public boolean agregarPublicacion(Publicacion publicacion) {
        if (!publicaciones.contains(publicacion)) {
            return publicaciones.add(publicacion);
        }
        return false;
    }

    public boolean eliminarPublicacion(Publicacion publicacion) {
        if (publicaciones.contains(publicacion)) {
            return publicaciones.remove(publicacion);
        }
        return false;
    }
}
