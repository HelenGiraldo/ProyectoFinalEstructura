package co.edu.uniquindio.red_social.util.grafo;

import co.edu.uniquindio.red_social.App3;
import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.contenidos.Calificacion;
import co.edu.uniquindio.red_social.clases.contenidos.Contenido;
import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

public class CreacionGrafo {

    RedSocial redSocial = RedSocial.getInstance();
    ListaSimplementeEnlazada<Arista> aristas = new ListaSimplementeEnlazada<>();
    ListaSimplementeEnlazada<GNodo> estudiantes = new ListaSimplementeEnlazada<>();
    static CreacionGrafo grafo;



    public static CreacionGrafo getInstance() {
        if (grafo == null) {
            grafo = new CreacionGrafo();
        }
        return grafo;
    }

    public void generarConexionesAutomaticas() {
        for (int i = 0; i < estudiantes.size(); i++) {
            GNodo estudiante1 = estudiantes.get(i);
            for (int j = i + 1; j < estudiantes.size(); j++) {
                GNodo estudiante2 = estudiantes.get(j);
                Arista arista = new Arista(estudiante1, estudiante2);

                // Solo crear una arista por par de nodos
                if (sonAmigos(estudiante1.estudiante, estudiante2.estudiante)) {
                    arista.amigos = true;
                } else if (tienenInteresesComunes(estudiante1.estudiante, estudiante2.estudiante)) {
                    arista.interesesComunes = true;
                } else if (tienenGruposComunes(estudiante1.estudiante, estudiante2.estudiante)) {
                    arista.gruposComunes = true;
                } else if (tienenValoracionesSimilares(estudiante1.estudiante, estudiante2.estudiante)) {
                    arista.valoracionesSimilares = true;
                }

                // Solo añadir si cumple al menos un criterio
                if (arista.amigos || arista.interesesComunes ||
                        arista.gruposComunes || arista.valoracionesSimilares) {
                    aristas.add(arista);
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

    public void crearNodos() {
        int x = 100, y = 100;
        for (Estudiante estudiante : redSocial.getEstudiantes()) {
            GNodo nodo = new GNodo(estudiante);
            nodo.x = x;
            nodo.y = y;
            estudiantes.add(nodo);
            x += 100;
            if (x > 700) {
                x = 100;
                y += 100;
            }
        }
    }

    public void crearGrafo() {
        crearNodos();
        generarConexionesAutomaticas();
        estudiantes.show();
       GrafoSwing.main();

    }

    public static void main(String[] args) {
        App3.iniciarDatos();
        CreacionGrafo creacionGrafo = CreacionGrafo.getInstance();
        creacionGrafo.crearGrafo();
    }


}
