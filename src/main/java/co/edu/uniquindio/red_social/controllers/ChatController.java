package co.edu.uniquindio.red_social.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    // Elementos FXML
    @FXML private ImageView AvatarCompañero;
    @FXML private ImageView AvatarCompañero1;
    @FXML private ImageView AvatarCompañero2chat;
    @FXML private HBox HboxAvatarNombre;
    @FXML private HBox HboxAvatarNombre1;
    @FXML private HBox HboxAvatarNombre2;
    @FXML private ToggleButton InicioButton;
    @FXML private Label LabelUser2;
    @FXML private Label LabelUser2chat;
    @FXML private Label LabelUser3chat;
    @FXML private ToggleButton MensajesButton;
    @FXML private ToggleButton SolicitudesDeAyudaButton;
    @FXML private ToggleButton SugerenciasButton;
    @FXML private Label TextFieldSaludo;
    @FXML private Label TextFieldTitle;
    @FXML private Button botonBuscar;
    @FXML private ToggleButton buttonUser2;
    @FXML private ToggleButton buttonUser3;
    @FXML private TextField campoBusqueda;
    @FXML private VBox chatListVBoxFondoDos;
    @FXML private VBox chatListVBoxFondoUno;
    @FXML private VBox chatSpace;
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
        // Configuración inicial del avatar redondo
        Platform.runLater(() -> {
            if (imagenPerfil != null) {
                double radius = imagenPerfil.getFitWidth() / 2;
                Circle clip = new Circle(radius, radius, radius);
                imagenPerfil.setClip(clip);
            }
        });

        // Configuración inicial de usuarios
        inicializarUsuarios();

        // Configuración del chat
        configurarChat();

        // Configurar listeners y eventos
        configurarEventos();

        scrollPaneChatsEnviadosRecibidos.setFitToWidth(true);
        scrollPaneChatsEnviadosRecibidos.setFitToHeight(true);
        scrollPaneChatsEnviadosRecibidos.vvalueProperty().bind(
                contenedorMensajes.heightProperty());
    }

    private void inicializarUsuarios() {
        // Verificar si el contenedor existe
        if (userchatListVBox == null) {
            System.err.println("Error: userchatListVBox no está inicializado");
            return;
        }

        // Configurar botones existentes (User2 y User3)
        configurarBotonUsuario(buttonUser2, "User2");
        configurarBotonUsuario(buttonUser3, "User3");

        // Seleccionar el primer usuario por defecto
        if (buttonUser2 != null) {
            buttonUser2.setSelected(true);
            seleccionarUsuario(buttonUser2);
        }
    }

    private void configurarBotonUsuario(ToggleButton boton, String nombreUsuario) {
        if (boton == null) return;

        boton.setUserData(nombreUsuario);
        boton.setOnAction(e -> {
            if (boton.isSelected()) {
                seleccionarUsuario(boton);
            }
        });
    }

    private void configurarChat() {
        // Verificar elementos nulos
        if (contenedorMensajes == null || scrollPaneChatsEnviadosRecibidos == null) {
            System.err.println("Error: Elementos del chat no inicializados");
            return;
        }

        // Estilo del contenedor de mensajes
        contenedorMensajes.setSpacing(10);
        contenedorMensajes.setPadding(new Insets(10));

        // Configuración del scroll pane
        scrollPaneChatsEnviadosRecibidos.setFitToWidth(true);
        scrollPaneChatsEnviadosRecibidos.vvalueProperty().bind(contenedorMensajes.heightProperty());
    }

    private void configurarEventos() {
        // Verificar elementos nulos
        if (messageTextField == null || sendButton == null || botonBuscar == null) {
            System.err.println("Error: Elementos para eventos no inicializados");
            return;
        }

        // Evento para enviar mensaje al presionar Enter
        messageTextField.setOnAction(this::enviarMensaje);

        // Evento para enviar mensaje al hacer clic en el botón
        sendButton.setOnAction(this::enviarMensaje);

        // Evento para buscar
        botonBuscar.setOnAction(this::buscarUsuario);
    }

    private void seleccionarUsuario(ToggleButton botonUsuario) {
        // Verificar nulos
        if (botonUsuario == null || userchatListVBox == null) return;

        // Deseleccionar todos los botones primero
        userchatListVBox.getChildren().forEach(node -> {
            if (node instanceof ToggleButton) {
                ToggleButton tb = (ToggleButton) node;
                if (tb != botonUsuario) {
                    tb.setSelected(false);
                }
            }
        });

        // Actualizar la interfaz para este usuario
        String nombreUsuario = (String) botonUsuario.getUserData();
        actualizarInterfazUsuario(nombreUsuario);
    }

    private void actualizarInterfazUsuario(String nombreUsuario) {
        // Verificar elementos nulos
        if (LabelUser2 == null || AvatarCompañero == null || contenedorMensajes == null) {
            System.err.println("Error: Elementos de interfaz no inicializados");
            return;
        }

        // Actualizar el label del usuario activo
        LabelUser2.setText(nombreUsuario);

        // Actualizar avatar (puedes implementar lógica para cargar imagen real)
        try {
            AvatarCompañero.setImage(new Image("/images/default-avatar.png"));
        } catch (Exception e) {
            System.err.println("Error cargando imagen de avatar: " + e.getMessage());
        }

        // Limpiar mensajes anteriores
        contenedorMensajes.getChildren().clear();

        // Aquí podrías cargar los mensajes de este usuario desde tu modelo
        // Por ahora agregamos mensajes de prueba
        agregarMensaje("Hola " + nombreUsuario + ", ¿cómo estás?", false);
        agregarMensaje("Estoy bien, gracias", true);
    }

    private void agregarMensaje(String texto, boolean esPropio) {
        if (contenedorMensajes == null) return;

        Platform.runLater(() -> {
            Label mensaje = new Label(texto);
            mensaje.getStyleClass().add(esPropio ? "message-bubble-user" : "message-bubble");
            mensaje.setWrapText(true);
            mensaje.setMaxWidth(300);
            mensaje.setPadding(new Insets(8, 12, 8, 12));

            HBox contenedor = new HBox(mensaje);
            contenedor.setPadding(new Insets(5));
            contenedor.setAlignment(esPropio ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

            contenedorMensajes.getChildren().add(contenedor);

            // Asegurar que el scroll se mantenga abajo
            scrollPaneChatsEnviadosRecibidos.setVvalue(1.0);
        });
    }

    @FXML
    private void enviarMensaje(ActionEvent event) {
        if (messageTextField == null || contenedorMensajes == null) return;

        String texto = messageTextField.getText().trim();
        if (!texto.isEmpty()) {
            // Agregar mensaje
            agregarMensaje(texto, true);

            // Limpiar y mantener foco
            Platform.runLater(() -> {
                messageTextField.clear();
                messageTextField.requestFocus();

                // Forzar actualización del layout
                contenedorMensajes.requestLayout();
                scrollPaneChatsEnviadosRecibidos.setVvalue(1.0);
            });
        }
    }

    @FXML
    private void buscarUsuario(ActionEvent event) {
        if (campoBusqueda == null || userchatListVBox == null) return;

        String busqueda = campoBusqueda.getText().trim();
        if (!busqueda.isEmpty()) {
            // Aquí iría la lógica para buscar usuarios existentes
            // Por ahora solo limpiamos el campo
            campoBusqueda.clear();

            // Ejemplo de cómo agregar un nuevo usuario
            // agregarNuevoUsuario(busqueda);
        }
    }

    public void agregarNuevoUsuario(String nombreUsuario) {
        if (userchatListVBox == null) return;

        // Implementación para agregar nuevos usuarios dinámicamente
        ToggleButton nuevoBoton = new ToggleButton(nombreUsuario);
        configurarBotonUsuario(nuevoBoton, nombreUsuario);
        userchatListVBox.getChildren().add(nuevoBoton);
    }
}