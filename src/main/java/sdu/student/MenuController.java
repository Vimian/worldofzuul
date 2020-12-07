package sdu.student;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void changeSceneEditor(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameEditor.fxml"));
            Stage stage = (Stage) gameButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void changeSceneGame(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader();
            FXMLController controller = new FXMLController();


            loader.setControllerFactory(aClass -> controller);
            //Defines the FXML file
            loader.setLocation(getClass().getResource("scene.fxml"));



            Stage stage = (Stage) gameButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            stage.setScene(scene);

//            scene.setOnKeyPressed(new EventHandler<KeyEvent>() { - non lampda implementation
//                @Override
//                public void handle(KeyEvent event) {
//                    switch (event.getCode()) {
//                        case A:
//                            controller.moveEast(actionEvent);
//                        ...
//                    }
//                }
//            });

            scene.addEventHandler(KeyEvent.KEY_PRESSED, (e) -> {

                switch(e.getCode()){
                    case W:
                        controller.moveNorth(actionEvent);
                        break;
                    case D:
                        controller.moveEast(actionEvent);
                        break;
                    case S:
                        controller.moveSouth(actionEvent);
                        break;
                    case A:
                        controller.moveWest(actionEvent);
                        break;
                }


            } );
            stage.setScene(scene);


        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void showInstructions(ActionEvent actionEvent) {

        instructionsPane.setVisible(true);

        FadeTransition ft = new FadeTransition(Duration.millis(instructionsShowDelay), instructionsPane);
            ft.setFromValue(0.0);
            ft.setToValue(1);
        ft.play();



    }
}
