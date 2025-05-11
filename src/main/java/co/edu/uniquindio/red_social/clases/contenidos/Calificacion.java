package co.edu.uniquindio.red_social.clases.contenidos;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;

public class Calificacion {
    private int valoracion;
    private Estudiante usuario;

    public Calificacion(int valoracion, Estudiante usuario) {
        this.valoracion = valoracion;
        this.usuario = usuario;
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
}
