package co.edu.uniquindio.red_social.clases.contenidos;

public class Calificacion {
    private int valoracion;
    private String usuario;

    public Calificacion(int valoracion, String usuario) {
        this.valoracion = valoracion;
        this.usuario = usuario;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
