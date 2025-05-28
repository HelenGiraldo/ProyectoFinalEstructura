package co.edu.uniquindio.red_social.util;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;

public class EstudianteActual {

    public static Estudiante usuarioActual;

    public static Estudiante getUsuarioActual() {
        return usuarioActual;
    }
    public static void setUsuarioActual(Estudiante usuarioActual) {
        EstudianteActual.usuarioActual = usuarioActual;
    }
    public static void limpiarUsuarioActual() {
        EstudianteActual.usuarioActual = null;
    }
    public static boolean isUsuarioActual() {
        return usuarioActual != null;
    }


}
