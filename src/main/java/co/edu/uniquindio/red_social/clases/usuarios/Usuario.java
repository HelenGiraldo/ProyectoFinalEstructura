package co.edu.uniquindio.red_social.clases.usuarios;

import java.io.File;
import java.time.LocalDate;

public abstract class Usuario {
    protected String id;
    protected String nombre;
    protected String correo;
    protected String contrasena;
    protected File fotoPerfil;


    public Usuario(String nombre, String correo, String contrasena, File fotoPerfil) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.fotoPerfil = fotoPerfil;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public File getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(File fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
}
