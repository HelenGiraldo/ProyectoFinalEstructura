package co.edu.uniquindio.red_social;

import co.edu.uniquindio.red_social.clases.RedSocial;
import co.edu.uniquindio.red_social.clases.usuarios.PerfilUsuario;
import co.edu.uniquindio.red_social.data_base.UtilSQL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App3 extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("sugerencias.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle(RedSocial.getInstance().getNombre());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        RedSocial red = RedSocial.getInstance("Think Together");
        iniciarDatos();
        PerfilUsuario.setUsuarioActual(red.getEstudiantes().get(1));
        launch();
    }

    public static void iniciarDatos(){
        RedSocial red = RedSocial.getInstance("Think Together");
        UtilSQL.obtenerEstudiantes();
        UtilSQL.obtenerGrupos();
        UtilSQL.obtenerAdministradores();
        UtilSQL.obtenerPublicaciones();
        UtilSQL.obtenerTodasLasCalificaciones();
        UtilSQL.cargarRelacionesAmistad();
        UtilSQL.cargarMiembrosDeGrupos();
        UtilSQL.cargarGrupos();
        UtilSQL.obtenerTodosLosChats();
        UtilSQL.obtenerTodasLasSolicitudes();
        UtilSQL.obtenerTodasLasPreferencias();
        red.getEstudiantes().show();
    }
}
