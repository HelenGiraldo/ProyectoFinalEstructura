package co.edu.uniquindio.red_social.clases.social;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;

import java.time.LocalDate;

public class Mensaje {

    private String mensaje;
    private LocalDate fecha;
    private Estudiante usuarioRemitente;

    public Mensaje(String mensaje, LocalDate fecha, Estudiante usuarioRemitente) {
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.usuarioRemitente = usuarioRemitente;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estudiante getUsuarioRemitente() {
        return usuarioRemitente;
    }

    public void setUsuarioRemitente(Estudiante usuarioRemitente) {
        this.usuarioRemitente = usuarioRemitente;
    }
}
