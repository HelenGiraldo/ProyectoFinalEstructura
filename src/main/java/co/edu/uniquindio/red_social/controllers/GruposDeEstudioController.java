package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.social.Grupo;
import co.edu.uniquindio.red_social.clases.usuarios.Administrador;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.Usuario;
import co.edu.uniquindio.red_social.data_base.UtilSQL;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

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
    private VBox userchatListVBox;

    @FXML
    void irAChat(ActionEvent event) {

    }


    @FXML
    void irAGruposDisponibles(ActionEvent event) {

    }


    @FXML
    void irAMisGrupos(ActionEvent event) {

    }

    private Estudiante usuarioActual;
    private Grupo grupoActual;

    @FXML
    private void initialize() {
        gruposEstudioButton.setSelected(true);
    }

    private void cargarGrupo(){
        if (usuarioActual == null) return;

        userchatListVBox.getChildren().clear();

        UtilSQL.obtenerGrupos();

        ListaSimplementeEnlazada<Grupo> grupos = RedSocial.getInstance().getGrupos();

        for (int i = 0; i < grupos.size(); i++) {
            Grupo grupo = grupos.get(i);

            ToggleButton grupoButton = new ToggleButton(grupo.getNombre());
            grupoButton.setPrefWidth(112);
            grupoButton.getStyleClass().add("sidebar-button-chat");

            grupoButton.setOnAction(e -> {
                grupoActual = grupo;
                grupoActualLabel.setText(grupo.getNombre());
                // Aquí puedes cargar el contenido del grupo seleccionado
            });

            userchatListVBox.getChildren().add(grupoButton);

        }


    }


    @FXML
    private void unirseAGrupo(ActionEvent event) {
        if (grupoActual != null && usuarioActual != null) {
            grupoActual.agregarMiembro(usuarioActual);
            UtilSQL.agregarUsuarioAGrupo(usuarioActual.getId(), grupoActual.getId());
            System.out.println(usuarioActual.getNombre() + " se unió al grupo " + grupoActual.getNombre());
        }
    }

    public void setUsuarioActual(Estudiante estudiante) {
        this.usuarioActual = estudiante;
        cargarGrupo();
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
