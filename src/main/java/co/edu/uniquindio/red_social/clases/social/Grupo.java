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

    /* * Método para crear un grupo con un ID generado automáticamente.
     * Este método es útil cuando se necesita un ID único para el grupo.
     */
    public boolean agregarMiembro( Estudiante miembro) {
        if (!miembros.contains(miembro)) {
            miembro.anadirGrupo(this);
            return miembros.add(miembro);
        }
        return false;
    }
    /* * Método para agregar un miembro al grupo.
     * Este método verifica si el miembro ya está en el grupo antes de agregarlo.
     *         * @param miembro El estudiante que se desea agregar al grupo.
     * @return true si el miembro fue agregado exitosamente, false si ya era miembro.
     */
    public boolean eliminarMiembro(Estudiante miembro) {
        if (miembro == null) return false;

        for (Estudiante m : miembros) {
            if (m.getId().equals(miembro.getId())) {
                if (UtilSQL.eliminarUsuarioDeGrupo(miembro.getId(), this.getId())) {
                    miembro.eliminarGrupo(this);
                    miembro.eliminarPublicacionGrupo(this);
                    return miembros.remove(m);
                }
                return false;
            }
        }
        return false;
    }

    /**
     * Método para verificar si un estudiante es miembro del grupo.
     * @param estudiante El estudiante a verificar.
     * @return true si el estudiante es miembro del grupo, false en caso contrario.
     */

    public boolean esMiembro(Estudiante estudiante) {
        if (estudiante == null) return false;

        for (Estudiante m : miembros) {
            if (m.getId().equals(estudiante.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método para agregar una publicación al grupo.
     * @param contenido El contenido a agregar.
     * @return true si la publicación fue agregada exitosamente, false si el contenido o tema es nulo.
     */

    public boolean agregarPublicacion(Contenido contenido) {
        if (contenido == null || contenido.getTema() == null) {
            System.out.println("[ERROR] Contenido o tema es nulo");
            return false;
        }

        this.contenidos.insertar(contenido);

        // Debug
        System.out.println("[GRUPO] Contenido agregado por tema: " + contenido.getTema());
        System.out.println("Árbol actualizado (ordenado por tema):");
        this.contenidos.mostrarArbolCompleto();

        return true;
    }


    /**
     * Método para eliminar una publicación del grupo.
     * @param contenido El contenido a eliminar.
     * @return true si la publicación fue eliminada exitosamente, false si no existe en el grupo.
     */
    public boolean eliminarPublicacion(Contenido contenido) {
        if (contenidos.contains(contenido)) {
            RedSocial.getInstance().eliminarPublicacion(contenido);
            return contenidos.remove(contenido);
        }
        return false;
    }

    /**
     * Método para eliminar el grupo y sus contenidos.
     * Este método elimina todos los miembros del grupo y sus publicaciones asociadas.
     */
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

    /**
     * Método para verificar si un estudiante puede unirse al grupo.
     * @param estudiante El estudiante que desea unirse.
     * @return true si el grupo es público y el estudiante no es miembro, false en caso contrario.
     */
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

