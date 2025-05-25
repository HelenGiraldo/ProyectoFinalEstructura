package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.interfaces.ChatObserver;
import co.edu.uniquindio.red_social.clases.social.Chat;
import co.edu.uniquindio.red_social.clases.social.Mensaje;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import co.edu.uniquindio.red_social.estructuras.ListaSimplementeEnlazada;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;

public class ChatController implements  ChatObserver {

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

    ToggleGroup grupoUsuarios = new ToggleGroup();

    Estudiante estudianteActual = (Estudiante) PerfilUsuario.getUsuarioActual();
    Chat chatActual;

    @FXML
    public void initialize() {

        MensajesButton.setSelected(true);


        if (imagenPerfil != null) {
            double radius = imagenPerfil.getFitWidth() / 2;
            Circle clip = new Circle(radius, radius, radius);
            imagenPerfil.setClip(clip);
        }

        if(estudianteActual.getChats().isEmpty()) {

            return;
        }

        inicializarUsuarios();
        configurarChat();
        configurarEventos();

        scrollPaneContenedorMensajes.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        contenedorMensajes.setFillWidth(true);

        contenedorMensajes.heightProperty().addListener((obs, oldVal, newVal) -> {
            scrollPaneContenedorMensajes.layout();
            scrollPaneContenedorMensajes.setVvalue(1.0);
        });

        PerfilUsuario perfil = PerfilUsuario.getInstancia();
        if (perfil.getImagenPerfil() != null) {
            imagenPerfil.setImage(perfil.getImagenPerfil());
        }



    }

    private void inicializarUsuarios() {
        userchatListVBox.getChildren().clear();

        for (Estudiante usuario : estudianteActual.getContactos()) {
            if (usuario.equals(estudianteActual)) continue;

            ToggleButton boton = new ToggleButton();
            boton.setPrefWidth(112);
            boton.setPrefHeight(42);
            boton.setToggleGroup(grupoUsuarios);
            boton.setUserData(usuario);
            boton.getStyleClass().add("sidebar-button-chat");

            String rutaOriginal = usuario.getImagenPerfil().toString(); // "src\\main\\resources\\co\\edu\\uniquindio\\...\\imagen_perfil2.jpg"
            String rutaClasspath = rutaOriginal.replace("\\", "/").replace("src/main/resources/", "");

            if (!rutaClasspath.startsWith("/")) {
                rutaClasspath = "/" + rutaClasspath;
            }

            URL url = getClass().getResource(rutaClasspath);
            ImageView avatar = null;
            if (url != null) {
                avatar = new ImageView(new Image(url.toExternalForm()));
                avatar.setFitWidth(20);
                avatar.setFitHeight(19);
                avatar.getStyleClass().add("avatar");
            } else {
                System.err.println("No se encontró la imagen en: " + rutaClasspath);
            }

            Label nombre = new Label(usuario.getNombre());
            nombre.setPrefHeight(19);
            nombre.setPrefWidth(70);
            nombre.getStyleClass().add("contact-name");

            HBox hbox = new HBox(8, avatar, nombre);
            hbox.setAlignment(Pos.CENTER_LEFT);
            boton.setGraphic(hbox);

            boton.setOnAction(e -> {
                if (boton.isSelected()) {
                    seleccionarUsuario(boton);
                }
            });

            userchatListVBox.getChildren().add(boton);
        }


        if (!userchatListVBox.getChildren().isEmpty()) {
            ToggleButton first = (ToggleButton) userchatListVBox.getChildren().get(0);
            first.setSelected(true);
            seleccionarUsuario(first);
        }
    }

    private void configurarChat() {
        contenedorMensajes.setSpacing(10);
        contenedorMensajes.setPadding(new Insets(10));

    }

    private void configurarEventos() {
        if (messageTextField == null || sendButton == null || botonBuscar == null) {
            System.err.println("Error: Elementos para eventos no inicializados");
        }
    }

    private void seleccionarUsuario(ToggleButton botonUsuario) {
        if (botonUsuario == null || userchatListVBox == null) return;

        // Limpiar mensajes actuales
        contenedorMensajes.getChildren().clear();

        // Deseleccionar otros botones
        userchatListVBox.getChildren().forEach(node -> {
            if (node instanceof ToggleButton) {
                ToggleButton tb = (ToggleButton) node;
                if (tb != botonUsuario) {
                    tb.setSelected(false);
                }
            }
        });

        Estudiante receptor = (Estudiante) botonUsuario.getUserData();
        if (receptor == null) return;

        // Obtener o crear el chat con el nuevo usuario
        chatActual = obtenerOCrearChat(receptor);

        // Actualizar interfaz con el nuevo usuario
        actualizarInterfazUsuario(receptor);

        // Cargar mensajes del nuevo chat
        cagarMensajesChat(chatActual);
    }

    private Chat obtenerOCrearChat(Estudiante receptor) {
        // Buscar chat existente
        for (Chat chat : estudianteActual.getChats()) {
            if ((chat.getEstudiante().equals(estudianteActual) && chat.getEstudiante2().equals(receptor)) ||
                    (chat.getEstudiante().equals(receptor) && chat.getEstudiante2().equals(estudianteActual))) {
                return chat;
            }
        }

        // Si no existe, crear uno nuevo
        Chat nuevoChat = new Chat(estudianteActual, receptor);
        estudianteActual.getChats().add(nuevoChat);
        receptor.getChats().add(nuevoChat); // Asegurar que el receptor también tenga el chat

        return nuevoChat;
    }


    private void actualizarInterfazUsuario(Estudiante receptor) {
        if (LabelUser2 == null || AvatarCompañero == null || contenedorMensajes == null) {
            System.err.println("Error: Elementos de interfaz no inicializados");
            return;
        }

        LabelUser2.setText(receptor.getNombre() + " ");
        try {

            AvatarCompañero.setImage(new Image(receptor.getImagenPerfil().toURI().toString()));


        } catch (Exception e) {
            System.err.println("Error cargando imagen de avatar: " + e.getMessage());
        }

        contenedorMensajes.getChildren().clear();

        Platform.runLater(() -> scrollPaneContenedorMensajes.setVvalue(0.0));
    }

    @FXML
    private void enviarMensaje(ActionEvent event) {
        if (messageTextField == null || contenedorMensajes == null) return;

        String texto = messageTextField.getText().trim();
        if (!texto.isEmpty()) {
            agregarMensaje(texto, true);


        }

        chatActual.enviarMensaje(new Mensaje(texto, LocalDateTime.now(), estudianteActual, chatActual) );

    }

    private void agregarMensaje(String texto, boolean esPropio) {
        if (contenedorMensajes == null) return;

        Platform.runLater(() -> {
            messageTextField.clear();
            messageTextField.requestFocus();
            scrollPaneContenedorMensajes.setVvalue(1.0);
        });

        Label mensaje = new Label(texto);
        mensaje.getStyleClass().add(esPropio ? "message-bubble" : "message-bubble-user");
        mensaje.setWrapText(true);
        mensaje.setPadding(new Insets(8, 12, 8, 12));

        HBox contenedor = new HBox(mensaje);
        contenedor.setPadding(new Insets(5));

        HBox.setHgrow(mensaje, Priority.ALWAYS);  // Esto permite que el mensaje ocupe todo el espacio disponible
        contenedor.setAlignment(esPropio ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        contenedorMensajes.getChildren().add(contenedor);
    }

    @FXML
    private void buscarUsuario(ActionEvent event) {
        if (campoBusqueda == null || userchatListVBox == null) return;

        String busqueda = campoBusqueda.getText().trim();
        if (!busqueda.isEmpty()) {
            campoBusqueda.clear();
        }
    }




    private void cargarMensajes(Estudiante receptor) {
        contenedorMensajes.getChildren().clear();

        Estudiante emisor = (Estudiante) PerfilUsuario.getInstancia().getUsuarioActual();
        if (emisor == null || receptor == null) return;

        for (Chat chat : estudianteActual.getChats()) {
            if ( (chat.getEstudiante().equals(emisor) && chat.getEstudiante2().equals(receptor)) ||
                    (chat.getEstudiante().equals(receptor) && chat.getEstudiante2().equals(emisor)) ) {
                chatActual = chat;
                break;
            }
        }

        if (chatActual == null) return;

        ListaSimplementeEnlazada<Mensaje> mensajes = chatActual.getMensajes();

        for (Mensaje mensaje : mensajes) {
            boolean esPropio = mensaje.getUsuarioRemitente().equals(emisor.getId());
            agregarMensaje(mensaje.getMensaje(), esPropio);
        }
    }


    private void cagarMensajesChat(Chat chat) {
        // Limpiar mensajes actuales
        contenedorMensajes.getChildren().clear();

        // Verificar que el chat no sea nulo y tenga mensajes
        if (chat == null || chat.getMensajes().isEmpty()) {
            return;
        }

        // Cargar todos los mensajes del chat
        for (Mensaje mensaje : chat.getMensajes()) {
            boolean esPropio = mensaje.getUsuarioRemitente().equals(estudianteActual);
            agregarMensaje(mensaje.getMensaje(), esPropio);
        }

        // Forzar scroll al final
        Platform.runLater(() -> {
            scrollPaneContenedorMensajes.setVvalue(1.0);
        });
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

    @Override
    public void update() {
        cagarMensajesChat(chatActual);
    }

    @FXML
    private void irAContenidos(ActionEvent event) {
        // Lógica para ir a contenidos
    }

    @FXML
    private void irAGruposEstudio(ActionEvent event) {
        // Lógica para ir a contenidos
    }

    @FXML
    private void irASugerencias(ActionEvent event) {
        // Lógica para ir a contenidos
    }

    @FXML
    private void irASolicitudesAyuda(ActionEvent event) {
        chatActual.getMensajes().show();
    }
}