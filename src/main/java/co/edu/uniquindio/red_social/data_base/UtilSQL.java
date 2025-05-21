package co.edu.uniquindio.red_social.data_base;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.contenidos.Calificacion;
import co.edu.uniquindio.red_social.clases.contenidos.Contenido;
import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.clases.social.SolicitudAmistad;
import co.edu.uniquindio.red_social.clases.usuarios.Administrador;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;

import java.io.File;
import java.sql.*;
import java.util.ResourceBundle;

public class UtilSQL {
    static ResourceBundle bundle = ResourceBundle.getBundle("sql");
    static String url = bundle.getString("url");
    static String user = bundle.getString("user");
    static String password = bundle.getString("password");
    static boolean save = true;

    public static int insertarEstudiante(Estudiante e) {
        if (!save){
            return -1;
        }
        int idGenerado = -1;
        // SQL para insertar un nuevo estudiante
        String sql = "INSERT INTO usuarios (nombre, apellido, correo, contrasena, imagenPerfil) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);

                // Crear un PreparedStatement para la inserción
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Establecer los valores de los parámetros
            stmt.setString(1, e.getNombre());
            stmt.setString(2, e.getApellido());
            stmt.setString(3, e.getEmail());
            stmt.setString(4, e.getContrasena());
            stmt.setString(5, e.getImagenPerfil().getPath());

            // Ejecutar la inserción
            stmt.executeUpdate();

            // Obtener el ID generado por la base de datos :D
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                idGenerado = generatedKeys.getInt(1);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return idGenerado;
    }

    public static void obtenerEstudiantes() {
        if (!save){
            return;
        }
        String sql = "SELECT id, nombre, apellido, correo, contrasena, imagenPerfil FROM usuarios";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    String id = rs.getString("id");
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    String correo = rs.getString("correo");
                    String contrasena = rs.getString("contrasena");
                    File imagenPerfil = new File(rs.getString("imagenPerfil"));

                    RedSocial.getInstance().crearEstudiante(id, nombre, apellido, correo, contrasena, imagenPerfil);
                }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean actualizarEstudiante(Estudiante e) {
        if (!save || e == null || e.getId() == null || e.getId().equals("-1")) {
            return false;
        }

        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, correo = ?, contrasena = ?, imagenPerfil = ? WHERE id = ?";
        boolean actualizado = false;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getNombre());
            stmt.setString(2, e.getApellido());
            stmt.setString(3, e.getEmail());
            stmt.setString(4, e.getContrasena());
            stmt.setString(5, e.getImagenPerfil().getPath());
            stmt.setString(6, e.getId());

            int filasAfectadas = stmt.executeUpdate();
            actualizado = filasAfectadas > 0;



        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return actualizado;
    }

    public static boolean eliminarEstudiante(Estudiante e) {
        if (!save || e == null || e.getId() == null || e.getId().equals("-1")) {
            return false;
        }

        String sql = "DELETE FROM usuarios WHERE id = ?";
        boolean eliminado = false;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getId());

            int filasAfectadas = stmt.executeUpdate();
            eliminado = filasAfectadas > 0;



        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return eliminado;
    }

    public static int insertarAdministrador(Administrador admin) {
        if (!save) {
            return -1;
        }
        int idGenerado = -1;
        String sql = "INSERT INTO administradores (nombre, apellido, correo, contrasena, imagenPerfil) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, admin.getNombre());
            stmt.setString(2, admin.getApellido());
            stmt.setString(3, admin.getEmail());
            stmt.setString(4, admin.getContrasena());
            stmt.setString(5, admin.getImagenPerfil().getPath());

            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                idGenerado = generatedKeys.getInt(1);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return idGenerado;
    }

    public static void obtenerAdministradores() {
        if (!save) {
            return;
        }
        String sql = "SELECT id, nombre, apellido, correo, contrasena, imagenPerfil FROM administradores";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String correo = rs.getString("correo");
                String contrasena = rs.getString("contrasena");
                File imagenPerfil = new File(rs.getString("imagenPerfil"));
                RedSocial.getInstance().crearAdministrador(id, nombre, apellido, correo, contrasena, imagenPerfil);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean actualizarAdministrador(Administrador admin) {
        if (!save || admin == null || admin.getId() == null || admin.getId().equals("-1")) {
            return false;
        }

        String sql = "UPDATE administradores SET nombre = ?, apellido = ?, correo = ?, contrasena = ?, imagenPerfil = ? WHERE id = ?";
        boolean actualizado = false;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getNombre());
            stmt.setString(2, admin.getApellido());
            stmt.setString(3, admin.getEmail());
            stmt.setString(4, admin.getContrasena());
            stmt.setString(5, admin.getImagenPerfil().getPath());
            stmt.setString(6, admin.getId());

            int filasAfectadas = stmt.executeUpdate();
            actualizado = filasAfectadas > 0;


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return actualizado;
    }

    public static boolean eliminarAdministrador(Administrador admin) {
        if (!save || admin == null || admin.getId() == null || admin.getId().equals("-1")) {
            return false;
        }

        String sql = "DELETE FROM administradores WHERE id = ?";
        boolean eliminado = false;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getId());

            int filasAfectadas = stmt.executeUpdate();
            eliminado = filasAfectadas > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return eliminado;
    }

    public static void cargarGrupos(){
        String sql = "SELECT id, nombre, descripcion FROM grupos";
    }

    public static void cargarRelacionesAmistad() {
        String sql = "SELECT id_user1, id_user2, estado, id_solicitante FROM amistades";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             ResultSet rs = conn.createStatement().executeQuery(sql)) {

            while (rs.next()) {
                String id1 = rs.getString("id_user1");
                String id2 = rs.getString("id_user2");
                String estado = rs.getString("estado");
                String idSolicitante = rs.getString("id_solicitante");

                Estudiante user1 = RedSocial.getInstance().obtenerEstudiantePorId(id1);
                Estudiante user2 = RedSocial.getInstance().obtenerEstudiantePorId(id2);
                Estudiante solicitante = RedSocial.getInstance().obtenerEstudiantePorId(idSolicitante);

                if (user1 != null && user2 != null && solicitante != null) {
                    if (estado.equals("confirmado")) {
                        user1.anadirContacto(user2);
                        user2.anadirContacto(user1);
                    } else if (estado.equals("pendiente")) {
                        Estudiante solicitado = user1.getId().equals(idSolicitante) ? user2 : user1;
                        solicitante.agregarSolicitud(solicitado);

                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar amistades", e);
        }
    }

    public static boolean crearRelacionAmistad(String idSolicitante, String idSolicitado, String estado) {
        if (!save) return false;

        String sql = "INSERT INTO amistades (id_user1, id_user2, estado, id_solicitante) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (idSolicitante.compareTo(idSolicitado) < 0) {
                stmt.setString(1, idSolicitante);
                stmt.setString(2, idSolicitado);
            } else {
                stmt.setString(1, idSolicitado);
                stmt.setString(2, idSolicitante);
            }

            stmt.setString(3, estado);
            stmt.setString(4, idSolicitante); // Guardamos el solicitante original

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear relación", e);
        }
    }

    public static boolean eliminarRelacionAmistad(String idUser1, String idUser2) {
        if (!save) {
            return false;
        }

        // Ordenar los IDs para asegurar que siempre se elimine la misma relación
        String idMenor = idUser1.compareTo(idUser2) < 0 ? idUser1 : idUser2;
        String idMayor = idUser1.compareTo(idUser2) < 0 ? idUser2 : idUser1;

        String sql = "DELETE FROM amistades WHERE id_user1 = ? AND id_user2 = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idMenor);
            stmt.setString(2, idMayor);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar relación de amistad", e);
        }
    }

    public static boolean actualizarEstadoAmistad(String idUser1, String idUser2, String nuevoEstado) {
        if (!save) {
            return false;
        }

        // Ordenar los IDs para asegurar que actualizamos correctamente
        String idMenor = idUser1.compareTo(idUser2) < 0 ? idUser1 : idUser2;
        String idMayor = idUser1.compareTo(idUser2) < 0 ? idUser2 : idUser1;

        String sql = "UPDATE amistades SET estado = ? WHERE id_user1 = ? AND id_user2 = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoEstado);
            stmt.setString(2, idMenor);
            stmt.setString(3, idMayor);

            int filasAfectadas = stmt.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar estado de amistad", e);
        }
    }

    public static boolean aceptarSolicitudAmistad(String idSolicitante, String idSolicitado) {
        return actualizarEstadoAmistad(idSolicitante, idSolicitado, "confirmado");
    }


    public static int insertarGrupo(Grupo grupo) {
        if (!save) {
            return -1;
        }

        String sql = "INSERT INTO grupos (nombre, descripcion, tipo, publico) VALUES (?, ?, ?, ?)";
        int idGenerado = -1;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, grupo.getNombre());
            stmt.setString(2, grupo.getDescripcion());
            stmt.setString(3, grupo.getTipoGrupo());
            stmt.setBoolean(4, grupo.isPublico());

            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                idGenerado = generatedKeys.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar grupo", e);
        }

        return idGenerado;
    }

    public static void obtenerGrupos() {
        if (!save) {
            return;
        }

        String sql = "SELECT id, nombre, descripcion, tipo, publico FROM grupos";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("id");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                String tipo = rs.getString("tipo");
                boolean publico = rs.getBoolean("publico");

                RedSocial.getInstance().crearGrupo(id, nombre, descripcion, tipo, publico);

            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener grupos", e);
        }
    }

    public static boolean actualizarGrupo(Grupo grupo) {
        if (!save || grupo == null || grupo.getId() == null) {
            return false;
        }

        String sql = "UPDATE grupos SET nombre = ?, descripcion = ?, tipo = ?, publico = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, grupo.getNombre());
            stmt.setString(2, grupo.getDescripcion());
            stmt.setString(3, grupo.getTipoGrupo());
            stmt.setBoolean(4, grupo.isPublico());
            stmt.setString(5, grupo.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar grupo", e);
        }
    }


    public static boolean eliminarGrupo(String idGrupo) {
        if (!save || idGrupo == null) {
            return false;
        }

        String sql = "DELETE FROM grupos WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idGrupo);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar grupo", e);
        }
    }

    public static void cargarMiembrosDeGrupos() {
        if (!save) {
            return;
        }

        String sql = "SELECT id_group, id_user FROM user_group";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String idGrupo = rs.getString("id_group");
                String idUsuario = rs.getString("id_user");

                Grupo grupo = RedSocial.getInstance().obtenerGrupoPorId(idGrupo);
                Estudiante estudiante = RedSocial.getInstance().obtenerEstudiantePorId(idUsuario);

                if (grupo != null && estudiante != null) {
                    grupo.agregarMiembro(estudiante);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar miembros de grupos", e);
        }
    }

    public static boolean agregarUsuarioAGrupo(String idUsuario, String idGrupo) {
        if (!save || idUsuario == null || idGrupo == null) {
            return false;
        }

        String sql = "INSERT INTO user_group (id_user, id_group) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idUsuario);
            stmt.setString(2, idGrupo);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al agregar usuario al grupo", e);
        }
    }

    public static boolean eliminarUsuarioDeGrupo(String idUsuario, String idGrupo) {
        if (!save || idUsuario == null || idGrupo == null) {
            return false;
        }

        String sql = "DELETE FROM user_group WHERE id_user = ? AND id_group = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idUsuario);
            stmt.setString(2, idGrupo);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar usuario del grupo", e);
        }
    }


    public static int agregarPublicacion(Contenido contenido) {
        if (!save || contenido == null || contenido.getAutor() == null) {
            return -1;
        }

        String sql = "INSERT INTO publicaciones (tipo_contenido, titulo, tema, descripcion, contenido, id_autor, id_grupo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int idGenerado = -1;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, contenido.getTipoContenido());
            stmt.setString(2, contenido.getTitulo());
            stmt.setString(3, contenido.getTema());
            stmt.setString(4, contenido.getDescripcion());
            stmt.setString(5, contenido.getContenido().getPath());
            stmt.setInt(6, Integer.parseInt(contenido.getAutor().getId()));

            if (contenido.getGrupo() != null) {
                stmt.setInt(7, Integer.parseInt(contenido.getGrupo().getId()));
            } else {
                stmt.setNull(7, Types.INTEGER);
            }

            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                idGenerado = generatedKeys.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return idGenerado;
    }

    public static boolean eliminarPublicacion(String idPublicacion) {
        if (!save || idPublicacion == null) {
            return false;
        }

        String sql = "DELETE FROM publicaciones WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(idPublicacion));
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void obtenerPublicaciones() {
        ListaSimplementeEnlazada<Contenido> lista = new ListaSimplementeEnlazada<>();
        if (!save) {
            return;
        }

        String sql = "SELECT id, tipo_contenido, titulo, tema, descripcion, contenido, id_autor, id_grupo FROM publicaciones";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("id");
                String tipoContenido = rs.getString("tipo_contenido");
                String titulo = rs.getString("titulo");
                String tema = rs.getString("tema");
                String descripcion = rs.getString("descripcion");
                File contenido = new File(rs.getString("contenido"));

                Estudiante autor = RedSocial.getInstance().obtenerEstudiantePorId(rs.getString("id_autor"));

                Grupo grupo = null;
                String idGrupo = rs.getString("id_grupo");
                if (idGrupo != null) {
                    grupo = RedSocial.getInstance().obtenerGrupoPorId(idGrupo);
                }

                Contenido pub = new Contenido(tipoContenido, titulo, tema, descripcion, autor, contenido, grupo);

                RedSocial.getInstance().agregarPublicacion(pub, idGrupo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static int agregarCalificacion(Calificacion calificacion) {
        if (!save || calificacion == null || calificacion.getUsuario() == null || calificacion.getContenido() == null) {
            return -1;
        }

        String sql = "INSERT INTO calificaciones (valoracion, autor, id_publicacion) VALUES (?, ?, ?)";
        int idGenerado = -1;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, calificacion.getValoracion());
            stmt.setInt(2, Integer.parseInt(calificacion.getUsuario().getId()));
            stmt.setInt(3, Integer.parseInt(calificacion.getContenido().getId()));

            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                idGenerado = generatedKeys.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return idGenerado;
    }

    public static boolean eliminarCalificacion(String idCalificacion) {
        if (!save || idCalificacion == null) {
            return false;
        }

        String sql = "DELETE FROM calificaciones WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(idCalificacion));
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void obtenerTodasLasCalificaciones() {
        if (!save) {
            return;
        }

        String sql = "SELECT c.id, c.valoracion, c.autor, c.id_publicacion FROM calificaciones c";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String idCalificacion = rs.getString("id");
                int valoracion = rs.getInt("valoracion");
                String idUsuario = rs.getString("autor");
                String idPublicacion = rs.getString("id_publicacion");

                // Obtener objetos relacionados
                Estudiante estudiante = RedSocial.getInstance().obtenerEstudiantePorId(idUsuario);
                Contenido contenido = RedSocial.getInstance().obtenerPublicacionPorId(idPublicacion);

                if (estudiante != null && contenido != null) {
                    Calificacion calificacion = new Calificacion(valoracion, estudiante, contenido);
                    calificacion.setId(idCalificacion);
                    contenido.agregarCalificacion(calificacion);

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todas las calificaciones", e);
        }


    }

}


