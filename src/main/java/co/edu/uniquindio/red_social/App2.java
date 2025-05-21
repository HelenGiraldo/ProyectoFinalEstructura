package co.edu.uniquindio.red_social;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.social.Chat;
import co.edu.uniquindio.red_social.clases.social.Mensaje;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.data_base.UtilSQL;

import java.io.File;
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

        Estudiante estudiante = red.getEstudiantes().get(1);
        Estudiante estudiante2 = red.getEstudiantes().get(2);
        estudiante.enviarSolicitud(estudiante2);
        estudiante2.aceptarSolicitud(estudiante2.getSolicitudesRecibidas().getFirst());
        estudiante.getContactos().show();
        estudiante2.getChats().show();
        Chat chat = estudiante.getChats().getFirst();
        chat.enviarMensaje(new Mensaje("Hola", LocalDateTime.now(), estudiante, chat));


    }
}
