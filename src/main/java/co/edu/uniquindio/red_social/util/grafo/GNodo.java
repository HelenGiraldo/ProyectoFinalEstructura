package co.edu.uniquindio.red_social.util.grafo;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;

class GNodo {
    int x, y;
    String nombre;
    Estudiante estudiante;

    public GNodo(Estudiante estudiante) {
        this.estudiante = estudiante;
        this.nombre = estudiante.getNombre();
        this.x = 100;
        this.y = 100;
    }


    public GNodo(String nombre, int x, int y) {
        this.nombre = nombre;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "GNodo{" +
                "nombre='" + nombre + '\'' +
                ", y=" + y +
                ", x=" + x +
                '}';
    }
}

