package sunmade.jcircuit;

import javafx.application.Application;
import javafx.stage.Stage;

public class JCircuit extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // Init the stage
        stage.setTitle("JCircuit");
        stage.setMinWidth(400);
        stage.setMinHeight(300);
        stage.setMaximized(true);

        // Show the scene
        stage.show();

    }

}
