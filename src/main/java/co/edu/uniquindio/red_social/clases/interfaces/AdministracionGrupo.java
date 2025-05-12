package co.edu.uniquindio.red_social.clases.interfaces;

import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.Usuario;

public interface AdministracionGrupo {
    public boolean crearGrupo(String nombreGrupo, String descripcion, String tipoGrupo, boolean publico);

    public boolean eliminarGrupo(Grupo grupo);

    public boolean modificarGrupo(Grupo grupoAntiguo, Grupo grupoNuevo);

    public boolean agregarMiembro(Grupo grupo, Estudiante miembro);

    public boolean eliminarMiembro(Grupo grupo, Estudiante miembro);
}
