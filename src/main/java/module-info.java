module co.edu.uniquindio.red_social {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens co.edu.uniquindio.red_social to javafx.fxml;
    exports co.edu.uniquindio.red_social;
    exports co.edu.uniquindio.red_social.clases;
    opens co.edu.uniquindio.red_social.clases to javafx.fxml;
}