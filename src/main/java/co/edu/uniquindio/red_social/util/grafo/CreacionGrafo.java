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

    public ListaSimplementeEnlazada<Estudiante> recomedarEstudiantes(Estudiante estudiante){
        ListaSimplementeEnlazada<Estudiante> sugerecnias = new ListaSimplementeEnlazada<>();
        for(Estudiante est: RedSocial.getInstance().getEstudiantes()){
            if(sonAmigos(est,estudiante)||est.equals(estudiante)){
                continue;
            }
            if(amigosMutuos(est,estudiante)){
                sugerecnias.add(est);
            }else
            if(tienenInteresesComunes(est,estudiante)){
                sugerecnias.add(est);
            }else if(tienenGruposComunes(est,estudiante)){
                sugerecnias.add(est);
            }else if(tienenValoracionesSimilares(estudiante, est)){
                sugerecnias.add(est);
            }

        }
        return sugerecnias;

    }

    public ListaSimplementeEnlazada<Estudiante> conexionesEstudiante(Estudiante estudiante){
        ListaSimplementeEnlazada<Estudiante> sugerecnias = new ListaSimplementeEnlazada<>();
        for(Estudiante est: RedSocial.getInstance().getEstudiantes()){
            if(est.equals(estudiante)){
                continue;
            }
            if(sonAmigos(est,estudiante)){
                sugerecnias.add(est);
            }else
            if(amigosMutuos(est,estudiante)){
                sugerecnias.add(est);
            }else
            if(tienenInteresesComunes(est,estudiante)){
                sugerecnias.add(est);
            }else if(tienenGruposComunes(est,estudiante)){
                sugerecnias.add(est);
            }else if(tienenValoracionesSimilares(estudiante, est)){
                sugerecnias.add(est);
            }

        }
        return sugerecnias;

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
            for(Estudiante amigo2 : amigo.getContactos()) {
                if (amigo2.equals(e2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean tienenInteresesComunes(Estudiante e1, Estudiante e2) {
        for (String interes : e1.getPreferencias()) {
            for (String pref : e2.getPreferencias()) {
                if (interes.equals(pref)) {
                    return true;
                }
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
        int centroX = 400;  // Centro del panel
        int centroY = 300;
        int panelMargin = 80; // Margen mínimo

        ListaSimplementeEnlazada<Estudiante> listaEstudiantes = new ListaSimplementeEnlazada<>( redSocial.getEstudiantes());
        int totalEstudiantes = listaEstudiantes.size();

        // Calcular radio automáticamente basado en el número de nodos
        int radio = calcularRadioOptimo(totalEstudiantes, centroX, centroY, panelMargin);

        double anguloEntreNodos = 2 * Math.PI / totalEstudiantes;
        double anguloInicial = -Math.PI/2; // Empezar desde arriba (12 o'clock)

        for (int i = 0; i < totalEstudiantes; i++) {
            Estudiante estudiante = listaEstudiantes.get(i);
            GNodo nodo = new GNodo(estudiante);

            double angulo = anguloInicial + i * anguloEntreNodos;
            nodo.x = centroX + (int)(radio * Math.cos(angulo));
            nodo.y = centroY + (int)(radio * Math.sin(angulo));

            estudiantes.add(nodo);
        }
    }

    private int calcularRadioOptimo(int numNodos, int centroX, int centroY, int margin) {
        // Radio mínimo basado en el tamaño del panel
        int maxWidth = Math.min(centroX, centroY) - margin;

        // Radio basado en el número de nodos (ajustar según necesidades)
        int radioBase = 80 + (int)(Math.sqrt(numNodos) * 20);

        return Math.min(radioBase, maxWidth);
    }


    public void crearGrafo() {
        crearNodos();
        generarConexionesAutomaticas();
        estudiantes.show();
        CreacionGrafo creacionGrafo = CreacionGrafo.getInstance();
       GrafoSwing.main();

    }

    public static void main(String[] args) {
        App3.iniciarDatos();
        CreacionGrafo creacionGrafo = CreacionGrafo.getInstance();
        creacionGrafo.crearGrafo();
    }


}
