package co.edu.uniquindio.red_social.clases.interfaces;


import co.edu.uniquindio.red_social.clases.contenidos.Publicacion;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

public interface Reporte {
    ListaSimplementeEnlazada<Publicacion> obtenerContenidosMasValorados();
    ListaSimplementeEnlazada<Estudiante> obtenerEstudiantesConMasConexiones();

}
