module com.example.pokemon_showdown {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    exports com.example.pokemon_showdown;
    exports com.example.pokemon_showdown.Classes;
    exports com.example.pokemon_showdown.Controller;
    exports com.example.pokemon_showdown.Controller.view;
    opens com.example.pokemon_showdown to javafx.fxml;
    opens com.example.pokemon_showdown.Classes to javafx.fxml;
    opens com.example.pokemon_showdown.Controller to javafx.fxml;
    opens com.example.pokemon_showdown.Controller.view to javafx.fxml;
}