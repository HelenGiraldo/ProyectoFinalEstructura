package co.edu.uniquindio.red_social;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML private ImageView AvatarCompañero;
    @FXML private HBox HboxAvatarNombre;
    @FXML private ToggleButton InicioButton;
    @FXML private Label LabelUser2;
    @FXML private ToggleButton MensajesButton;
    @FXML private ToggleButton SolicitudesDeAyudaButton;
    @FXML private ToggleButton SugerenciasButton;
    @FXML private Label TextFieldSaludo;
    @FXML private Label TextFieldTitle;
    @FXML private Button botonBuscar;
    @FXML private TextField campoBusqueda;
    @FXML private VBox chatListVBoxFondo1;
    @FXML private VBox chatListVBoxFondo2;
    @FXML private VBox chatsSpace;
    @FXML private ToggleButton configuracionPerfilButton;
    @FXML private VBox contenedorMensajes;
    @FXML private ToggleButton gruposEstudioButton;
    @FXML private ImageView imagenPerfil;
    @FXML private TextField messageTextField;
    @FXML private ToggleButton misContenidosButton;
    @FXML private ScrollPane scrollPaneChats;
    @FXML private ScrollPane scrollPaneChatsEnviadosRecibidos;
    @FXML private Button sendButton;
    @FXML private VBox userchatListVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            double radius = imagenPerfil.getFitWidth() / 2;
            Circle clip = new Circle(radius, radius, radius);
            imagenPerfil.setClip(clip);
        });

        // Mensajes de prueba
        agregarMensaje("¡Hola! ¿Cómo estás?", false);
        agregarMensaje("Estoy bien, ¿y tú?", true);

        // Acción del botón de enviar
        sendButton.setOnAction(e -> enviarMensaje());

        System.out.println("contenedorMensajes: " + contenedorMensajes);
    }


    public void agregarMensaje(String texto, boolean esPropio) {
        Label mensaje = new Label(texto);
        mensaje.getStyleClass().add(esPropio ? "message-bubble-user" : "message-bubble");
        mensaje.setWrapText(true);
        mensaje.setMaxWidth(300);

        HBox contenedor = new HBox(mensaje);
        contenedor.setPadding(new Insets(5));
        contenedor.setAlignment(esPropio ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        contenedorMensajes.getChildren().add(contenedor);

        // Fuerza el redibujado del layout para asegurar el scroll correcto
        contenedorMensajes.layout();

        // Desplazar automáticamente hacia el final
        Platform.runLater(() -> {
            scrollPaneChatsEnviadosRecibidos.setVvalue(1.0);
        });
    }

    @FXML
    private void enviarMensaje() {
        String texto = messageTextField.getText().trim();
        if (!texto.isEmpty()) {
            agregarMensaje(texto, true);
            messageTextField.clear();
        }
    }
}
