package sdu.student;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    private final static int instructionsShowDelay = 2000;
    public Button editorButton;
    public Button gameButton;
    public GridPane instructionsPane;

    public static void loadGameScene(Stage stage, Class<?> controllerClass, FXMLController fxmlController) {

        if (fxmlController != null) {
            fxmlController = null;
        }


        try {
            FXMLLoader loader = new FXMLLoader();
            FXMLController controller = new FXMLController(stage);


            loader.setControllerFactory(aClass -> controller);
            loader.setLocation(controllerClass.getResource("scene.fxml"));


            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(controllerClass.getResource("styles.css").toExternalForm());
            stage.setScene(scene);


            scene.addEventHandler(KeyEvent.KEY_PRESSED, (e) -> {

                switch (e.getCode()) {
                    case W -> controller.moveNorth();
                    case D -> controller.moveEast();
                    case S -> controller.moveSouth();
                    case A -> controller.moveWest();
                }


            });
            stage.setScene(scene);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void changeSceneEditor() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameEditor.fxml"));
            Stage stage = (Stage) gameButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeSceneGame() {
        Stage stage = (Stage) gameButton.getScene().getWindow();

        loadGameScene(stage, getClass(), null);

    }

    public void showInstructions() {

        instructionsPane.setVisible(true);

        FadeTransition ft = new FadeTransition(Duration.millis(instructionsShowDelay), instructionsPane);
        ft.setFromValue(0.0);
        ft.setToValue(1);
        ft.play();


    }
}
