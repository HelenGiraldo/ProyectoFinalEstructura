package co.edu.uniquindio.red_social.clases;

import co.edu.uniquindio.red_social.clases.interfaces.AdministracionEstudiante;
import co.edu.uniquindio.red_social.clases.usuarios.Administrador;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

import java.io.File;
import java.time.LocalDate;
import java.util.Iterator;

public class RedSocial implements AdministracionEstudiante {
    private String nombre;
    private ListaSimplementeEnlazada<Estudiante> estudiantes;
    private ListaSimplementeEnlazada<Administrador> administradores;
    private static RedSocial redSocial;

    private RedSocial(String nombre) {
        this.nombre = nombre;
        this.estudiantes = new ListaSimplementeEnlazada<>();
        this.administradores = new ListaSimplementeEnlazada<>();
    }

    public static RedSocial getInstance(String nombre) {
        if (redSocial == null) {
            redSocial = new RedSocial(nombre);
        }
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

    @Override
    public boolean crearEstudiante(String nombre, String correo, String contrasena, LocalDate fechaNacimiento, File fotoPerfil) {
        Estudiante nuevoEstudiante = new Estudiante(nombre, correo, contrasena, fechaNacimiento, fotoPerfil);
        if (estudianteExisteCorreo(correo)!=null) {
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
            usuarioAntiguo.setFechaNacimiento(usuarioNuevo.getFechaNacimiento());
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
}
