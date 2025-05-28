package co.edu.uniquindio.red_social.util;

import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

public class GrupoTabla {
    private String nombreGrupo;
    private String tipoGrupo;
    public static String NOMBRE_GRUPO = null;

    public GrupoTabla(String nombreGrupo, String tipoGrupo) {
        this.nombreGrupo = nombreGrupo;
        this.tipoGrupo = tipoGrupo;
    }

    // Getters
    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public String getTipoGrupo() {
        return tipoGrupo;
    }
}