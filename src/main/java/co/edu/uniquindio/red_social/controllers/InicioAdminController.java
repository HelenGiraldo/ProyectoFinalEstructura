
package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.usuarios.Administrador;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import co.edu.uniquindio.red_social.clases.usuarios.Usuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class InicioAdminController {

    @FXML
    private HBox HboxTusContendiosContenido;

    @FXML
    private ToggleButton InicioButton;

    @FXML
    private Label LabelBienvendioAThinkTogether;

    @FXML
    private Label LabelCantidadIntegrantes;

    @FXML
    private Label LabelExploraContenidos;

    @FXML
    private Label LabelGrupoSugerido;

    @FXML
    private Label LabelGruposDeEstudiosSugeridos;

    @FXML
    private Label LabelGruposEncontradosParaTi;

    @FXML
    private Label LabelTipo;

    @FXML
    private Label LabelTotalPublicados;

    @FXML
    private Label LabelTusContenidos;

    @FXML
    private Label LabelTusContenidosContenido;

    @FXML
    private Label LabelUltimoContenido;

    @FXML
    private Label LabelUltimoContenidoTexto;

    @FXML
    private Label LabelValoracion;

    @FXML
    private ToggleButton MensajesButton;

    @FXML
    private ToggleButton SolicitudesDeAyudaButton;

    @FXML
    private ToggleButton SugerenciasButton;

    @FXML
    private Label TextFieldSaludo;

    @FXML
    private Label TextFieldTitle;

    @FXML
    private VBox TuscontendiosVBox;

    @FXML
    private VBox VBoxGruposEstudio;

    @FXML
    private VBox VBoxTusContenidosContenido;

    @FXML
    private VBox VistaInicio;

    @FXML
    private Button botonBuscar;

    @FXML
    private TextField campoBusqueda;

    @FXML
    private ToggleButton configuracionPerfilButton;

    @FXML
    private ToggleButton gruposEstudioButton;

    @FXML
    private VBox hBoxDerecha;

    @FXML
    private GridPane hBoxizquierda;

    @FXML
    private ImageView imagenPerfil;

    @FXML
    private ToggleButton misContenidosButton;

    @FXML
    private ScrollPane scrollPrincipal;
    @FXML
    private AnchorPane root;

    private Administrador usuario;

    public void setAdministradorActual(Administrador usuario) {
        this.usuario = usuario;
        inicializarUsuario(usuario);
    }

    @FXML
    public void initialize() {
        InicioButton.setSelected(true);

        PerfilUsuario perfil = PerfilUsuario.getInstancia();

        Platform.runLater(() -> {
            if (imagenPerfil != null) {
                double radius = imagenPerfil.getFitWidth() / 2;
                Circle clip = new Circle(radius, radius, radius);
                imagenPerfil.setClip(clip);
            }
        });

        perfil.imagenPerfilProperty().addListener((obs, oldImg, newImg) -> {
            if (newImg != null) {
                imagenPerfil.setImage(newImg);
            }
        });

        actualizarSaludo();
    }


    @FXML
    public void inicializarUsuario(Administrador usuario) {
        this.usuario = usuario;

        // Guardar el usuario en el singleton
        PerfilUsuario.getInstancia().setUsuarioActual(usuario);

        // Actualizar la UI con los datos del usuario
        cargarDatosUsuario();
    }

    private void cargarDatosUsuario() {
        Usuario usuarioActual = PerfilUsuario.getUsuarioActual();

        if (usuarioActual != null && usuarioActual.getImagenPerfil() != null) {
            Image imagen = new Image(usuarioActual.getImagenPerfil().toURI().toString());
            imagenPerfil.setImage(imagen);
            PerfilUsuario.getInstancia().setImagenPerfil(imagen);
        }

        actualizarSaludo();
    }






    public void actualizarSaludo() {
        Usuario usuario = PerfilUsuario.getUsuarioActual();
        if (usuario != null) {
            TextFieldSaludo.setText("¡Hola, " + usuario.getNombre() + "!");
        } else {
            System.out.println("No hay usuario actual en PerfilUsuario");
            TextFieldSaludo.setText("¡Hola!");
        }
    }



    public void actualizarGrupoEstudio(String nombreGrupo, String integrantes) {
        LabelGrupoSugerido.setText(nombreGrupo);
        LabelCantidadIntegrantes.setText(integrantes + " integrantes");
    }

    public void actualizarContenido(String tipo, String valoracion) {
        LabelTipo.setText(tipo);
        LabelValoracion.setText("★ " + valoracion);
    }

    @FXML
    private void irAGruposEstudio(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/GruposDeEstudioAdmin.fxml"));
            Parent configView = loader.load();

            GruposDeEstudioAdminController controller = loader.getController();

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
    private void irAConfig(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/ConfiguracionAdmin.fxml"));
            Parent configView = loader.load();

            ConfiguracionAdminController controller = loader.getController();
            // PASAR el usuario (que tienes en 'usuario' o usa PerfilUsuario.getUsuarioActual())
            if (usuario != null) {
                controller.setAdministradorActual(usuario);
                System.out.println("Usuario enviado a ConfiguracionAdminController: " + usuario.getNombre());
            } else {
                System.out.println("usuario en InicioAdminController es null");
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
    private void irASolicitudesAyuda(ActionEvent event) {
        try {

            URL configUrl = getClass().getResource("/co/edu/uniquindio/red_social/solicitudesAyudaAdmin.fxml");
            System.out.println("URL config: " + configUrl);
            FXMLLoader loader = new FXMLLoader(configUrl);
            Parent configView = loader.load();
            SolicitudesAyudaAdminController controller = loader.getController();
            // PASAR el usuario (que tienes en 'usuario' o usa PerfilUsuario.getUsuarioActual())
            if (usuario != null) {
                controller.setUsuarioActual(usuario);
                System.out.println("Usuario enviado a SolicitudesAyudaAdminController: " + usuario.getNombre());
            } else {
                System.out.println("usuario en InicioAdminController es null");
            }

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
