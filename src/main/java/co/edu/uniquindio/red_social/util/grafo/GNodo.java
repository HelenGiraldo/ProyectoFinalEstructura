package co.edu.uniquindio.red_social.util.grafo;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;

class GNodo {
    int x, y;
    String nombre;
    Estudiante estudiante;

    public GNodo(Estudiante estudiante) {
        this.estudiante = estudiante;
        this.nombre = estudiante.getNombre();
        this.x = 0;
        this.y = 0;
    }


    public GNodo(String nombre, int x, int y) {
        this.nombre = nombre;
        this.x = x;
        this.y = y;
    }

    public GNodo(String nombre, int x, int y, Estudiante estudiante) {
        this.nombre = nombre;
        this.x = x;
        this.y = y;
        this.estudiante = estudiante;
    }
}

