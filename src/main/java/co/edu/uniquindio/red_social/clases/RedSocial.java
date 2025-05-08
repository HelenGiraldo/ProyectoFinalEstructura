package co.edu.uniquindio.red_social.clases;

import co.edu.uniquindio.red_social.clases.interfaces.AdministracionEstudiante;
import co.edu.uniquindio.red_social.clases.interfaces.AdministracionGrupo;
import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.clases.usuarios.Administrador;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
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


    private static RedSocial redSocial;


    private RedSocial(String nombre) {
        this.nombre = nombre;
        this.estudiantes = new ListaSimplementeEnlazada<>();
        this.administradores = new ListaSimplementeEnlazada<>();
        this.claveAdministrador = "admin123";
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
    public boolean crearEstudiante(String nombre, String correo, String contrasena, File fotoPerfil) {
        Estudiante nuevoEstudiante = new Estudiante(nombre, correo, contrasena, fotoPerfil);
        if (estudianteExisteCorreo(correo)==null) {
            return estudiantes.add(nuevoEstudiante);
        }
        return false;
    }

    @Override
    public boolean eliminarEstudiante(String correo) {
        Estudiante estudiante = estudianteExisteCorreo(correo);
        if (estudiante != null) {
            return estudiantes.remove(estudiante);
        }
        return false;
    }

    @Override
    public boolean modificarEstudiante(Estudiante usuarioAntiguo, Estudiante usuarioNuevo) {

        if (estudiantes.contains(usuarioAntiguo)) {
            usuarioAntiguo.setNombre(usuarioNuevo.getNombre());
            usuarioAntiguo.setCorreo(usuarioNuevo.getCorreo());
            usuarioAntiguo.setContrasena(usuarioNuevo.getContrasena());
            usuarioAntiguo.setFotoPerfil(usuarioNuevo.getFotoPerfil());
            return true;
        }
        return false;
    }



    @Override
    public Estudiante estudianteExisteCorreo(String correo) {
        Iterator<Estudiante> iterator = estudiantes.iterator();
        while (iterator.hasNext()) {
            Estudiante estudiante = iterator.next();
            if (estudiante.getCorreo().equals(correo)) {
                return estudiante;
            }
        }
        return null;
    }


    @Override
    public boolean crearGrupo(String nombreGrupo, String descripcion, String tipoGrupo) {
        return false;
    }

    @Override
    public boolean eliminarGrupo(Grupo nombreGrupo) {
        return false;
    }

    @Override
    public boolean modificarGrupo(Grupo grupoAntiguo, Grupo grupoNuevo) {
        return false;
    }

    @Override
    public boolean agregarMiembro(Grupo grupo, Estudiante miembro) {
        return false;
    }

    @Override
    public boolean eliminarMiembro(Grupo grupo, Estudiante miembro) {
        return false;
    }

    public boolean iniciarSesion(String correo, String contrasena) {
        Estudiante estudiante = estudianteExisteCorreo(correo);
        if (estudiante != null && estudiante.getContrasena().equals(contrasena)) {
            EstudianteActual.setUsuarioActual(estudiante);
            return true;
        }
        return false;
    }
}
