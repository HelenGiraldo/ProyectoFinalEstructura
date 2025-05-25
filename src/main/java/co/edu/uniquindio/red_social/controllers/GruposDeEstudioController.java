package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.contenidos.Contenido;
import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.clases.usuarios.Administrador;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.Usuario;
import co.edu.uniquindio.red_social.data_base.UtilSQL;
import co.edu.uniquindio.red_social.estructuras.ArbolBinario;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;
import co.edu.uniquindio.red_social.util.GrupoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

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

    private ListaSimplementeEnlazada<Grupo> listaGrupos;


    GrupoService grupoService = GrupoService.getInstance();

    private ToggleGroup grupoGrupos = new ToggleGroup();

    private ArbolBinario<Contenido> arbolContenido;

    private Estudiante usuarioActual;

    private boolean mostrandoMisGrupos = true;



    @FXML
    private void initialize() {
        if (usuarioActual != null) {
            cargarGrupos(usuarioActual, true);
        }
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

        gruposVBox.getChildren().clear();

        // Recargar grupos desde BD
        RedSocial.getInstance().getGrupos().clear();
        UtilSQL.obtenerGrupos();
        UtilSQL.cargarMiembrosDeGrupos();

        ListaSimplementeEnlazada<Grupo> todosGrupos = RedSocial.getInstance().getGrupos();
        ListaSimplementeEnlazada<Grupo> gruposFiltrados = new ListaSimplementeEnlazada<>();

        for (Grupo grupo : todosGrupos) {
            boolean esMiembro = grupo.esMiembro(estudiante);

            if (mostrarMisGrupos && esMiembro) {
                gruposFiltrados.add(grupo);
            } else if (!mostrarMisGrupos && grupo.isPublico() && !esMiembro) {
                gruposFiltrados.add(grupo);
            }
        }

        // Mostrar grupos filtrados
        for (Grupo grupo : gruposFiltrados) {
            ToggleButton botonGrupo = new ToggleButton(grupo.getNombre());
            botonGrupo.setPrefWidth(150);
            botonGrupo.setPrefHeight(40);
            botonGrupo.setToggleGroup(grupoGrupos);
            botonGrupo.setUserData(grupo);
            botonGrupo.getStyleClass().add("sidebar-button-grupo");

            botonGrupo.setOnAction(e -> {
                if (botonGrupo.isSelected()) {
                    seleccionarGrupo(botonGrupo);
                    actualizarBotonesAccion(grupo);
                }
            });

            gruposVBox.getChildren().add(botonGrupo);
        }

        // Resto del método permanece igual...
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


        if (grupoSeleccionado != null) {

            System.out.println("Grupo seleccionado: " + grupoSeleccionado.getNombre());
            mostrarContenidoGrupo(grupoSeleccionado);
        }


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

        // Obtener la instancia ACTUAL del grupo desde RedSocial
        RedSocial redSocial = RedSocial.getInstance();
        Grupo grupoActual = redSocial.obtenerGrupoPorId(grupoSeleccionado.getId());

        if (grupoActual == null) {
            mostrarAlerta("Error", "Grupo no encontrado");
            return;
        }

        // Debug 1: Verificar instancia del grupo
        System.out.println("=== DEBUG GRUPO ===");
        System.out.println("Grupo seleccionado: " + grupoSeleccionado.getNombre());
        System.out.println("Hash grupo seleccionado: " + System.identityHashCode(grupoSeleccionado));
        System.out.println("Hash grupo actual: " + System.identityHashCode(grupoActual));
        System.out.println("¿Misma instancia? " + (grupoSeleccionado == grupoActual));
        System.out.println("\n=== ESTRUCTURA DEL ÁRBOL ===");
        grupoActual.getContenidos().mostrarInorden();

        // Debug 2: Verificar contenidos directamente del árbol
        System.out.println("\n=== CONTENIDOS EN GRUPO ===");
        ListaSimplementeEnlazada<Contenido> contenidosGrupo = grupoActual.getContenidos().recorrerInorden();
        System.out.println("Total en grupo: " + contenidosGrupo.size());

        for (Contenido c : contenidosGrupo) {
            System.out.println("DEBUG - Contenido: " + c.getTitulo());
            System.out.println("DEBUG - Grupo: " + (c.getGrupo() != null ? c.getGrupo().getNombre() : "null"));
        }

        // Actualizar UI
        grupoActualLabel.setText(grupoActual.getNombre());
        VBox contenidosVBox = new VBox(10);
        contenidosVBox.setPadding(new Insets(10));

        if (contenidosGrupo.isEmpty()) {
            Label vacioLabel = new Label("Este grupo no tiene contenidos aún");
            contenidosVBox.getChildren().add(vacioLabel);
        } else {
            for (Contenido contenido : contenidosGrupo) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/TarjetaContenido.fxml"));
                    Parent tarjeta = loader.load();

                    TarjetaContenidoController controller = loader.getController();
                    controller.setContenido(contenido);

                    contenidosVBox.getChildren().add(tarjeta);
                } catch (IOException e) {
                    // Fallback básico si falla la carga de la tarjeta
                    HBox fallback = new HBox(
                            new Label(contenido.getTitulo()),
                            new Label(" | " + contenido.getTipoContenido())
                    );
                    contenidosVBox.getChildren().add(fallback);
                }
            }
        }

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
