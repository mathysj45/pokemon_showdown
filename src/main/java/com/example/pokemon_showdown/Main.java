package com.example.pokemon_showdown;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.
                                getResource("pokemon-view.fxml"));
        Scene mainScene = new Scene(fxmlLoader.load(), 500, 570);

        String cssPath = Objects.requireNonNull(Main.class.
                                getResource("style.css")).toExternalForm();
        mainScene.getStylesheets().add(cssPath);

        primaryStage.setTitle("Battle!");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}