package co.edu.uniquindio.red_social.util.grafo;

import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;

class Arista {
    GNodo origen, destino;
    private String etiqueta;
    int etiquetaX, etiquetaY;
    boolean amigos, amigosMutuos, interesesComunes, gruposComunes, valoracionesSimilares;



    public Arista(GNodo origen, GNodo destino) {
        this.origen = origen;
        this.destino = destino;
        this.etiqueta = "N0";
    }


    public void calcularPosicionEtiqueta() {
        etiquetaX = (origen.x + destino.x) / 2;
        etiquetaY = (origen.y + destino.y) / 2;

        double angle = Math.atan2(destino.y - origen.y, destino.x - origen.x);
        etiquetaX += (int)(15 * Math.sin(angle));
        etiquetaY -= (int)(15 * Math.cos(angle));
    }

    public String getEtiqueta() {
        if(amigos) {
            return "Amigos";
        } else if(amigosMutuos) {
            return "Amigos mutuos";
        } else if(interesesComunes) {
            return "Intereses comunes";
        } else if(gruposComunes) {
            return "Grupos comunes";
        } else if(valoracionesSimilares) {
            return "Valoraciones similares";
        }
        return etiqueta;
    }

}