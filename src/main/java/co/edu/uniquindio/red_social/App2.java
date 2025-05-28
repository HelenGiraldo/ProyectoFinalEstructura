package co.edu.uniquindio.red_social;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.social.Chat;
import co.edu.uniquindio.red_social.clases.social.Mensaje;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.data_base.UtilSQL;

import java.time.LocalDateTime;

public class App2 {

    public static void main(String[] args) {

        RedSocial red = RedSocial.getInstance("Think Together");
        UtilSQL.obtenerEstudiantes();
        UtilSQL.obtenerGrupos();
        UtilSQL.obtenerAdministradores();
        UtilSQL.obtenerPublicaciones();
        UtilSQL.obtenerTodasLasCalificaciones();
        UtilSQL.cargarRelacionesAmistad();
        UtilSQL.cargarMiembrosDeGrupos();
        UtilSQL.cargarGrupos();
        UtilSQL.obtenerTodosLosChats();
        UtilSQL.obtenerTodasLasPreferencias();
        red.getAdministradores().show();
        System.out.println(red.getEstudiantes().size());
        System.out.println("---------------------------------");

        Estudiante estudiante = red.getEstudiantes().get(1);
        estudiante.anadirPreferencia("cuca");
        Estudiante estudiante1 = red.getEstudiantes().get(4);
        estudiante1.anadirPreferencia("cuca");

    }
}
