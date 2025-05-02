package co.edu.uniquindio.red_social.clases.contenidos;

public class Calificacion {
    private int valoracion;
    private String comentario;
    private String usuario;

    public Calificacion(int valoracion, String comentario, String usuario) {
        this.valoracion = valoracion;
        this.comentario = comentario;
        this.usuario = usuario;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
