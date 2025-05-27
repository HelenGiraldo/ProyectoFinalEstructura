package co.edu.uniquindio.red_social.clases.social;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;

public class Solucion {
    private String id;
    private String texto;
    private Estudiante estudianteImplicado;

    public Solucion(String texto, Estudiante estudianteImplicado) {
        this.texto = texto;
        this.estudianteImplicado = estudianteImplicado;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Estudiante getEstudianteImplicado() {
        return estudianteImplicado;
    }

    public void setEstudianteImplicado(Estudiante estudianteImplicado) {
        this.estudianteImplicado = estudianteImplicado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
