package edu.sait.team1.travelexperts.travelexpertsmaintenance;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

/**
 * TravelExpertsMaintenance.java
 * The entry point to our database application.
 * James B., Ali H., Trevor P., Evan D.
 * Fall 2022
 */
public class TravelExpertsMaintenance extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TravelExpertsMaintenance.class.getResource("travel-experts.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Travel Experts Maintenance");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}