package co.edu.uniquindio.red_social.clases.contenidos;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;

public class Calificacion {
    private int valoracion;
    private Estudiante usuario;
    private String id;
    private Contenido contenido;

    public Calificacion(int valoracion, Estudiante usuario, Contenido contenido) {
        this.valoracion = valoracion;
        this.usuario = usuario;
        this.contenido = contenido;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public Estudiante getUsuario() {
        return usuario;
    }

    public void setUsuario(Estudiante usuario) {
        this.usuario = usuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Contenido getContenido() {
        return contenido;
    }

    public void setContenido(Contenido contenido) {
        this.contenido = contenido;
    }
}
