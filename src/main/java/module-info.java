module com.example.pokemon_showdown {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pokemon_showdown to javafx.fxml;
    exports com.example.pokemon_showdown;
    exports com.example.pokemon_showdown.Classes;
    opens com.example.pokemon_showdown.Classes to javafx.fxml;
    exports com.example.pokemon_showdown.Controller;
    opens com.example.pokemon_showdown.Controller to javafx.fxml;
}