module co.edu.uniquindio.red_social {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;



    opens co.edu.uniquindio.red_social to javafx.fxml;
    exports co.edu.uniquindio.red_social;
}