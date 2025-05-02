package co.edu.uniquindio.red_social.clases.interfaces;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.Usuario;

public interface AdministracionGrupo {
    public boolean crearGrupo(String nombreGrupo, String descripcion, String tipoGrupo);

    public boolean eliminarGrupo(String nombreGrupo);

    public boolean modificarGrupo(String nombreGrupo, String nuevoNombre, String nuevaDescripcion, String nuevoTipoGrupo);

    public boolean agregarMiembro(String nombreGrupo, Estudiante miembro);

    public boolean eliminarMiembro(String nombreGrupo, Estudiante miembro);
}
