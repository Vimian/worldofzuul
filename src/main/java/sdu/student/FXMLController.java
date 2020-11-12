package sdu.student;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import worldofzuul.Game;
import worldofzuul.world.Direction;

import java.net.URL;
import java.util.ResourceBundle;

import static worldofzuul.util.Data.jsonToGame;
import static worldofzuul.util.Data.readConfigFile;

public class FXMLController implements Initializable {
    private static final String configFileName = "gameConfig.json";

    @FXML
    private Label label;

    @FXML
    private Game game;

    @FXML
    private Label playerPositionProperty;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");

        loadGame();
    }


    private void loadGame(){
        game = jsonToGame(readConfigFile(configFileName));
        game.reconfigureRooms();

        bindProperties();
    }

    private void bindProperties(){
        playerPositionProperty.textProperty()
                .bindBidirectional(game.getPlayer().getPos().vectorValueProperty());

    }


    public void moveNorth(ActionEvent actionEvent) {
        game.move(Direction.NORTH);
    }
    public void moveSouth(ActionEvent actionEvent) {
        game.move(Direction.SOUTH);
    }
    public void moveEast(ActionEvent actionEvent) {
        game.move(Direction.EAST);
    }
    public void moveWest(ActionEvent actionEvent) {
        game.move(Direction.WEST);
    }
    public void interact(ActionEvent actionEvent) {
        game.interact();
    }
}