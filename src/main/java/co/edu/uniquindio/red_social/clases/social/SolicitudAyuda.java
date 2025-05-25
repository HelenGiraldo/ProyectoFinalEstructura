package co.edu.uniquindio.red_social.clases.social;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;

/**
 * Clase que representa una solicitud de ayuda en la red social.
 * Contiene información sobre el mensaje, el estudiante que la realiza,
 * el título, la prioridad y el estado de la solicitud.
 */
public class SolicitudAyuda {

    private String mensaje;
    private Estudiante estudiante;
    private String id;
    private String titulo;
    private String prioridad;
    private String estado;

    public SolicitudAyuda(String mensaje, Estudiante estudiante, String titulo, String prioridad) {
        this.mensaje = mensaje;
        this.estudiante = estudiante;
        this.titulo = titulo;
        this.prioridad = prioridad;
        this.estado = "Pendiente";
    }

    public SolicitudAyuda(String mensaje, Estudiante estudiante, String titulo, String prioridad, String estado) {
        this.mensaje = mensaje;
        this.estudiante = estudiante;
        this.titulo = titulo;
        this.prioridad = prioridad;
        this.estado = estado;
    }

    // Getters y Setters
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
