package co.edu.uniquindio.red_social.clases.social;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.contenidos.Contenido;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.estructuras.ArbolBinario;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;


public class Grupo {
    private String nombre;
    private String descripcion;
    private String tipoGrupo;
    private boolean publico;
    private String id;
    private ArbolBinario<Contenido> contenidos;
    private ListaSimplementeEnlazada<Estudiante> miembros;

    public Grupo(String nombre,String descripcion, String tipoGrupo, boolean publico) {
        this.contenidos = new ArbolBinario<>();
        this.descripcion = descripcion;
        this.tipoGrupo = tipoGrupo;
        this.nombre = nombre;
        this.publico = publico;
        this.miembros = new ListaSimplementeEnlazada<>();
    }

    public boolean agregarMiembro( Estudiante miembro) {
        if (!miembros.contains(miembro)) {
            miembro.anadirGrupo(this);
            return miembros.add(miembro);
        }
        return false;
    }
    public boolean eliminarMiembro( Estudiante miembro) {
        if (miembros.contains(miembro)) {
            miembro.eliminarGrupo(this);
            miembro.eliminarPublicacionGrupo(this);
            return miembros.remove(miembro);
        }
        return false;
    }

    public boolean agregarPublicacion(Contenido contenido) {
        if (!contenidos.contains(contenido)) {
            RedSocial.getInstance().agregarPublicacion(contenido);
            return contenidos.add(contenido);
        }
        return false;
    }

    public boolean eliminarPublicacion(Contenido contenido) {
        if (contenidos.contains(contenido)) {
            RedSocial.getInstance().eliminarPublicacion(contenido);
            return contenidos.remove(contenido);
        }
        return false;
    }

    public void eliminarGrupo(){
        for(Estudiante miembro : miembros){
            miembro.eliminarGrupo(this);
        }

        RedSocial redSocial = RedSocial.getInstance();
        for(Contenido contenido : contenidos.recorrerInorden()){
            redSocial.eliminarPublicacion(contenido);
        }

        for(Estudiante miembro : miembros){
            miembro.eliminarGrupo(this);
        }

        miembros.clear();
        contenidos.clear();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArbolBinario<Contenido> getContenidos() {
        return contenidos;
    }

    public void setContenidos(ArbolBinario<Contenido> contenidos) {
        this.contenidos = contenidos;
    }

    public ListaSimplementeEnlazada<Estudiante> getMiembros() {
        return miembros;
    }

    public void setMiembros(ListaSimplementeEnlazada<Estudiante> miembros) {
        this.miembros = miembros;
    }

    public boolean isPublico() {
        return publico;
    }

    public void setPublico(boolean publico) {
        this.publico = publico;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoGrupo() {
        return tipoGrupo;
    }

    public void setTipoGrupo(String tipoGrupo) {
        this.tipoGrupo = tipoGrupo;
    }
}

