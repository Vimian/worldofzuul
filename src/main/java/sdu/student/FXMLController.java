package sdu.student;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import worldofzuul.Game;
import worldofzuul.world.Direction;

import java.net.URL;
import java.util.*;

import static worldofzuul.util.Data.*;

public class FXMLController implements Initializable {
    private static final String configFileName = "gameConfig.json";

    @FXML
    private Label label;

    @FXML
    private Game game;

    @FXML
    private Label playerPositionProperty;
    @FXML
    private ImageView imageView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");



        //loadGame();
        game.createRooms();

       //examplePlayAnimation();

        bindProperties();

    }


    private void loadGame(){
        game = jsonToGame(readConfigFile(configFileName));
        game.reconfigureRooms();
    }

    private void examplePlayAnimation(){

        Image playerSpriteSheet = getImages("sprites", getClass()).get("sprites/player/walkCycle.png");
        List<Image[]> spriteAnimations = cutSprites(playerSpriteSheet, 64);

        List<Object> animationKeys = new ArrayList<>();
        animationKeys.add(Direction.NORTH);
        animationKeys.add(Direction.EAST);
        animationKeys.add(Direction.SOUTH);
        animationKeys.add(Direction.WEST);

        game.getPlayer().addAnimation(animationKeys, spriteAnimations);
        game.getPlayer().setImageView(imageView);
        game.getPlayer().setAnimationCycleLengthMillis(650);



        //game.getPlayer().displayStill();
        game.getPlayer().playAnimation(Direction.EAST);
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