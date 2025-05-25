module co.edu.uniquindio.red_social {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jakarta.mail;




    opens co.edu.uniquindio.red_social to javafx.fxml;
    exports co.edu.uniquindio.red_social;
    exports co.edu.uniquindio.red_social.controllers;
    opens co.edu.uniquindio.red_social.controllers to javafx.fxml;
}