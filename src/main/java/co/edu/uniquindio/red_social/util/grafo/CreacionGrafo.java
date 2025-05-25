package co.edu.uniquindio.red_social.util.grafo;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.contenidos.Calificacion;
import co.edu.uniquindio.red_social.clases.contenidos.Contenido;
import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

public class CreacionGrafo {

    RedSocial redSocial = RedSocial.getInstance();
    ListaSimplementeEnlazada<Arista> aristas = new ListaSimplementeEnlazada<>();

    public Arista agregarConexion(Estudiante estudiante1, Estudiante estudiante2) {
        if (estudiante1 != null && estudiante2 != null) {
            Arista arista = new Arista(estudiante1, estudiante2);
            aristas.add(arista);
            return arista;
        }
        return null;
    }

    public void generarConexionesAutomaticas() {
        ListaSimplementeEnlazada<Estudiante> estudiantes = redSocial.getEstudiantes();
        for (int i = 0; i < estudiantes.size(); i++) {
            Estudiante estudiante1 = estudiantes.get(i);
            for (int j = i + 1; j < estudiantes.size(); j++) {
                Estudiante estudiante2 = estudiantes.get(j);

                if (!sonAmigos(estudiante1, estudiante2)) {
                    // Conexión por intereses comunes
                    if (tienenInteresesComunes(estudiante1, estudiante2)) {
                        agregarConexion(estudiante1, estudiante2);
                    }

                    // Conexión por grupos de estudio comunes
                    if (tienenGruposComunes(estudiante1, estudiante2)) {
                        agregarConexion(estudiante1, estudiante2);
                    }

                    // Conexión por valoraciones similares
                    if (tienenValoracionesSimilares(estudiante1, estudiante2)) {
                        agregarConexion(estudiante1, estudiante2);
                    }
                }else{
                    Arista arista = agregarConexion(estudiante1, estudiante2);
                    arista.amigos = true;
                }
            }
        }
    }


    private boolean sonAmigos(Estudiante estudiante1, Estudiante estudiante2) {
        for(Estudiante estudiante : estudiante1.getContactos()) {
            if (estudiante.equals(estudiante2)) {
                return true;
            }
        }
        return false;
    }

    private boolean amigosMutuos(Estudiante e1, Estudiante e2) {
        for (Estudiante amigo : e1.getContactos()) {
            for(Estudiante amigo2 : e2.getContactos()) {
                if (amigo.equals(e2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean tienenInteresesComunes(Estudiante e1, Estudiante e2) {
        for (String interes : e1.getPreferencias()) {
            if (e2.getPreferencias().contains(interes)) {
                return true;
            }
        }
        return false;
    }

    private boolean tienenGruposComunes(Estudiante e1, Estudiante e2) {
        for (Grupo grupo : e1.getGrupos()) {
            if (e2.getGrupos().contains(grupo)) {
                return true;
            }
        }
        return false;
    }

    private boolean tienenValoracionesSimilares(Estudiante e1, Estudiante e2) {
        ListaSimplementeEnlazada<Contenido> contenidos = new ListaSimplementeEnlazada<>();

        for(Contenido contenido : redSocial.getContenidos().recorrerInorden()) {
            for(Calificacion calificacion : contenido.getCalificaciones()) {
                if(calificacion.getUsuario().equals(e1)) {
                    contenidos.add(calificacion.getContenido());
                }
            }
        }
        for(Contenido contenido: contenidos) {
            for(Calificacion calificacion : contenido.getCalificaciones()) {
                if(calificacion.getUsuario().equals(e2)) {
                    return true;
                }
            }
        }
        return false;
    }
}
