package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.contenidos.Calificacion;
import co.edu.uniquindio.red_social.clases.contenidos.Contenido;
import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.clases.usuarios.Administrador;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import co.edu.uniquindio.red_social.clases.usuarios.Usuario;
import co.edu.uniquindio.red_social.data_base.UtilSQL;
import co.edu.uniquindio.red_social.estructuras.ArbolBinario;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;
import co.edu.uniquindio.red_social.util.GrupoContenidoViewUtil;
import co.edu.uniquindio.red_social.util.GrupoService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static co.edu.uniquindio.red_social.data_base.UtilSQL.cargarGrupos;

public class GruposDeEstudioController {

    @FXML
    private HBox HboxAvatarNombre1;

    @FXML
    private HBox HboxAvatarNombre2;

    @FXML
    private HBox HboxTusContendiosContenido;

    @FXML
    private ToggleButton InicioButton;

    @FXML
    private Label LabelTipo;

    @FXML
    private Label LabelTotalPublicados;

    @FXML
    private Label LabelTusContenidos;

    @FXML
    private Label LabelTusContenidosContenido;

    @FXML
    private Label LabelUser3chat;

    @FXML
    private Label LabelValoracion;

    @FXML
    private ToggleButton MensajesButton;

    @FXML
    private ToggleButton SolicitudesDeAyudaButton;

    @FXML
    private ToggleButton SugerenciasButton;

    @FXML
    private Label TextFieldTitle;

    @FXML
    private VBox TuscontendiosVBox;

    @FXML
    private VBox VBoxTusContenidosContenido;

    @FXML
    private Button botonBuscar;

    @FXML
    private ToggleButton buttonGrupo1;

    @FXML
    private ToggleButton buttonGrupo2;

    @FXML
    private Button buttonGruposDisponibles;

    @FXML
    private Button buttonMisGrupos;

    @FXML
    private Button buttonSalir;

    @FXML
    private Button buttonUnirse;

    @FXML
    private TextField campoBusqueda;

    @FXML
    private VBox chatListVBoxFondoDos;

    @FXML
    private VBox chatSpace;

    @FXML
    private ToggleButton configuracionPerfilButton;

    @FXML
    private Label contenidosLabel;

    @FXML
    private Label grupo1Label;

    @FXML
    private Label grupoActualLabel;

    @FXML
    private ToggleButton gruposEstudioButton;

    @FXML
    private Label gruposEstudioLabel;

    @FXML
    private VBox hBoxDerecha;

    @FXML
    private ImageView imagenPerfil;

    @FXML
    private ToggleButton misContenidosButton;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPaneContenedorContenidos;

    @FXML
    private VBox contenedorGrupos;


    @FXML
    private VBox gruposVBox;

    @FXML
    private Button calificarButtton;


    private ToggleGroup grupoGrupos = new ToggleGroup();


    private Estudiante usuarioActual;

    private boolean mostrandoMisGrupos = true;

    private Contenido contenidoSeleccionado;




    @FXML
    private void initialize() {
        calificarButtton.setDisable(true);

        calificarButtton.setOnAction(this::calificarContenido);
        if (usuarioActual != null) {
            cargarGrupos(usuarioActual, true);
        }

        Platform.runLater(() -> {
            if (imagenPerfil != null) {
                double radius = imagenPerfil.getFitWidth() / 2;
                Circle clip = new Circle(radius, radius, radius);
                imagenPerfil.setClip(clip);
            }
        });

        PerfilUsuario perfil = PerfilUsuario.getInstancia();


        perfil.imagenPerfilProperty().addListener((obs, oldImg, newImg) -> {
            if (newImg != null) {
                imagenPerfil.setImage(newImg);
            }
        });

        System.out.println("Imagen de perfil: " + perfil.getImagenPerfil());
        // Mostrar imagen actual si ya existe
        if (perfil.getImagenPerfil() != null) {
            imagenPerfil.setImage(perfil.getImagenPerfil());
        }
        File file = PerfilUsuario.getUsuarioActual().getImagenPerfil();
        Image imagen = new Image(file.toURI().toString());
        imagenPerfil.setImage(imagen);
    }


    public void setUsuarioActual(Estudiante usuario) {
        this.usuarioActual = usuario;
        cargarGrupos(usuario, true);
    }

    private void cargarGrupos(Estudiante estudiante, boolean mostrarMisGrupos) {
        if (usuarioActual == null) {
            mostrarAlerta("Error", "No hay usuario autenticado");
            return;
        }

        // Limpiar la interfaz
        gruposVBox.getChildren().clear();
        limpiarContenido();
        deshabilitarBotonesAccion();
        grupoActualLabel.setText("");

        // Debug: Mostrar estado antes de cargar
        System.out.println("\n=== DEBUG PRE-CARGA ===");
        System.out.println("Grupos en memoria antes de cargar: " + RedSocial.getInstance().getGrupos().size());

        // Recargar datos desde la base de datos
        try {
            // 1. Limpiar datos existentes
            RedSocial.getInstance().getGrupos().clear();

            // 2. Cargar grupos básicos
            UtilSQL.obtenerGrupos();

            // Debug: Ver grupos recién cargados
            System.out.println("Grupos cargados desde BD: " + RedSocial.getInstance().getGrupos().size());
            for (Grupo g : RedSocial.getInstance().getGrupos()) {
                System.out.println(" - " + g.getNombre() + " (ID: " + g.getId() + ")");
            }

            // 3. Cargar miembros para cada grupo
            UtilSQL.cargarMiembrosDeGrupos();

            // 4. Cargar contenidos para cada grupo
            for (Grupo grupo : RedSocial.getInstance().getGrupos()) {
                ListaSimplementeEnlazada<Contenido> contenidos = UtilSQL.cargarContenidosDelGrupo(grupo.getId());
                ArbolBinario<Contenido> arbolContenidos = grupo.getContenidos(); // Obtener el árbol existente
                arbolContenidos.clear(); // Limpiar el árbol si es necesario

// Insertar todos los contenidos en el árbol
                for (Contenido contenido : contenidos) {
                    arbolContenidos.insertar(contenido);
                }

                // Debug por grupo
                System.out.println("\nGrupo: " + grupo.getNombre());
                System.out.println("Miembros: " + grupo.getMiembros().size());
                System.out.println("Contenidos cargados: " + contenidos.size());
                grupo.getContenidos().mostrarArbolCompleto(); // Mostrar estructura del árbol
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron cargar los grupos desde la base de datos");
            e.printStackTrace();
            return;
        }

        // Filtrar grupos según el modo (mis grupos o disponibles)
        ListaSimplementeEnlazada<Grupo> todosGrupos = RedSocial.getInstance().getGrupos();
        ListaSimplementeEnlazada<Grupo> gruposFiltrados = new ListaSimplementeEnlazada<>();

        for (Grupo grupo : todosGrupos) {
            boolean esMiembro = grupo.esMiembro(estudiante);

            if (mostrarMisGrupos) {
                if (esMiembro) {
                    gruposFiltrados.add(grupo);
                }
            } else {
                if (grupo.isPublico() && !esMiembro) {
                    gruposFiltrados.add(grupo);
                }
            }
        }

        // Mostrar grupos en la interfaz
        if (gruposFiltrados.isEmpty()) {
            Label mensaje = new Label(mostrarMisGrupos ?
                    "No perteneces a ningún grupo" :
                    "No hay grupos disponibles para unirse");
            mensaje.getStyleClass().add("mensaje-vacio");
            gruposVBox.getChildren().add(mensaje);
        } else {
            for (Grupo grupo : gruposFiltrados) {
                ToggleButton botonGrupo = new ToggleButton(grupo.getNombre());
                botonGrupo.setPrefWidth(150);
                botonGrupo.setPrefHeight(40);
                botonGrupo.setToggleGroup(grupoGrupos);
                botonGrupo.setUserData(grupo);
                botonGrupo.getStyleClass().add("sidebar-button-grupo");

                // Resaltar si es el grupo actualmente seleccionado
                if (grupoGrupos.getSelectedToggle() != null &&
                        grupo.equals(((ToggleButton) grupoGrupos.getSelectedToggle()).getUserData())) {
                    botonGrupo.setSelected(true);
                }

                botonGrupo.setOnAction(e -> {
                    if (botonGrupo.isSelected()) {
                        // Debug al seleccionar grupo
                        System.out.println("\nGrupo seleccionado: " + grupo.getNombre());
                        System.out.println("HashCode: " + grupo.hashCode());
                        System.out.println("Contenidos en este grupo:");
                        grupo.getContenidos().mostrarArbolCompleto();

                        seleccionarGrupo(botonGrupo);
                        actualizarBotonesAccion(grupo);
                    } else {
                        limpiarContenido();
                        deshabilitarBotonesAccion();
                    }
                });

                gruposVBox.getChildren().add(botonGrupo);
            }
        }

        // Actualizar estado de los botones de navegación
        buttonMisGrupos.setDisable(mostrarMisGrupos);
        buttonGruposDisponibles.setDisable(!mostrarMisGrupos);
    }

    private void limpiarContenido() {
        scrollPaneContenedorContenidos.setContent(new VBox()); // Limpia el contenido mostrado
    }



    private void seleccionarGrupo(ToggleButton botonGrupo) {

        gruposVBox.getChildren().forEach(node -> {
            if (node instanceof ToggleButton) {
                ToggleButton tb = (ToggleButton) node;
                if (tb != botonGrupo) {
                    tb.setSelected(false);
                }
            }
        });

        Grupo grupoSeleccionado = (Grupo) botonGrupo.getUserData();
        contenidoSeleccionado = null;
        calificarButtton.setDisable(true);


        if (grupoSeleccionado != null) {
            System.out.println("Grupo seleccionado: " + grupoSeleccionado.getNombre());
            mostrarContenidoGrupo(grupoSeleccionado);
        }


    }

    @FXML
    private void calificarContenido(ActionEvent event) {
        if (contenidoSeleccionado == null) {
            mostrarAlerta("Error", "Debes seleccionar un contenido primero");
            return;
        }

        if (usuarioActual == null) {
            mostrarAlerta("Error", "No hay usuario autenticado");
            return;
        }

        // Verificar si el usuario es el autor del contenido
        if (contenidoSeleccionado.getAutor().equals(usuarioActual)) {
            mostrarAlerta("Información", "No puedes calificar tu propio contenido");
            return;
        }

        // Crear diálogo de calificación
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Calificar Contenido");
        dialog.setHeaderText("Califica: " + contenidoSeleccionado.getTitulo());

        // Crear slider para la calificación
        Slider slider = new Slider(1, 5, 3);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setBlockIncrement(1);
        slider.setSnapToTicks(true);

        Label valorLabel = new Label("3");
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            valorLabel.setText(String.valueOf(newVal.intValue()));
        });

        VBox content = new VBox(10);
        content.getChildren().addAll(
                new Label("Selecciona una calificación (1-5):"),
                slider,
                new HBox(5, new Label("Valor:"), valorLabel)
        );
        content.setPadding(new Insets(20));
        dialog.getDialogPane().setContent(content);

        // Agregar botones
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Mostrar diálogo y procesar resultado
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            int valoracion = (int) slider.getValue();
            procesarCalificacion(valoracion);
        }
    }

    private void procesarCalificacion(int valoracion) {
        try {
            // Crear nueva calificación
            Calificacion calificacion = new Calificacion(valoracion, usuarioActual, contenidoSeleccionado);



            if (contenidoSeleccionado.getCalificaciones() == null) {
                contenidoSeleccionado.setCalificaciones(new ListaSimplementeEnlazada<>());
            }


            // Validar si usuario ya calificó
            for (Calificacion c : contenidoSeleccionado.getCalificaciones()) {
                if (c.getUsuario().equals(usuarioActual)) {
                    mostrarAlerta("Información", "Ya has calificado este contenido.");
                    return;
                }
            }

            // Guardar en BD
            int idCalificacion = UtilSQL.agregarCalificacion(calificacion);
            System.out.println("ID calificación insertada: " + idCalificacion);

            if (idCalificacion > 0) {
                calificacion.setId(String.valueOf(idCalificacion));
                contenidoSeleccionado.agregarCalificacion(calificacion);
                contenidoSeleccionado.setCalificacionPromedio(calcularPromedio(contenidoSeleccionado));
                mostrarAlerta("Éxito", "Calificación registrada: " + valoracion + " estrellas");

                // Actualizar la vista
                Grupo grupoActual = (Grupo) ((ToggleButton) grupoGrupos.getSelectedToggle()).getUserData();
                mostrarContenidoGrupo(grupoActual);
            } else {
                mostrarAlerta("Error", "No se pudo guardar la calificación");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Ocurrió un error al calificar");
        }
    }



    private double calcularPromedio(Contenido contenido) {
        ListaSimplementeEnlazada<Calificacion> calificaciones = contenido.getCalificaciones();
        if (calificaciones == null || calificaciones.isEmpty()) {
            return 0.0;
        }

        double suma = 0;
        int cantidad = 0;

        for (Calificacion c : calificaciones) {
            suma += c.getValoracion();
            cantidad++;
        }

        double promedio = suma / cantidad;

        // Redondear a 1 decimal
        return Math.round(promedio * 10.0) / 10.0;
    }


    @FXML
    void irAGruposDisponibles(ActionEvent event) {
        mostrandoMisGrupos = false;
        cargarGrupos(usuarioActual, false);
    }

    @FXML
    void irAMisGrupos(ActionEvent event) {
        mostrandoMisGrupos = true;
        cargarGrupos(usuarioActual, true);
    }

    @FXML
    private void unirseAGrupo(ActionEvent event) {
        if (usuarioActual == null) {
            mostrarAlerta("Error", "No hay usuario autenticado");
            return;
        }

        ToggleButton seleccionado = (ToggleButton) grupoGrupos.getSelectedToggle();
        if (seleccionado != null) {
            Grupo grupo = (Grupo) seleccionado.getUserData();

            // Verificar membresía tanto en BD como en memoria
            if (UtilSQL.usuarioEstaEnGrupo(usuarioActual.getId(), grupo.getId()) ||
                    grupo.esMiembro(usuarioActual)) {
                mostrarAlerta("Información", "Ya eres miembro de este grupo");
                return;
            }

            if (grupo.isPublico()) {
                if (UtilSQL.agregarUsuarioAGrupo(usuarioActual.getId(), grupo.getId())) {
                    // Actualizar en memoria
                    grupo.agregarMiembro(usuarioActual);

                    // Forzar recarga desde BD
                    RedSocial.getInstance().getGrupos().clear();
                    UtilSQL.obtenerGrupos();
                    UtilSQL.cargarMiembrosDeGrupos();

                    // Cambiar a "Mis grupos" y recargar
                    mostrandoMisGrupos = true;
                    cargarGrupos(usuarioActual, true);

                    mostrarAlerta("Éxito", "Te has unido al grupo " + grupo.getNombre());
                } else {
                    mostrarAlerta("Error", "No se pudo unir al grupo");
                }
            } else {
                mostrarAlerta("Error", "Este grupo no es público");
            }
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void salirDeGrupo(ActionEvent event) {
        if (usuarioActual == null) {
            mostrarAlerta("Error", "No hay usuario autenticado");
            return;
        }

        ToggleButton seleccionado = (ToggleButton) grupoGrupos.getSelectedToggle();
        if (seleccionado != null) {
            Grupo grupo = (Grupo) seleccionado.getUserData();

            // Verificar si el usuario es miembro
            if (grupo.esMiembro(usuarioActual)) {
                if (UtilSQL.eliminarUsuarioDeGrupo(usuarioActual.getId(), grupo.getId())) {
                    // Actualizar en memoria
                    grupo.eliminarMiembro(usuarioActual);

                    // Forzar recarga desde BD
                    RedSocial.getInstance().getGrupos().clear();
                    UtilSQL.obtenerGrupos();
                    UtilSQL.cargarMiembrosDeGrupos();

                    // Cambiar a vista de "Disponibles" y recargar
                    mostrandoMisGrupos = false;
                    cargarGrupos(usuarioActual, false);

                    mostrarAlerta("Éxito", "Has salido del grupo " + grupo.getNombre());
                } else {
                    mostrarAlerta("Error", "No se pudo salir del grupo");
                }
            } else {
                mostrarAlerta("Información", "No eres miembro de este grupo");
            }
        }
    }

    private void actualizarBotonesAccion(Grupo grupo) {
        if (grupo == null) {
            deshabilitarBotonesAccion();
            return;
        }

        boolean esMiembro = grupo.esMiembro(usuarioActual);

        buttonUnirse.setDisable(esMiembro);
        buttonSalir.setDisable(!esMiembro);
    }

    private void deshabilitarBotonesAccion() {
        buttonUnirse.setDisable(true);
        buttonSalir.setDisable(true);
    }



    private void mostrarContenidoGrupo(Grupo grupoSeleccionado) {
        if (grupoSeleccionado == null || usuarioActual == null) {
            mostrarAlerta("Error", "No se ha seleccionado grupo o no hay usuario autenticado");
            return;
        }

        contenidoSeleccionado = null;
        calificarButtton.setDisable(true);

        // Debug clave
        System.out.println("\n=== DEBUG DIRECTO ===");
        System.out.println("Grupo: " + grupoSeleccionado.getNombre());
        System.out.println("Contenidos en memoria:");
        grupoSeleccionado.getContenidos().mostrarArbolCompleto(); // Asegúrate de que este método funcione

        // Crear contenedor UI
        VBox contenidosVBox = new VBox(10);
        contenidosVBox.setPadding(new Insets(10));

        // Obtener contenidos como lista
        ListaSimplementeEnlazada<Contenido> contenidos = grupoSeleccionado.getContenidos().recorrerInorden();

        // Mostrar en UI (sin agrupar por tema para simplificar)
        if (contenidos.isEmpty()) {
            contenidosVBox.getChildren().add(new Label("No hay contenidos en este grupo"));
        } else {

            // Recorrer lista enlazada manualmente
            for (int i = 0; i < contenidos.size(); i++) {
                Contenido contenido = contenidos.get(i);
                ListaSimplementeEnlazada<Calificacion> calificaciones = UtilSQL.obtenerCalificacionesPorContenido(contenido.getId());
                contenido.setCalificaciones(calificaciones);
                contenido.setCalificacionPromedio(calcularPromedio(contenido));
                System.out.println("Contenido: " + contenido.getTitulo() + " - Calificaciones cargadas: " + calificaciones.size() +
                        " - Promedio calculado: " + contenido.getCalificacionPromedio());
                try {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/co/edu/uniquindio/red_social/TarjetaContenido.fxml"));
                    Parent tarjeta = loader.load();
                    TarjetaContenidoController controller = loader.getController();
                    controller.setContenido(contenido);

                    tarjeta.setOnMouseClicked(event -> {
                        contenidoSeleccionado = contenido;
                        calificarButtton.setDisable(false);
                    });
                    System.out.println("contenido seleccionado: " + contenido.getTitulo());


                    contenidosVBox.getChildren().add(tarjeta);
                } catch (IOException e) {
                    // Fallback básico
                    Label fallback = new Label(contenido.getTitulo() + " (" + contenido.getTema() + ")");
                    contenidosVBox.getChildren().add(fallback);
                }
            }
        }

        // Actualizar UI
        grupoActualLabel.setText(grupoSeleccionado.getNombre());
        scrollPaneContenedorContenidos.setContent(contenidosVBox);
        scrollPaneContenedorContenidos.setFitToWidth(true);

    }



    @FXML
    void irAChat(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/mensajes.fxml"));
            Parent configView = loader.load();

            Scene scene = new Scene(configView);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void irAInicio(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/inicio.fxml"));
            Parent configView = loader.load();

            Scene scene = new Scene(configView);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void irASugerencias(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/Configuracion.fxml"));
            Parent configView = loader.load();

            Scene scene = new Scene(configView);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void irAConfig(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/Configuracion.fxml"));
            Parent configView = loader.load();

            Scene scene = new Scene(configView);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void irASolicitudesAyuda(ActionEvent event) {
        try {

            URL configUrl = getClass().getResource("/co/edu/uniquindio/red_social/solicitudes.fxml");
            System.out.println("URL config: " + configUrl);
            FXMLLoader loader = new FXMLLoader(configUrl);
            Parent configView = loader.load();

            if (root != null) {
                root.getChildren().clear();
                root.getChildren().add(configView);
            } else {
                System.err.println("El contenedor principal es null.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void irAContenidos(ActionEvent event) {
        try {

            URL configUrl = getClass().getResource("/co/edu/uniquindio/red_social/TusContenidos.fxml");
            System.out.println("URL Logo: " + configUrl);
            FXMLLoader loader = new FXMLLoader(configUrl);
            Parent configView = loader.load();

            if (root != null) {
                root.getChildren().clear();
                root.getChildren().add(configView);
            } else {
                System.err.println("El contenedor principal es null.");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
