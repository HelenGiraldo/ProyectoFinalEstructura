package co.edu.uniquindio.red_social.pruebas;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        RedSocial redSocial = new RedSocial("Red Social");
        ListaSimplementeEnlazada<Estudiante> estudiantes = redSocial.getEstudiantes();
        estudiantes.add(new Estudiante("Juan", "juan@gmail.com", "123", LocalDate.now(), null));
        System.out.println(redSocial.usuarioExiste("juan@gmail.com"));
        System.out.println(redSocial.usuarioExiste("helen@gmail.com"));

    }
}
