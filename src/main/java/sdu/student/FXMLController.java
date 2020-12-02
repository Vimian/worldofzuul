package sdu.student;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import worldofzuul.Game;
import worldofzuul.Player;
import worldofzuul.world.Direction;

public class FXMLController implements Initializable {

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