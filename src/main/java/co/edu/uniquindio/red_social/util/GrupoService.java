package co.edu.uniquindio.red_social.util;

import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.data_base.UtilSQL;

import java.util.List;

public class GrupoService {
    private static GrupoService instancia;

    private GrupoService() {}

    public static GrupoService getInstance() {
        if (instancia == null) {
            instancia = new GrupoService();
        }
        return instancia;
    }

    public void agregarGrupo(Grupo grupo) {
        UtilSQL.insertarGrupo(grupo);
    }

    public List<Grupo> obtenerGrupos() {
        return UtilSQL.obtenerGrupos();
    }
}
