package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    // Elementos FXML
    @FXML
    private ImageView AvatarCompañero;

    @FXML
    private ImageView AvatarCompañero1;

    @FXML
    private ImageView AvatarCompañero2chat;

    @FXML
    private HBox HboxAvatarNombre;

    @FXML
    private HBox HboxAvatarNombre1;

    @FXML
    private HBox HboxAvatarNombre2;

    @FXML
    private ToggleButton InicioButton;

    @FXML
    private Label LabelUser2;

    @FXML
    private Label LabelUser2chat;

    @FXML
    private Label LabelUser3chat;

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
    private Button botonBuscar;

    @FXML
    private ToggleButton buttonUser2;

    @FXML
    private ToggleButton buttonUser3;

    @FXML
    private TextField campoBusqueda;

    @FXML
    private VBox chatListVBoxFondoDos;

    @FXML
    private VBox chatListVBoxFondoUno;

    @FXML
    private VBox chatSpace;

    @FXML
    private ToggleButton configuracionPerfilButton;

    @FXML
    private VBox contenedorMensajes;

    @FXML
    private ToggleButton gruposEstudioButton;

    @FXML
    private ImageView imagenPerfil;

    @FXML
    private TextField messageTextField;

    @FXML
    private ToggleButton misContenidosButton;

    @FXML
    private ScrollPane scrollPaneChats;

    @FXML
    private ScrollPane scrollPaneChatsEnviadosRecibidos;

    @FXML
    private ScrollPane scrollPaneContenedorMensajes;

    @FXML
    private Button sendButton;

    @FXML
    private VBox userchatListVBox;

    @FXML
    private AnchorPane root;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MensajesButton.setSelected(true);
        Platform.runLater(() -> {
            if (imagenPerfil != null) {
                double radius = imagenPerfil.getFitWidth() / 2;
                Circle clip = new Circle(radius, radius, radius);
                imagenPerfil.setClip(clip);
            }
        });


        inicializarUsuarios();
        configurarChat();
        configurarEventos();

        scrollPaneContenedorMensajes.setFitToWidth(true);
        scrollPaneContenedorMensajes.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


        contenedorMensajes.setFillWidth(true);

        contenedorMensajes.heightProperty().addListener((obs, oldVal, newVal) -> {
            scrollPaneContenedorMensajes.layout();
            scrollPaneContenedorMensajes.setVvalue(1.0);
        });

        PerfilUsuario perfil = PerfilUsuario.getInstancia();
        perfil.imagenPerfilProperty().addListener((obs, oldImg, newImg) -> {
            if (newImg != null) {
                imagenPerfil.setImage(newImg);
            }
        });

        // Para inicializar desde el comienzo
        if (perfil.getImagenPerfil() != null) {
            imagenPerfil.setImage(perfil.getImagenPerfil());
        }


    }

    private void inicializarUsuarios() {
        if (userchatListVBox == null) {
            System.err.println("Error: userchatListVBox no está inicializado");
            return;
        }

        configurarBotonUsuario(buttonUser2, "User2");
        configurarBotonUsuario(buttonUser3, "User3");

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
        contenedorMensajes.setSpacing(10);
        contenedorMensajes.setPadding(new Insets(10));
        scrollPaneContenedorMensajes.setFitToWidth(true);

    }

    private void configurarEventos() {
        if (messageTextField == null || sendButton == null || botonBuscar == null) {
            System.err.println("Error: Elementos para eventos no inicializados");
            return;
        }

        messageTextField.setOnAction(this::enviarMensaje);
        sendButton.setOnAction(this::enviarMensaje);
        botonBuscar.setOnAction(this::buscarUsuario);
    }

    private void seleccionarUsuario(ToggleButton botonUsuario) {
        if (botonUsuario == null || userchatListVBox == null) return;

        userchatListVBox.getChildren().forEach(node -> {
            if (node instanceof ToggleButton) {
                ToggleButton tb = (ToggleButton) node;
                if (tb != botonUsuario) {
                    tb.setSelected(false);
                }
            }
        });

        String nombreUsuario = (String) botonUsuario.getUserData();
        actualizarInterfazUsuario(nombreUsuario);
    }

    private void actualizarInterfazUsuario(String nombreUsuario) {
        if (LabelUser2 == null || AvatarCompañero == null || contenedorMensajes == null) {
            System.err.println("Error: Elementos de interfaz no inicializados");
            return;
        }

        LabelUser2.setText(nombreUsuario);

        try {
            AvatarCompañero.setImage(new Image("/images/default-avatar.png"));
        } catch (Exception e) {
            System.err.println("Error cargando imagen de avatar: " + e.getMessage());
        }

        contenedorMensajes.getChildren().clear();

        agregarMensaje("Hola " + nombreUsuario + ", ¿cómo estás?", false);
        agregarMensaje("Estoy bien, gracias", true);

        Platform.runLater(() -> scrollPaneContenedorMensajes.setVvalue(0.0));
    }

    @FXML
    private void enviarMensaje(ActionEvent event) {
        if (messageTextField == null || contenedorMensajes == null) return;

        String texto = messageTextField.getText().trim();
        if (!texto.isEmpty()) {
            agregarMensaje(texto, true);

            Platform.runLater(() -> {
                messageTextField.clear();
                messageTextField.requestFocus();
                scrollPaneContenedorMensajes.setVvalue(1.0);
            });
        }
    }

    private void agregarMensaje(String texto, boolean esPropio) {
        if (contenedorMensajes == null) return;

        Label mensaje = new Label(texto);
        mensaje.getStyleClass().add(esPropio ? "message-bubble-user" : "message-bubble");
        mensaje.setWrapText(true);
        mensaje.setMaxWidth(300);
        mensaje.setPadding(new Insets(8, 12, 8, 12));

        HBox contenedor = new HBox(mensaje);
        contenedor.setPadding(new Insets(5));
        contenedor.setAlignment(esPropio ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        contenedorMensajes.getChildren().add(contenedor);

    }

    @FXML
    private void buscarUsuario(ActionEvent event) {
        if (campoBusqueda == null || userchatListVBox == null) return;

        String busqueda = campoBusqueda.getText().trim();
        if (!busqueda.isEmpty()) {
            campoBusqueda.clear();
            //Aquí se puede agregar un nuevo usuario, segun el nombre que estemos buscanod
        }
    }

    public void agregarNuevoUsuario(String nombreUsuario) {
        if (userchatListVBox == null) return;

        ToggleButton nuevoBoton = new ToggleButton(nombreUsuario);
        configurarBotonUsuario(nuevoBoton, nombreUsuario);
        userchatListVBox.getChildren().add(nuevoBoton);
    }

    @FXML
    private void irAConfig(ActionEvent event) {
        try {

            URL configUrl = getClass().getResource("/co/edu/uniquindio/red_social/configuracion.fxml");
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
    private void irAInicio(ActionEvent event) {
        try {

            URL configUrl = getClass().getResource("/co/edu/uniquindio/red_social/Inicio.fxml");
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
}
