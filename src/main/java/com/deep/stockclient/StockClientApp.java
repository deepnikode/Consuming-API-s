package com.deep.stockclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StockClientApp extends Application {
    private static final Logger LOGGER = Logger.getLogger(StockClientApp.class.getName());
    private static Scene scene;

    @Override
    public void start(Stage stage) {
        try {
            scene = new Scene(loadFXML("user_view1"), 640, 480);
            stage.setScene(scene);
            stage.setTitle("LOGIN");
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load FXML", e);
        }
    }

    static void setRoot(String fxml) {
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to set root FXML", e);
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StockClientApp.class.getResource("/" + fxml + ".fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load FXML file: " + fxml, e);
            throw e;
        }
    }

    public static void main(String[] args) {
        launch(args); 
    }
}
