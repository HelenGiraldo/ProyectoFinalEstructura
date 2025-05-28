package co.edu.uniquindio.red_social.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.social.SolicitudAyuda;
import co.edu.uniquindio.red_social.clases.social.Solucion;
import co.edu.uniquindio.red_social.clases.usuarios.Administrador;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.util.SolicitudActual;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ResolverSolicitudController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label ContenidoLabel;

    @FXML
    private Label EnviadoConExitoLabel;

    @FXML
    private Label TituloLabel;

    @FXML
    private VBox TuscontendiosVBox;

    @FXML
    private Label UsuarioLabel;

    @FXML
    private Button buttonEnviar;

    @FXML
    private Label importanciaLabel;

    @FXML
    private Label resolverSolicitudLabel;

    @FXML
    private AnchorPane root;

    @FXML
    private ScrollPane scrollPaneContenedorNuevoContenido;

    @FXML
    private TextField solucionPropuestaField;

    @FXML
    private Label volverLabel;

    private Administrador usuario;

    public void setUsuarioActual(Administrador usuario) {
        this.usuario = usuario;
    }



    @FXML
    void enviarSolucion() {
        if(solucionPropuestaField.textProperty().getValue().isEmpty()){
            return;
        }
        RedSocial.getInstance().eliminarSolicitudAyuda(solicitud);
        solicitud.getEstudiante().agregarSolucion(new Solucion(solucionPropuestaField.textProperty().getValue(), solicitud.getEstudiante()));
        EnviadoConExitoLabel.setVisible(true);
        EnviadoConExitoLabel.setText("Solución enviada con exito");
    }

    @FXML
    void onVolver(MouseEvent event) {
        navegar("/co/edu/uniquindio/red_social/solicitudesAyudaAdmin.fxml",event);
    }
    private void navegar(String url, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            Parent configView = loader.load();
            SolicitudesAyudaAdminController controller = loader.getController();
            // PASAR el usuario (que tienes en 'usuario' o usa PerfilUsuario.getUsuarioActual())
            if (usuario != null) {
                controller.setUsuarioActual(usuario);
                System.out.println("Usuario enviado a SolicitudesAyudaAdminController: " + usuario.getNombre());
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
    void initialize() {
        solicitud = SolicitudActual.getInstance().getSolicitudAyuda();
        TituloLabel.setText(solicitud.getTitulo());
        importanciaLabel.setText(solicitud.getPrioridad());
        UsuarioLabel.setText(solicitud.getEstudiante().getNombre()+ " "+solicitud.getEstudiante().getApellido());
        ContenidoLabel.setText(solicitud.getMensaje());
        EnviadoConExitoLabel.setVisible(false);
    }


    SolicitudAyuda solicitud;

}
