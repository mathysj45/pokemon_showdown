package com.example.pokemon_showdown.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneManager {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void switchScene(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlFileName));
            Parent rootNode = loader.load();
            primaryStage.getScene().setRoot(rootNode);
        } catch (IOException exception) {
            throw new RuntimeException("Échec du chargement de la vue : " + fxmlFileName, exception);
        }
    }
}