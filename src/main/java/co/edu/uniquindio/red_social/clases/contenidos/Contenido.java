package co.edu.uniquindio.red_social.clases.contenidos;

import java.io.File;
import java.time.LocalDateTime;

public class Contenido implements Comparable<Contenido> {
    String tipoContenido;
    File contenido;

    public Contenido(String tipoContenido, File contenido) {
        this.tipoContenido = tipoContenido;
        this.contenido = contenido;
    }

    public String getTipoContenido() {
        return tipoContenido;
    }

    public void setTipoContenido(String tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    public File getContenido() {
        return contenido;
    }

    public void setContenido(File contenido) {
        this.contenido = contenido;
    }

    @Override
    public int compareTo(Contenido o) {
        return 0;
    }
}
