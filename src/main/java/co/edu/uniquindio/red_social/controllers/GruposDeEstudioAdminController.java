package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.contenidos.Contenido;
import co.edu.uniquindio.red_social.clases.social.Grupo;
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

public class GruposDeEstudioAdminController {

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
    private Label adminLabel;

    @FXML
    private Button botonBuscar;

    @FXML
    private Button buttonAniadir;

    @FXML
    private ToggleButton buttonGrupo1;

    @FXML
    private ToggleButton buttonGrupo2;

    @FXML
    private Button buttonGrupos;

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
    private VBox userchatListVBox;



    @FXML
    private VBox contenedorGrupos;


    @FXML
    private VBox gruposVBox;

    private List<Grupo> listaGrupos;


    GrupoService grupoService = GrupoService.getInstance();

    private ToggleGroup grupoGrupos = new ToggleGroup();

    private ArbolBinario<Contenido> arbolContenido;


    @FXML
    private void initialize() {
        cargarGrupos();
    }

    private void cargarGrupos() {
        gruposVBox.getChildren().clear();
        listaGrupos = grupoService.obtenerGrupos();

        for (Grupo grupo : listaGrupos) {
            ToggleButton botonGrupo = new ToggleButton(grupo.getNombre());
            botonGrupo.setPrefWidth(150);
            botonGrupo.setPrefHeight(40);
            botonGrupo.setToggleGroup(grupoGrupos);
            botonGrupo.setUserData(grupo);
            botonGrupo.getStyleClass().add("sidebar-button-grupo");

            botonGrupo.setOnAction(e -> {
                if (botonGrupo.isSelected()) {
                    seleccionarGrupo(botonGrupo);
                }
            });

            gruposVBox.getChildren().add(botonGrupo);
        }

        if (!gruposVBox.getChildren().isEmpty()) {
            ToggleButton primero = (ToggleButton) gruposVBox.getChildren().get(0);
            primero.setSelected(true);
            seleccionarGrupo(primero);
        }
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
    private void irACrearGrupo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/crearGrupo.fxml"));
            Parent root = loader.load();

            Stage popupStage = new Stage();
            popupStage.setTitle("Crear nuevo grupo");
            popupStage.setScene(new Scene(root));
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow()); // Define la ventana principal como dueña
            popupStage.setResizable(false);

            popupStage.setOnHidden(e -> {
                cargarGrupos(); // Recarga los grupos cuando se cierra la ventana popup
            });

            popupStage.show();// Muestra como ventana nueva sin cerrar la actual



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void mostrarContenidoGrupo(Grupo grupo) {
        grupoActualLabel.setText(grupo.getNombre());
        arbolContenido = grupo.getContenidos();

        LabelTotalPublicados.setText(arbolContenido.getPeso() + " Publicaciones");

        VBox contenidosVBox = new VBox(10);
        contenidosVBox.setPadding(new Insets(10));
        contenidosVBox.setStyle("-fx-background-color: #f9f9f9;");

        if (arbolContenido.getPeso() == 0) {
            Label vacioLabel = new Label("Este grupo no tiene contenidos aún");
            vacioLabel.setStyle("-fx-text-fill: #666; -fx-font-style: italic;");
            contenidosVBox.getChildren().add(vacioLabel);
        } else {
            ListaSimplementeEnlazada<Contenido> lista = arbolContenido.recorrerInorden();

            for (Contenido contenido : lista) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/TarjetaContenido.fxml"));
                    VBox tarjeta = loader.load();

                    TarjetaContenidoController controller = loader.getController();
                    controller.setContenido(contenido, arbolContenido.getPeso());


                    contenidosVBox.getChildren().add(tarjeta);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Fallback básico
                    HBox contenidoBox = new HBox(10);
                    Label contenidoLabel = new Label(contenido.getTitulo() + " - " + contenido.getAutor().getNombre());

                    Button eliminarBtn = new Button("Eliminar");
                    eliminarBtn.setOnAction(ex -> {
                        if (UtilSQL.eliminarPublicacion(contenido.getId())) {
                            grupo.eliminarPublicacion(contenido);
                            mostrarContenidoGrupo(grupo); // Refrescar vista
                            mostrarAlerta("Éxito", "Contenido eliminado correctamente");
                        }
                    });

                    contenidoBox.getChildren().addAll(contenidoLabel, eliminarBtn);
                    contenidosVBox.getChildren().add(contenidoBox);
                }
            }
        }

        scrollPaneContenedorContenidos.setContent(contenidosVBox);
        scrollPaneContenedorContenidos.setFitToWidth(true);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    @FXML
    private void irAChat(ActionEvent event) {
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/gruposEstudio.fxml"));
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
