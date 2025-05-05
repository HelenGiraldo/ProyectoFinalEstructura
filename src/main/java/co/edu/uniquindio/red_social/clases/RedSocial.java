package co.edu.uniquindio.red_social.clases;

import co.edu.uniquindio.red_social.clases.interfaces.AdministracionUsuario;
import co.edu.uniquindio.red_social.clases.usuarios.Administrador;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

import java.io.File;
import java.util.Iterator;

public class RedSocial implements AdministracionUsuario {
    private String nombre;
    private ListaSimplementeEnlazada<Estudiante> estudiantes;
    private ListaSimplementeEnlazada<Administrador> administradores;

    public RedSocial(String nombre) {
        this.nombre = nombre;
        this.estudiantes = new ListaSimplementeEnlazada<>();
        this.administradores = new ListaSimplementeEnlazada<>();
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
    public boolean crearUsuario(String nombre, String correo, String contrasena, String fechaNacimiento, File fotoPerfil) {
        return false;
    }

    @Override
    public boolean eliminarUsuario(String correo) {
return  false;
    }

    @Override
    public boolean modificarUsuario(Estudiante usuarioAntiguo, Estudiante usuarioNuevo) {

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
    public Estudiante usuarioExiste(String correo) {
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
