package sdu.student;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    private static final String configFileName = "gameConfig.txt";

    @Override
    public void start(Stage stage) throws Exception {



        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("JavaFX and Gradle");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch(args);

    }



}
