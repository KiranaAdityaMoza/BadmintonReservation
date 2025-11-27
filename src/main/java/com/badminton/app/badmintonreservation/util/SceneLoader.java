package com.badminton.app.badmintonreservation.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneLoader {

    private static Stage primaryStage;

    // Dipanggil sekali dari Main.java
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void load(String fxmlName) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource("/fxml/" + fxmlName));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

