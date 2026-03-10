module com.example.pokemon_showdown {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pokemon_showdown to javafx.fxml;
    exports com.example.pokemon_showdown;
}