package co.edu.uniquindio.red_social.util;

import co.edu.uniquindio.red_social.clases.social.Grupo;

import java.util.ArrayList;
import java.util.List;

public class GrupoService {
    private static GrupoService instancia;
    private final List<Grupo> listaGrupos = new ArrayList<>();

    private GrupoService() {}

    public static GrupoService getInstance() {
        if (instancia == null) {
            instancia = new GrupoService();
        }
        return instancia;
    }

    public void agregarGrupo(Grupo grupo) {
        listaGrupos.add(grupo);
    }

    public List<Grupo> obtenerGrupos() {
        return listaGrupos;
    }
}
