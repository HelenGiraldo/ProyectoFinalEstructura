package co.edu.uniquindio.red_social.clases.usuarios;

import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

import java.io.File;

public class Estudiante extends Usuario {
    private String id;
    private static int contador = 0;
    private ListaSimplementeEnlazada<Usuario> contactos;
    private ListaSimplementeEnlazada<String> preferencias;

    public Estudiante(String nombre, String correo, String contrasena, String fechaNacimiento, File fotoPerfil) {
        super(nombre, correo, contrasena, fechaNacimiento, fotoPerfil);
        this.id = String.valueOf(++contador);
        this.contactos = new ListaSimplementeEnlazada<>();
        this.preferencias = new ListaSimplementeEnlazada<>();
    }

    public boolean anadirContacto(Usuario contacto) {
        if (!contactos.contains(contacto)) {
            contactos.add(contacto);
            return true;
        }
        return false;
    }

    public boolean eliminarContacto(Usuario contacto) {
        if (contactos.contains(contacto)) {
            contactos.remove(contacto);
            return true;
        }
        return false;
    }

    public boolean anadirPreferencia(String preferencia) {
        if (!preferencias.contains(preferencia)) {
            preferencias.add(preferencia);
            return true;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Estudiante.contador = contador;
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
}
