package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import javafx.application.Platform;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class SugerenciasController {

    @FXML
    private HBox HboxSolicitud;

    @FXML
    private HBox HboxSolicitud1;

    @FXML
    private ToggleButton InicioButton;

    @FXML
    private ToggleButton MensajesButton;

    @FXML
    private ToggleButton SolicitudesDeAyudaButton;

    @FXML
    private ToggleButton SugerenciasButton;

    @FXML
    private Label TextFieldTitle;

    @FXML
    private Button botonBuscar;

    @FXML
    private Button buttonEnviarSolicitud;

    @FXML
    private TextField campoBusqueda;

    @FXML
    private VBox chatListVBoxFondoDos;

    @FXML
    private VBox chatSpace;

    @FXML
    private ToggleButton configuracionPerfilButton;

    @FXML
    private ToggleButton gruposEstudioButton;

    @FXML
    private VBox hBoxDerecha;

    @FXML
    private ImageView imagePerfil;

    @FXML
    private ImageView imagePerfil2;

    @FXML
    private ImageView imagenPerfil;

    @FXML
    private Label interesesLabel;

    @FXML
    private Label interesesLabel2;

    @FXML
    private Label labelUsuario;

    @FXML
    private Label labelUsuario2;

    @FXML
    private ToggleButton misContenidosButton;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPaneContenedorContenidos;

    @FXML
    private ScrollPane scrollPrincipal;

    @FXML
    private Label tusContenidosLabel;

    @FXML
    private VBox vBoxTodosContenidos;

    private Estudiante usuario;

    public void setUsuarioActual(Estudiante usuario) {
        this.usuario = usuario;
    }

    @FXML
    private void initialize() {
        SugerenciasButton.setSelected(true);

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


    @FXML
    private void irAGruposEstudio(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/gruposEstudio.fxml"));
            Parent configView = loader.load();

            GruposDeEstudioController controller = loader.getController();

            // PASAR el usuario (que tienes en 'usuario' o usa PerfilUsuario.getUsuarioActual())
            if (usuario != null) {
                controller.setUsuarioActual(usuario);
                System.out.println("Usuario enviado a GruposDeEstudioController: " + usuario.getNombre());
            } else {
                System.out.println("usuario en InicioController es null");
            }

            Scene scene = new Scene(configView);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void irAContenido(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/TusContenidos.fxml", event);
    }

    @FXML
    void irAGrupoEStudio(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/gruposEstudio.fxml", event);

    }

    @FXML
    void irASolitudesAyuda(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/solicitudes.fxml", event);
    }

    @FXML
    void irAChat(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/mensajes.fxml", event);

    }

    @FXML
    void irAConfig(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/Configuracion.fxml", event);
    }

    @FXML
    void irAInicio(ActionEvent event) {
        navegar("/co/edu/uniquindio/red_social/Inicio.fxml", event);
    }

    RedSocial redSocial = RedSocial.getInstance();

    private void navegar(String url, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            Parent configView = loader.load();
            Object controller = loader.getController();

            if (controller instanceof GruposDeEstudioController) {
                ((GruposDeEstudioController) controller).setUsuarioActual(usuario);
                System.out.println("Usuario enviado a GruposDeEstudioAdminController: " + usuario.getNombre());
            } else if (controller instanceof InicioController) {
                ((InicioController) controller).setUsuarioActual(usuario);
                System.out.println("Usuario enviado a InicioController: " + usuario.getNombre());
            } else if (controller instanceof ConfiguracionController) {
                ((ConfiguracionController) controller).setUsuarioActual(usuario);
            } // Agrega otros controladores según el destino
            else if (controller instanceof ChatController) {
                ((ChatController) controller).setUsuarioActual(usuario);
            } else if (controller instanceof SolicitudesAyudaController) {
                ((SolicitudesAyudaController) controller).setUsuarioActual(usuario);
            } else if (controller instanceof SugerenciasController) {
                ((SugerenciasController) controller).setUsuarioActual(usuario);
            }

            Scene scene = new Scene(configView);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
