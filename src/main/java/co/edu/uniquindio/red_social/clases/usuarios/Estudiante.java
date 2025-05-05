package co.edu.uniquindio.red_social.clases.usuarios;

import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

import java.io.File;
import java.time.LocalDate;

public class Estudiante extends Usuario {
    private ListaSimplementeEnlazada<Usuario> contactos;
    private ListaSimplementeEnlazada<String> preferencias;

    public Estudiante(String nombre, String correo, String contrasena, LocalDate fechaNacimiento, File fotoPerfil) {
        super(nombre, correo, contrasena, fechaNacimiento, fotoPerfil);
        this.contactos = new ListaSimplementeEnlazada<>();
        this.preferencias = new ListaSimplementeEnlazada<>();
    }

    public boolean anadirContacto(Usuario contacto) {
        if (!contactos.contains(contacto)) {
            return contactos.add(contacto);
        }
        return false;
    }

    public boolean eliminarContacto(Usuario contacto) {
        if (contactos.contains(contacto)) {
            return contactos.remove(contacto);
        }
        return false;
    }

    public boolean anadirPreferencia(String preferencia) {
        if (!preferencias.contains(preferencia)) {
           return  preferencias.add(preferencia);
        }
        return false;
    }

    public boolean eliminarPreferencia(String preferencia) {
        if (preferencias.contains(preferencia)) {
            preferencias.remove(preferencia);
            return true;
        }
        return false;
    }

    public ListaSimplementeEnlazada<Usuario> getContactos() {
        return contactos;
    }

    public void setContactos(ListaSimplementeEnlazada<Usuario> contactos) {
        this.contactos = contactos;
    }

    public ListaSimplementeEnlazada<String> getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(ListaSimplementeEnlazada<String> preferencias) {
        this.preferencias = preferencias;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                '}';
    }
}
