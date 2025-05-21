package co.edu.uniquindio.red_social;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.data_base.UtilSQL;

import java.io.File;

public class App2 {

    public static void main(String[] args) {

        RedSocial red = RedSocial.getInstance("Think Together");
        UtilSQL.obtenerEstudiantes();
        red.getEstudiantes().show();
        System.out.println(red.getEstudiantes().size());
        System.out.println("---------------------------------");

        Estudiante estudiante = red.getEstudiantes().get(0);
        Estudiante estudiante2 = red.getEstudiantes().get(1);



    }
}
