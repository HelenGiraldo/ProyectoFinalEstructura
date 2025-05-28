package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.usuarios.Administrador;
import co.edu.uniquindio.red_social.clases.usuarios.Estudiante;
import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import co.edu.uniquindio.red_social.data_base.UtilSQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class RegistroController {

    private File archivoImagenSeleccionada;

    @FXML
    private Label LabelNombre;

    @FXML
    private TextField apellidoField;

    @FXML
    private VBox cardTransparente;

    @FXML
    private Button elegirImagen;

    @FXML
    private Button handleRegistrarse;

    @FXML
    private Label handleVolver;

    @FXML
    private Label labelApellido;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelRegistro;

    @FXML
    private Label labelRegistroValidacion;

    @FXML
    private TextField nombreField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private ImageView imagenPerfil;

    @FXML
    private AnchorPane root;

    @FXML private CheckBox adminCheckBox;
    @FXML private PasswordField adminPasswordField;

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleVolver(MouseEvent event) {
        cambiarEscenaLogin();
    }

    public void cambiarEscenaLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/red_social/Login.fxml"));
            Parent newRoot = loader.load();

            Stage stage = (Stage) handleVolver.getScene().getWindow();
            Scene newScene = new Scene(newRoot);
            stage.setScene(newScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdminCheckbox() {
        adminPasswordField.setVisible(adminCheckBox.isSelected());
    }



    @FXML
    private void registrarUsuario(ActionEvent event) {
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        String email = textFieldEmail.getText();
        String password = passwordField.getText();

        boolean esAdmin = adminCheckBox.isSelected();
        String claveAdminIngresada = adminPasswordField.getText().trim();



        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty()) {
            labelRegistroValidacion.setText("Por favor, complete todos los campos.");
            return;
        }

        if (imagenPerfil.getImage() == null) {
            labelRegistroValidacion.setText("Debe seleccionar una imagen de perfil.");
            return;
        }


        RedSocial redSocial = RedSocial.getInstance();

        if (esAdmin) {
            if (!claveAdminIngresada.equals("admin123")) {
                labelRegistroValidacion.setText("Contraseña de administrador incorrecta.");
                return;
            }

            Administrador admin = redSocial.crearAdministrador(nombre, apellido, email, password, new File("src/main/resources/co/edu/uniquindio/red_social/imagenes/imagen_correo.png"));
            PerfilUsuario.setUsuarioActual(admin);
            labelRegistro.setText("¡Registro exitoso como administrador!");
            cambiarEscenaLogin();
            return;

        }




        Estudiante estudiante= redSocial.crearEstudiante(nombre, apellido, email, password, new File("src/main/resources/co/edu/uniquindio/red_social/imagenes/imagePerfil.png"));
        System.out.println(estudiante);
        if (estudiante == null) {
            labelRegistroValidacion.setText("Error al crear el estudiante.");
            return;
        }


        int idGenerado = UtilSQL.insertarEstudiante(estudiante);
        if (idGenerado == -1) {
            labelRegistroValidacion.setText("Error al registrar el usuario en la base de datos.");
            return;
        }

        estudiante.setId(String.valueOf(idGenerado));

        if (archivoImagenSeleccionada != null) {
            try {

                File archivoOriginal = archivoImagenSeleccionada;

                String extension = archivoOriginal.getName().substring(archivoOriginal.getName().lastIndexOf("."));
                String nombreArchivoNuevo = "imagen_perfil" + estudiante.getId() + extension;

                File destino = new File("src/main/resources/co/edu/uniquindio/red_social/usuarios/imagenes_perfil/", nombreArchivoNuevo);
                System.out.println("Ruta de destino: " + destino.getPath());

                java.nio.file.Files.copy(
                        archivoOriginal.toPath(),
                        destino.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                );

                estudiante.setImagenPerfil(destino);

                Image imagenNueva = new Image(destino.toURI().toString());
                imagenPerfil.setImage(imagenNueva);
            } catch (Exception e) {
                e.printStackTrace();
                labelRegistroValidacion.setText("Error al guardar la imagen de perfil.");
            }
        }

        labelRegistro.setText("¡Registro exitoso!");

        UtilSQL.actualizarEstudiante(estudiante);

        PerfilUsuario.setUsuarioActual(estudiante);

        cambiarEscenaLogin();


    }


    @FXML
    private void seleccionarImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen de Perfil");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File archivoSeleccionado = fileChooser.showOpenDialog(stage);

        if (archivoSeleccionado != null) {
            Image imagen = new Image(archivoSeleccionado.toURI().toString());
            imagenPerfil.setImage(imagen);
            archivoImagenSeleccionada = archivoSeleccionado;
            System.out.println("Imagen cargada: " + archivoSeleccionado.toURI().toString());
        } else {
            System.out.println("Selección de imagen cancelada.");
        }
    }

}
