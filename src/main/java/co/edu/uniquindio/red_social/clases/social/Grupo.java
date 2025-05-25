
package co.edu.uniquindio.red_social.clases.social;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.contenidos.Contenido;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.Usuario;
import co.edu.uniquindio.red_social.data_base.UtilSQL;
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
    public Grupo( String id, String nombre, String descripcion, String tipoGrupo, boolean publico) {
        this(nombre, descripcion, tipoGrupo, publico);
        this.id = id;
    }

    public boolean agregarMiembro( Estudiante miembro) {
        if (!miembros.contains(miembro)) {
            miembro.anadirGrupo(this);
            return miembros.add(miembro);
        }
        return false;
    }
    public boolean eliminarMiembro(Estudiante miembro) {
        if (miembro == null) return false;

        // Buscar por ID en lugar de por referencia de objeto
        for (Estudiante m : miembros) {
            if (m.getId().equals(miembro.getId())) {
                // Primero eliminar de la base de datos
                if (UtilSQL.eliminarUsuarioDeGrupo(miembro.getId(), this.getId())) {
                    // Luego eliminar de las estructuras en memoria
                    miembro.eliminarGrupo(this);
                    miembro.eliminarPublicacionGrupo(this);
                    return miembros.remove(m);
                }
                return false;
            }
        }
        return false;
    }

    public boolean esMiembro(Estudiante estudiante) {
        if (estudiante == null) return false;

        for (Estudiante m : miembros) {
            if (m.getId().equals(estudiante.getId())) {
                return true;
            }
        }
        return false;
    }


    public boolean agregarPublicacion(Contenido contenido) {
        if (contenido == null) return false;

        // Debug detallado
        System.out.println("[GRUPO] Agregando contenido ID: " + contenido.getId() +
                " al grupo: " + this.nombre);

        // Verificar si el contenido ya existe
        for (Contenido c : this.contenidos.recorrerInorden()) {
            if (c.getId().equals(contenido.getId())) {
                System.out.println("[GRUPO] El contenido ya existe en el grupo");
                return false;
            }
        }

        // Establecer la relación bidireccional
        contenido.setGrupo(this);

        // Insertar en el árbol del grupo
        boolean resultado = this.contenidos.add(contenido);
        System.out.println("[GRUPO] Resultado inserción: " + resultado +
                " | Total ahora: " + this.contenidos.recorrerInorden().size());

        return resultado;
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


        miembros.clear();
        contenidos.clear();
    }

    public boolean puedeUnirse(Estudiante estudiante) {
        return publico && !miembros.contains(estudiante);
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
        if (this.contenidos == null) {
            this.contenidos = new ArbolBinario<>();
            System.out.println("Inicializado árbol vacío para grupo: " + this.nombre);
        }
        return this.contenidos;
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

    @Override
    public String toString() {
        return this.nombre != null ? this.nombre : "Grupo sin nombre";
    }
}
