package sdu.student;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import worldofzuul.Game;
import worldofzuul.util.Vector;
import worldofzuul.world.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import static worldofzuul.util.Data.*;
import static worldofzuul.util.Drawing.drawGrid;
import static worldofzuul.util.Drawing.translate;
import static worldofzuul.util.Math.vectorDifference;
import static worldofzuul.util.Math.vectorDirection;

public class FXMLController implements Initializable {
    private static final String configFileName = "gameConfig.json";
    private static final int gameTileDim = 32;
    private static final int backgroundScaling = 3;
    private static final double paneTransDelayCoefficient = 1.2;

    @FXML
    private Pane roomPane;

    @FXML
    private Label label;

    @FXML
    private Game game;

    @FXML
    private Label playerPositionProperty;
    @FXML
    private ImageView imageView;
    private TranslateTransition paneTranslation;

    private HashMap<String, Image> loadedImages;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");

        loadedImages = getImages("sprites", getClass());

        //loadGame();
        game.createRooms();

        bindProperties();

        //examplePlayAnimation();


    }


    private void loadGame() {
        game = jsonToGame(readConfigFile(configFileName));
        game.reconfigureRooms();
    }

    private void examplePlayAnimation() {

        Image playerSpriteSheet = loadedImages.get("sprites/player/walkCycle.png");
        List<Image[]> spriteAnimations = cutSprites(playerSpriteSheet, 64);

        List<Object> animationKeys = new ArrayList<>();
        animationKeys.add(Direction.NORTH);
        animationKeys.add(Direction.EAST);
        animationKeys.add(Direction.SOUTH);
        animationKeys.add(Direction.WEST);

        game.getPlayer().addAnimation(animationKeys, spriteAnimations);
        game.getPlayer().setImageView(imageView);
        game.getPlayer().setAnimationCycleLengthMillis(400);

        game.getPlayer().display();


        drawRoom();
        setPaneTranslation(game.getPlayer().getPos());
        subscribeToPlayerMovement();

    }

    private void subscribeToPlayerMovement() {
        //Upon player position change, move pane and activate player walk animation
        game.getPlayer().getPos().vectorValueProperty().addListener((observable, oldValue, newValue) -> {
            Vector pos = new Vector(oldValue);
            Vector pos2 = new Vector(newValue);
            repositionPlayer(pos, pos2);

        });
    }

    private void repositionPlayer(Vector pos, Vector pos2) {
        setPaneTranslation(pos);
        if(vectorDifference(pos, pos2) > 1){
            drawRoom();
            game.getPlayer().stopAnimation();
            paneTranslation.stop();
            setPaneTranslation(pos2);
            return;
        }
        repositionPlayer(vectorDirection(pos, pos2));
    }

    private void repositionPlayer(Direction direction) {
        game.getPlayer().playAnimation(1, direction);

        double transX = getBackgroundTileDim();
        double transY = getBackgroundTileDim();

        switch(direction){
            case SOUTH -> {
                transY *= -1;
                transX = 0;
            }
            case NORTH -> {
                transX = 0;
            }
            case WEST -> {
                transY = 0;
                transX *= -1;
            }
            case EAST -> {
                transY = 0;
            }
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }

        paneTranslation = translate(roomPane,
                transX,
                transY,
                0,
                (int) (game.getPlayer().getAnimationCycleLengthMillis() * paneTransDelayCoefficient));

        paneTranslation.setDelay(Duration.millis(paneTransDelayCoefficient * game.getPlayer().getAnimationCycleLengthMillis()));

        paneTranslation.play();
    }


    private void setPaneTranslation(Vector pos){
        double cubeDim = (getBackgroundTileDim());


        double transX = cubeDim * pos.getX();
        double transY = cubeDim * pos.getY();

        transX += cubeDim;
        transY += cubeDim;


        double roomCenterX = roomPane.getMinWidth() / 2;
        double roomCenterY = roomPane.getMinHeight() / 2;


        roomPane.setTranslateX(roomCenterX - transX + cubeDim / 2);
        roomPane.setTranslateY(roomCenterY - transY + cubeDim);

    }

    private void drawRoom() {
        roomPane.getChildren().clear();
        setBackground(loadedImages.get("sprites/room/test.png"));

        drawGrid(roomPane, getBackgroundRowCount());

        for (int i = 0; i < game.getRoom().getRoomGrid().length; i++) {
            for (int j = 0; j < game.getRoom().getRoomGrid().length; j++) {
                var rect = new Rectangle(j * getBackgroundTileDim(), i * getBackgroundTileDim(), getBackgroundTileDim(), getBackgroundTileDim());

                GameObject object = game.getRoom().getGridGameObject(new Vector(j, i));
                //Replace with method to get images / add imageviews instead
                if (object instanceof Block) {
                    if (object.colliding) {
                        rect.setStroke(Color.RED);
                    } else {
                        continue;
                    }
                } else if (object instanceof Door) {
                    rect.setStroke(Color.BLUE);
                } else if (object instanceof Field) {
                    rect.setStroke(Color.GREEN);
                } else {
                    continue;
                }
                rect.setStrokeWidth(4);
                rect.setFill(Color.TRANSPARENT);
                roomPane.getChildren().add(rect);
            }
        }


    }


    private void bindProperties() {
        playerPositionProperty.textProperty()
                .bindBidirectional(game.getPlayer().getPos().vectorValueProperty());


    }

    private void setBackground(Room room){
        Image backgroundImage = loadedImages.get(room.getBackgroundImage());
        setBackground(backgroundImage);

    }

    private void setBackground(Image backgroundImage) {
        BackgroundImage myBI = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(0, 0, false, false, false, true));

        roomPane.setMinSize(myBI.getImage().getWidth() * backgroundScaling, myBI.getImage().getHeight() * backgroundScaling);
        roomPane.setBackground(new Background(myBI));
    }


    public void moveNorth(ActionEvent actionEvent) {
        if (!isPlayerMoving()) {
            game.move(Direction.NORTH);
        }
    }

    public void moveSouth(ActionEvent actionEvent) {
        if (!isPlayerMoving()) {
            game.move(Direction.SOUTH);
        }
    }

    public void moveEast(ActionEvent actionEvent) {
        if (!isPlayerMoving()) {
            game.move(Direction.EAST);
        }
    }

    public void moveWest(ActionEvent actionEvent) {
        if (!isPlayerMoving()) {
            game.move(Direction.WEST);
        }
    }

    public void interact(ActionEvent actionEvent) {
        game.interact();
    }


    private boolean isPlayerMoving() {

        if (game.getPlayer().getAnimationTimeline() != null) {
            return game.getPlayer().isAnimationActive();
        }

        return false;
        /*

        if (paneTranslation != null) {
            if (paneTranslation.getStatus().equals(Animation.Status.RUNNING)) {
                return true;
            }
        }


        return false;*/
    }

    private double getBackgroundTileDim() {
        return roomPane.getMinWidth() / getBackgroundRowCount();
    }
    private double getBackgroundRowCount() {
        return (roomPane.getMinWidth() / backgroundScaling) / gameTileDim;
    }

}