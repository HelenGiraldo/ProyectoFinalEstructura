package co.edu.uniquindio.red_social.util.grafo;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;

class Arista {
    GNodo origen, destino;
    String etiqueta;
    int etiquetaX, etiquetaY;
    boolean amigos, amigosMutuos, interesesComunes, gruposComunes, valoracionesSimilares;

    public Arista(Estudiante e, Estudiante e2) {
        this.origen = new GNodo(e);
        this.destino = new GNodo(e2);
    }

    public Arista(GNodo origen, GNodo destino) {
        this.origen = origen;
        this.destino = destino;
        this.etiqueta = "N0";
    }

    public Arista(GNodo origen, GNodo destino, String etiqueta) {
        this.origen = origen;
        this.destino = destino;
        this.etiqueta = etiqueta;
        calcularPosicionEtiqueta();
    }

    public void calcularPosicionEtiqueta() {
        etiquetaX = (origen.x + destino.x) / 2;
        etiquetaY = (origen.y + destino.y) / 2;

        double angle = Math.atan2(destino.y - origen.y, destino.x - origen.x);
        etiquetaX += (int)(15 * Math.sin(angle));
        etiquetaY -= (int)(15 * Math.cos(angle));
    }

}