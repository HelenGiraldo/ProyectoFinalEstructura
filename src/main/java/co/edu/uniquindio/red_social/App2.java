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
        red.getEstudiantes().show();
        System.out.println(red.getEstudiantes().size());
        System.out.println("---------------------------------");

        Estudiante estudiante = red.getEstudiantes().get(0);
        Estudiante estudiante2 = red.getEstudiantes().get(2);
        estudiante.getContactos().show();
        estudiante.enviarSolicitud(estudiante2);
        estudiante2.aceptarSolicitud(estudiante2.getSolicitudesRecibidas().get(0));
        estudiante.getChats().show();

    }
}
