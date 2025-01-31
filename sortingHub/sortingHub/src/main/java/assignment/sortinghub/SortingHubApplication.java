package assignment.sortinghub;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

// Main entry point for the JavaFX application
public class SortingHubApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML file to define the user interface layout
        FXMLLoader fxmlLoader = new FXMLLoader(SortingHubApplication.class.getResource("SortingHub-view.fxml"));

        // Create a Scene with the loaded FXML and set the initial size (600x400)
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        // Set the title of the application window
        stage.setTitle("SortingHub");

        // Set the window icon from a resource file
        stage.getIcons().add(new Image(getClass().getResource("WesternLogo.png").toExternalForm()));

        // Set the scene for the stage (window) and show the stage
        stage.setScene(scene);
        stage.show();
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch();
    }
}
