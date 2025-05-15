package co.edu.uniquindio.red_social.controllers;

import co.edu.uniquindio.red_social.clases.contenidos.Contenido;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TarjetaContenidoController {


    @FXML
    private HBox HboxTusContendiosContenido;

    @FXML
    private VBox TuscontendiosVBox;

    @FXML
    private VBox VBoxTusContenidosContenido;

    @FXML
    private Label autorLabel;

    @FXML
    private Label descripcionLabel;

    @FXML
    private Label temaLabel;

    @FXML
    private Label tipoLabel;

    @FXML
    private Label tituloLabel;

    @FXML
    private Label totalPublicadosLabel;

    @FXML
    private Label valoracionLabel;

    @FXML
    private Label archivoAdjuntoLabel;

    public void inicializarDatos(Contenido contenido) {


    }



    public void setContenido(Contenido contenido) {
        tituloLabel.setText(contenido.getTitulo());
        autorLabel.setText(contenido.getAutor());
        descripcionLabel.setText(contenido.getDescripcion());
        tipoLabel.setText(contenido.getTipo());
        valoracionLabel.setText("★ " + contenido.getCalificacionPromedio());

        File archivo = contenido.getArchivoAdjunto();

        if (archivo != null && archivo.exists()) {
            archivoAdjuntoLabel.setText("Archivo: " + archivo.getName());

            archivoAdjuntoLabel.setOnMouseClicked(event -> {
                try {
                    java.awt.Desktop.getDesktop().open(archivo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            archivoAdjuntoLabel.setText("No hay archivo adjunto");
            archivoAdjuntoLabel.setOnMouseClicked(null);
        }
    }

    private void abrirArchivoAdjunto(File archivo) {
        if (archivo != null && archivo.exists()) {
            try {
                Desktop.getDesktop().open(archivo);
            } catch (IOException e) {
                e.printStackTrace();
                // Puedes mostrar un diálogo de error aquí si quieres
            }
        } else {
            System.out.println("Archivo no existe o es nulo");
        }
    }

    public void setContenido(Contenido contenido, int totalPublicados) {
        tituloLabel.setText(contenido.getTitulo());
        autorLabel.setText(contenido.getAutor());
        descripcionLabel.setText(contenido.getDescripcion());
        tipoLabel.setText(contenido.getTipo());
        valoracionLabel.setText("★ " + contenido.getCalificacionPromedio());
        totalPublicadosLabel.setText("Total Publicados: " + totalPublicados);

        if (contenido.getArchivoAdjunto() != null) {
            archivoAdjuntoLabel.setText(contenido.getArchivoAdjunto().getName());

            archivoAdjuntoLabel.setOnMouseClicked(event -> {
                abrirArchivoAdjunto(contenido.getArchivoAdjunto());
            });
        } else {
            archivoAdjuntoLabel.setText("Sin archivo adjunto");
            archivoAdjuntoLabel.setOnMouseClicked(null); // Quitar listener si no hay archivo
        }
    }




}
