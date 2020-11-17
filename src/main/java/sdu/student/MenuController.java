package sdu.student;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    public Button editorButton;
    public Button gameButton;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene.fxml"));
            Stage stage = (Stage) gameButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            stage.setScene(scene);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
