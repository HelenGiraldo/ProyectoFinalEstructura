package co.edu.uniquindio.red_social.util.grafo;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;

public class GNodo {
    int x, y;
    String nombre;
    Estudiante estudiante;

    public GNodo(Estudiante estudiante) {
        this.estudiante = estudiante;
        this.nombre = estudiante.getId()+" "+estudiante.getNombre();
        this.x = 100;
        this.y = 100;
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

