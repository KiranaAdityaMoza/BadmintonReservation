package com.badminton.app.badmintonreservation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.badminton.app.badmintonreservation.util.SceneLoader;


import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        SceneLoader.setPrimaryStage(stage);
        
        scene = new Scene(loadFXML("LoginView"), 640, 480);

        stage.setScene(scene);
        stage.setTitle("Badminton Reservation");
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
    return new FXMLLoader(
            App.class.getResource("/fxml/" + fxml + ".fxml")
    ).load();
}


    public static void main(String[] args) {
        launch();
    }
}
