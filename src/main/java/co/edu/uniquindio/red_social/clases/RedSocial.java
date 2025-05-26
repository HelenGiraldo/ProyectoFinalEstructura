package co.edu.uniquindio.red_social.clases;

import co.edu.uniquindio.red_social.clases.contenidos.Contenido;
import co.edu.uniquindio.red_social.clases.interfaces.AdministracionAdministrador;
import co.edu.uniquindio.red_social.clases.interfaces.AdministracionEstudiante;
import co.edu.uniquindio.red_social.clases.interfaces.AdministracionGrupo;
import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.clases.social.SolicitudAyuda;
import co.edu.uniquindio.red_social.clases.usuarios.Administrador;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.data_base.UtilSQL;
import co.edu.uniquindio.red_social.estructuras.ArbolBinario;
import co.edu.uniquindio.red_social.estructuras.ColaDePrioridad;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;
import co.edu.uniquindio.red_social.util.Email;
import co.edu.uniquindio.red_social.util.EstudianteActual;
import java.io.File;
import java.util.Iterator;

/**
 * Clase que representa la red social.
 * Implementa las interfaces de administración de estudiantes, grupos y administradores.
 */

public class RedSocial implements AdministracionEstudiante, AdministracionGrupo, AdministracionAdministrador {
    private String nombre;
    private String claveAdministrador;
    private ListaSimplementeEnlazada<Estudiante> estudiantes;
    private ListaSimplementeEnlazada<Administrador> administradores;
    private ListaSimplementeEnlazada<Grupo> grupos;
    private ArbolBinario<Contenido> contenidos;
    private ColaDePrioridad<SolicitudAyuda> solicitudesAyuda;

    private static RedSocial redSocial;


    private RedSocial(String nombre) {
        this.nombre = nombre;
        this.estudiantes = new ListaSimplementeEnlazada<>();
        this.administradores = new ListaSimplementeEnlazada<>();
        this.claveAdministrador = "admin123";
        this.grupos = new ListaSimplementeEnlazada<>();
        this.contenidos = new ArbolBinario<>();
        this.solicitudesAyuda = new ColaDePrioridad<>();
    }

    /**
     * Método para obtener la instancia de la red social.
     *
     * @param nombre El nombre de la red social.
     * @return La instancia de la red social.
     */
    public static RedSocial getInstance(String nombre) {
        if (redSocial == null) {
            redSocial = new RedSocial(nombre);
        }
        return redSocial;
    }

    /**
     * Crea un nuevo estudiante en la red social.
     *
     * @param nombre      El nombre del estudiante.
     * @param apellido    El apellido del estudiante.
     * @param correo      El correo electrónico del estudiante.
     * @param contrasena  La contraseña del estudiante.
     * @param fotoPerfil  La foto de perfil del estudiante.
     * @return El nuevo estudiante creado, o null si ya existe un estudiante con el mismo correo.
     */
    @Override
    public Estudiante crearEstudiante(String nombre, String apellido, String correo, String contrasena, File fotoPerfil) {
        // Validaciones básicas
        if (nombre == null || nombre.isEmpty() ||
                apellido == null || apellido.isEmpty() ||
                correo == null || correo.isEmpty() ||
                contrasena == null || contrasena.isEmpty()) {
            System.err.println("Error: Campos requeridos faltantes");
            return null;
        }

        try {
            // Verifica que el archivo de imagen exista
            if (fotoPerfil == null || !fotoPerfil.exists()) {
                System.err.println("Error: Imagen de perfil no válida");
                return null;
            }

            return new Estudiante(nombre, apellido, correo, contrasena, fotoPerfil);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
        * Crea un nuevo estudiante en la red social.
        *
        * @param id          El ID del estudiante.
        * @param nombre      El nombre del estudiante.
        * @param apellido    El apellido del estudiante.
        * @param correo      El correo electrónico del estudiante.
        * @param contrasena  La contraseña del estudiante.
        * @param fotoPerfil  La foto de perfil del estudiante.
        * @return true si se creó correctamente, false en caso contrario.
     */
    public boolean crearEstudiante(String id, String nombre, String apellido, String correo, String contrasena, File fotoPerfil) {
        Estudiante nuevoEstudiante = new Estudiante(id, nombre, apellido, correo, contrasena, fotoPerfil);
        if (estudianteExisteCorreo(correo) == null) {
            return estudiantes.add(nuevoEstudiante);
        }
        return false;
    }

    /**
     * Elimina un estudiante de la red social.
     *
     * @param estudiante El estudiante a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    @Override
    public boolean eliminarEstudiante(Estudiante estudiante) {
        if (estudiantes.contains(estudiante)) {
            UtilSQL.eliminarEstudiante(estudiante);
            return estudiantes.remove(estudiante);
        }
        return false;
    }

    /**
     * Crea un nuevo administrador en la red social.
     *
     * @param nombre      El nombre del administrador.
     * @param apellido    El apellido del administrador.
     * @param correo      El correo electrónico del administrador.
     * @param contrasena  La contraseña del administrador.
     * @param fotoPerfil  La foto de perfil del administrador.
     * @return El nuevo administrador creado, o null si ya existe un administrador con el mismo correo.
     */
    @Override
    public Administrador crearAdministrador(String nombre, String apellido, String correo, String contrasena, File fotoPerfil) {
        Administrador nuevoAdministrador = new Administrador(nombre, apellido, correo, contrasena, fotoPerfil);
        if (administradorExisteCorreo(correo) == null) {
            boolean a = administradores.add(nuevoAdministrador);
            int id = UtilSQL.insertarAdministrador(nuevoAdministrador);
            nuevoAdministrador.setId(String.valueOf(id));
            Email.saludoBienvenida(correo, nombre + " " + apellido);
            return (a) ? nuevoAdministrador : null;
        }
        return null;
    }

    /**
     * Crea un nuevo administrador en la red social.
     *
     * @param id          El ID del administrador.
     * @param nombre      El nombre del administrador.
     * @param apellido    El apellido del administrador.
     * @param correo      El correo electrónico del administrador.
     * @param contrasena  La contraseña del administrador.
     * @param fotoPerfil  La foto de perfil del administrador.
     * @return true si se creó correctamente, false en caso contrario.
     */
    public boolean crearAdministrador(String id, String nombre, String apellido, String correo, String contrasena, File fotoPerfil) {
        Administrador nuevoAdministrador = new Administrador(id, nombre, apellido, correo, contrasena, fotoPerfil);
        if (administradorExisteCorreo(correo) == null) {
            return administradores.add(nuevoAdministrador);
        }
        return false;
    }

    /**
     * Elimina un administrador de la red social.
     *
     * @param administrador El administrador a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    @Override
    public boolean eliminarAdministrador(Administrador administrador) {

        if (administradores.contains(administrador)) {
            UtilSQL.eliminarAdministrador(administrador);
            return administradores.remove(administrador);
        }
        return false;
    }

    /**
     * Modifica un administrador existente.
     *
     * @param usuarioAntiguo El administrador a modificar.
     * @param usuarioNuevo   El nuevo administrador con los datos actualizados.
     * @return true si se modificó correctamente, false en caso contrario.
     */
    @Override
    public boolean modificarAdministrador(Administrador usuarioAntiguo, Administrador usuarioNuevo) {
        if (administradores.contains(usuarioAntiguo)) {
            usuarioAntiguo.setNombre(usuarioNuevo.getNombre());
            usuarioAntiguo.setEmail(usuarioNuevo.getEmail());
            usuarioAntiguo.setContrasena(usuarioNuevo.getContrasena());
            usuarioAntiguo.setImagenPerfil(usuarioNuevo.getImagenPerfil());
            usuarioNuevo.setId(usuarioAntiguo.getId());
            UtilSQL.actualizarAdministrador(usuarioAntiguo);
            return true;
        }
        return false;
    }

    /**
     * Modifica un estudiante existente.
     *
     * @param usuarioAntiguo El estudiante a modificar.
     * @param usuarioNuevo   El nuevo estudiante con los datos actualizados.
     * @return true si se modificó correctamente, false en caso contrario.
     */
    @Override
    public boolean modificarEstudiante(Estudiante usuarioAntiguo, Estudiante usuarioNuevo) {
        if (estudiantes.contains(usuarioAntiguo)) {
            usuarioAntiguo.setNombre(usuarioNuevo.getNombre());
            usuarioAntiguo.setEmail(usuarioNuevo.getEmail());
            usuarioAntiguo.setContrasena(usuarioNuevo.getContrasena());
            usuarioAntiguo.setImagenPerfil(usuarioNuevo.getImagenPerfil());
            usuarioNuevo.setId(usuarioAntiguo.getId());
            UtilSQL.actualizarEstudiante(usuarioAntiguo);
            return true;
        }
        return false;
    }

    /**
     * Verifica si un administrador existe en la red social por su correo electrónico.
     *
     * @param correo El correo electrónico del administrador.
     * @return El administrador si existe, null en caso contrario.
     */
    @Override
    public Administrador administradorExisteCorreo(String correo) {
        Iterator<Administrador> iterator = administradores.iterator();
        while (iterator.hasNext()) {
            Administrador administrador = iterator.next();
            if (administrador.getEmail().equals(correo)) {
                return administrador;
            }
        }
        return null;
    }

    /**
     * Verifica si un estudiante existe en la red social por su correo electrónico.
     *
     * @param correo El correo electrónico del estudiante.
     * @return El estudiante si existe, null en caso contrario.
     */
    @Override
    public Estudiante estudianteExisteCorreo(String correo) {
        Iterator<Estudiante> iterator = estudiantes.iterator();
        while (iterator.hasNext()) {
            Estudiante estudiante = iterator.next();
            if (estudiante.getEmail().equals(correo)) {
                return estudiante;
            }
        }
        return null;
    }

    /**
     * Crea un grupo en la red social.
     *
     * @param nombreGrupo   El nombre del grupo.
     * @param descripcion   La descripción del grupo.
     * @param tipoGrupo     El tipo de grupo (público o privado).
     * @param publico       Indica si el grupo es público o privado.
     * @return true si se creó correctamente, false en caso contrario.
     */



    @Override
    public Grupo crearGrupo(String nombreGrupo, String descripcion, String tipoGrupo, boolean publico) {
        Grupo nuevoGrupo = new Grupo(nombreGrupo, descripcion, tipoGrupo, publico);
        if (!grupos.contains(nuevoGrupo)) {
            nuevoGrupo.setId(String.valueOf(UtilSQL.insertarGrupo(nuevoGrupo)));
            return (grupos.add(nuevoGrupo)) ? nuevoGrupo : null;
        }
        return null;
    }

    /**
     * Verifica si un grupo existe en la red social por su nombre.
     *
     * @param nombreGrupo El nombre del grupo.
     * @return El grupo si existe, null en caso contrario.
     */
    public boolean crearGrupo(String id, String nombreGrupo, String descripcion, String tipoGrupo, boolean publico) {
        Grupo nuevoGrupo = new Grupo(id, nombreGrupo, descripcion, tipoGrupo, publico);
        if (grupos == null) {
            grupos = new ListaSimplementeEnlazada<>();
        }
        return grupos.add(nuevoGrupo);
    }

    /**
     * Elimina un grupo de la red social.
     *
     * @param grupo El grupo a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    @Override
    public boolean eliminarGrupo(Grupo grupo) {
        grupo.eliminarGrupo();
       UtilSQL.eliminarGrupo(grupo.getId());
        return grupos.remove(grupo);
    }

    /**
     * Modifica un grupo existente.
     *
     * @param grupoAntiguo El grupo a modificar.
     * @param grupoNuevo   El nuevo grupo con los datos actualizados.
     * @return true si se modificó correctamente, false en caso contrario.
     */
    @Override
    public boolean modificarGrupo(Grupo grupoAntiguo, Grupo grupoNuevo) {
        if (grupos.contains(grupoAntiguo)) {
            grupoAntiguo.setNombre(grupoNuevo.getNombre());
            grupoAntiguo.setDescripcion(grupoNuevo.getDescripcion());
            grupoAntiguo.setTipoGrupo(grupoNuevo.getTipoGrupo());
            grupoAntiguo.setPublico(grupoNuevo.isPublico());
            grupoNuevo.setId(grupoAntiguo.getId());
            UtilSQL.actualizarGrupo(grupoNuevo);
            return true;
        }
        return false;
    }

    /**
     * Agrega un miembro a un grupo.
     *
     * @param grupo  El grupo al que se agregará el miembro.
     * @param miembro El miembro a agregar.
     * @return true si se agregó correctamente, false en caso contrario.
     */
    @Override
    public boolean agregarMiembro(Grupo grupo, Estudiante miembro) {
        return grupo.agregarMiembro(miembro);
    }


    /**
     * Elimina un miembro de un grupo.
     *
     * @param grupo  El grupo del que se eliminará el miembro.
     * @param miembro El miembro a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    @Override
    public boolean eliminarMiembro(Grupo grupo, Estudiante miembro) {
        return grupo.eliminarMiembro(miembro);
    }

    /**
     * Inicia sesión en la red social.
     *
     * @param correo    El correo electrónico del estudiante.
     * @param contrasena La contraseña del estudiante.
     * @return true si el inicio de sesión fue exitoso, false en caso contrario.
     */
    public boolean iniciarSesion(String correo, String contrasena) {
        Estudiante estudiante = estudianteExisteCorreo(correo);
        if (estudiante != null && estudiante.getContrasena().equals(contrasena)) {
            EstudianteActual.setUsuarioActual(estudiante);
            return true;
        }
        return false;
    }

    /**
     * Agrega una publicación a la red social.
     *
     * @param contenido La publicación a agregar.
     * @return true si se agregó correctamente, false en caso contrario.
     */
    public void agregarPublicacion(Contenido contenido) {
        // Verificar si ya existe una publicación con ese ID
        if (obtenerPublicacionPorId(contenido.getId()) != null) {
            throw new IllegalStateException("Ya existe una publicación con ID: " + contenido.getId());
        }
        this.contenidos.add(contenido);
    }


    public boolean agregarPublicacion(Contenido contenido, String id) {
        if (contenido == null) return false; // Cambiado para retornar boolean

        // Verificar que no se modifique el ID
        String idOriginal = contenido.getId();

        // Insertar en el árbol principal
        this.contenidos.insertar(contenido);

        // Si el ID cambió, revertirlo
        if (!idOriginal.equals(contenido.getId())) {
            System.err.println("ADVERTENCIA: Se intentó modificar el ID de " + idOriginal + " a " + contenido.getId());
            contenido.setId(idOriginal); // Revertir el cambio
        }

        if(!contenidos.contains(contenido)) {
            contenido.setId(id);
            contenidos.add(contenido);
            contenido.getAutor().publicarContenido(contenido, contenido.getGrupo(), id);
            return true;
        }
        return false;
    }

    /**
     * Elimina una publicación de la red social.
     *
     * @param contenido La publicación a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    public boolean eliminarPublicacion(Contenido contenido) {
        if (contenidos.contains(contenido)) {
            UtilSQL.eliminarPublicacion(contenido.getId());
            contenido.getAutor().getContenidos().remove(contenido);
            contenido.getGrupo().getContenidos().remove(contenido);
            return contenidos.remove(contenido);
        }
        return false;
    }

    /**
     * Agrega una solicitud de ayuda a la cola de prioridad.
     *
     * @param mensaje   El mensaje de la solicitud de ayuda.
     * @param estudiante El estudiante que realiza la solicitud.
     * @param titulo    El título de la solicitud de ayuda.
     * @param prioridad La prioridad de la solicitud (alta, media, baja).
     * @return La solicitud de ayuda agregada.
     */

    public SolicitudAyuda agregarSolicitudAyuda(String mensaje, Estudiante estudiante, String titulo, String prioridad) {
       int prioridadInt = 0;
        if (prioridad.equalsIgnoreCase("normal")) {
            prioridadInt = 1;
        } else if (prioridad.equalsIgnoreCase("urgente")) {
            prioridadInt = 2;
        } else if (prioridad.equalsIgnoreCase("muy urgente")) {
            prioridadInt = 3;
        }
        SolicitudAyuda solicitudAyuda = new SolicitudAyuda(mensaje, estudiante, titulo, prioridad);
         solicitudesAyuda.add(solicitudAyuda, prioridadInt);
         UtilSQL.crearSolicitudAyuda(solicitudAyuda);
         return solicitudAyuda;
    }

    /**
     * Elimina una solicitud de ayuda de la cola de prioridad.
     *
     * @param solicitudAyuda La solicitud de ayuda a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    public boolean eliminarSolicitudAyuda(SolicitudAyuda solicitudAyuda) {
        if (solicitudesAyuda.contains(solicitudAyuda)) {
            UtilSQL.eliminarSolicitudAyuda(solicitudAyuda.getId());
            return solicitudesAyuda.remove(solicitudAyuda);
        }
        return false;
    }

    /**
     * Obtiene un estudiante por su ID.
     *
     * @param id El ID del estudiante.
     * @return El estudiante correspondiente al ID, o null si no existe.
     */
    public Estudiante obtenerEstudiantePorId(String id) {
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getId().equals(id)) {
                return estudiante;
            }
        }
        return null;
    }

    /**
     * Obtiene un administrador por su ID.
     *
     * @param id El ID del administrador.
     * @return El administrador correspondiente al ID, o null si no existe.
     */
    public Grupo obtenerGrupoPorId(String id) {
        for (Grupo g : this.grupos) {
            if (g.getId().equals(id)) {
                // Devuelve la instancia EXACTA que está en la lista principal
                return g;
            }
        }
        return null;
    }

    public Contenido obtenerPublicacionPorId(String idPublicacion) {
        for (Estudiante estudiante : estudiantes) {
            for (Contenido contenido : estudiante.getContenidos().recorrerInorden()) {
                if (contenido.getId().equals(idPublicacion)) {
                    return contenido;
                }
            }
        }
        return null;
    }

    public boolean existePublicacion(String id) {
        return obtenerPublicacionPorId(id) != null;
    }

    public void agregarPublicacionSinValidarId(Contenido contenido) {
        this.contenidos.add(contenido);
    }

    public boolean agregarAdministrador(Administrador admin) {
        int idGenerado = UtilSQL.insertarAdministrador(admin);
        if (idGenerado > 0) {
            admin.setId(String.valueOf(idGenerado));
            return administradores.add(admin);
        }
        return false;
    }


    public void limpiarPublicaciones() {
        this.contenidos.clear();
    }




    // Getters y Setters


    public static RedSocial getInstance() {
        return redSocial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ListaSimplementeEnlazada<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(ListaSimplementeEnlazada<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public ListaSimplementeEnlazada<Administrador> getAdministradores() {
        return administradores;
    }

    public void setAdministradores(ListaSimplementeEnlazada<Administrador> administradores) {
        this.administradores = administradores;
    }

    public static RedSocial getRedSocial() {
        return redSocial;
    }

    public static void setRedSocial(RedSocial redSocial) {
        RedSocial.redSocial = redSocial;
    }

    public ListaSimplementeEnlazada<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(ListaSimplementeEnlazada<Grupo> grupos) {
        this.grupos = grupos;
    }

    public String getClaveAdministrador() {
        return claveAdministrador;
    }

    public void setClaveAdministrador(String claveAdministrador) {
        this.claveAdministrador = claveAdministrador;
    }

    public ArbolBinario<Contenido> getContenidos() {
        return contenidos;
    }

    public void setContenidos(ArbolBinario<Contenido> contenidos) {
        this.contenidos = contenidos;
    }

    public ColaDePrioridad<SolicitudAyuda> getSolicitudesAyuda() {
        return solicitudesAyuda;
    }

    public void setSolicitudesAyuda(ColaDePrioridad<SolicitudAyuda> solicitudesAyuda) {
        this.solicitudesAyuda = solicitudesAyuda;
    }



}