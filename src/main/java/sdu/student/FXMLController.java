package sdu.student;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import sdu.student.editor.BlockEditor;
import sdu.student.editor.DoorEditor;
import sdu.student.editor.FieldEditor;
import worldofzuul.Game;
import worldofzuul.item.GrowthStage;
import worldofzuul.item.Item;
import worldofzuul.util.Vector;
import worldofzuul.world.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static worldofzuul.util.Data.*;
import static worldofzuul.util.Drawing.*;
import static worldofzuul.util.Math.*;

public class FXMLController implements Initializable {
    private static final String configFileName = "gameConfig.json";
    private static final String spriteDirectory = "sprites";
    private static final int gameTileDim = 16;
    private static final int backgroundScaling = 6;
    private static final double paneTransDelayCoefficient = 1.2;
    private static final int updateDelay = 60;
    @FXML
    private ListView playerItems;
    @FXML
    private Pane roomPane;
    @FXML
    private Label label;
    @FXML
    private Label playerPositionProperty;
    @FXML
    private ImageView imageView;

    private TranslateTransition paneTranslation;
    private HashMap<String, Image> loadedImages;
    private Game model;
    private ScheduledExecutorService scheduledThreadPool;
    private Vector selectedGamePosition;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");

        loadedImages = getImages(spriteDirectory, getClass());
        loadGame();



        bindProperties();
        examplePlayAnimation();

        enableGameUpdater();

    }

    private void enableGameUpdater() {

        scheduledThreadPool = Executors.newScheduledThreadPool(1, r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });

        long delay = ((long) 1e9)/ updateDelay;
        scheduledThreadPool.scheduleAtFixedRate(() -> model.update(), 0, delay, TimeUnit.NANOSECONDS);
    }

    private void loadGame() {

        var gamee = new Game();
        gamee.createRooms();

        var aa = gameToJson(gamee);

        model = jsonToGame(readConfigFile(configFileName));
        if (model != null) {
            model.reconfigureRooms();
        } else {
            System.out.println("Error loading game config");
            model = new Game();
            model.createRooms();
        }
    }

    private void examplePlayAnimation() {

        Image playerSpriteSheet = loadedImages.get("sprites/player/walkCycle.png");
        List<Image[]> spriteAnimations = cutSprites(playerSpriteSheet, 64);

        List<Object> animationKeys = new ArrayList<>();
        animationKeys.add(Direction.NORTH);
        animationKeys.add(Direction.EAST);
        animationKeys.add(Direction.SOUTH);
        animationKeys.add(Direction.WEST);

        model.getPlayer().addAnimation(animationKeys, spriteAnimations);
        model.getPlayer().setImageView(imageView);
        model.getPlayer().setAnimationCycleLengthMillis(400);

        model.getPlayer().display();

        //Example add sprites to fields
        Arrays.stream(model.getRoom().getRoomGrid()).forEach(t -> Arrays.stream(t).forEach(gameObject -> {
            if (gameObject instanceof Field) {

                List<Image[]> imgs = new ArrayList<>();
                imgs.add(new Image[]{loadedImages.get("sprites/asteriskAnim/asterisk_circle0000.png")});
                imgs.add(new Image[]{loadedImages.get("sprites/asteriskAnim/asterisk_circle0003.png")});
                imgs.add(new Image[]{loadedImages.get("sprites/asteriskAnim/asterisk_circle0008.png")});

                List<Object> keys = new ArrayList<>();
                keys.add(GrowthStage.SEED);
                keys.add(GrowthStage.ADULT);
                keys.add(GrowthStage.RIPE);

                gameObject.addAnimation(keys, imgs);
                gameObject.setAnimationCycleLengthMillis(400);

            }
        }));


        drawRoom();
        setPaneTranslation(model.getPlayer().getPos());
        subscribeToPlayerMovement();

    }

    private void subscribeToPlayerMovement() {
        //Upon player position change, move pane and activate player walk animation
        model.getPlayer().getPos().vectorValueProperty().addListener((observable, oldValue, newValue) -> {
            repositionPlayer(new Vector(oldValue), new Vector(newValue));
        });
    }

    private void repositionPlayer(Vector pos, Vector pos2) {
        setPaneTranslation(pos);
        if (vectorDifference(pos, pos2) > 1) {
            drawRoom();
            model.getPlayer().stopAnimation();
            paneTranslation.stop();
            setPaneTranslation(pos2);
            return;
        }
        repositionPlayer(vectorDirection(pos, pos2));
    }

    private void repositionPlayer(Direction direction) {
        model.getPlayer().playAnimation(1, direction);

        double transX = getBackgroundTileDim();
        double transY = getBackgroundTileDim();

        switch (direction) {
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
                (int) (model.getPlayer().getAnimationCycleLengthMillis() * paneTransDelayCoefficient));

        paneTranslation.setDelay(Duration.millis(paneTransDelayCoefficient * model.getPlayer().getAnimationCycleLengthMillis()));

        paneTranslation.play();
    }

    private void setPaneTranslation(Vector pos) {
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
        if (model.getRoom().getBackgroundImage() != null && loadedImages.containsKey(model.getRoom().getBackgroundImage())) {
            setBackground(model.getRoom());
        } else {
            setBackground(loadedImages.get("sprites/room/test.png"));
        }
        drawGrid(roomPane, getBackgroundRowCount());
        drawGameObjects(model.getRoom(), loadedImages, roomPane, getBackgroundTileDim(), getClass());
    }

    private void bindProperties() {
        playerPositionProperty.textProperty().bindBidirectional(model.getPlayer().getPos().vectorValueProperty());

        playerItems.itemsProperty().bindBidirectional(model.getPlayer().getInventory().itemsProperty());


    }

    private void setBackground(Room room) {
        setBackground(loadedImages.get(room.getBackgroundImage()));
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
            model.move(Direction.NORTH);
        }
    }

    public void moveSouth(ActionEvent actionEvent) {
        if (!isPlayerMoving()) {
            model.move(Direction.SOUTH);
        }
    }

    public void moveEast(ActionEvent actionEvent) {
        if (!isPlayerMoving()) {
            model.move(Direction.EAST);
        }
    }

    public void moveWest(ActionEvent actionEvent) {
        if (!isPlayerMoving()) {
            model.move(Direction.WEST);
        }
    }

    public void interact(ActionEvent actionEvent) {
        model.interact();
    }


    private boolean isPlayerMoving() {

        if (model.getPlayer().getAnimationTimeline() != null) {
            return model.getPlayer().isAnimationActive();
        }

        return false;
        /*

        //TODO: Solve pane translation flicker caused by animation cancelling
        if (paneTranslation != null) {
            if (paneTranslation.getStatus().equals(Animation.Status.RUNNING)) {
                return true;
            }
        }


        return false;*/
    }

    public void playerItemsClicked(MouseEvent mouseEvent) {

        Object clickedElement = playerItems.getSelectionModel().getSelectedItem();
        if(clickedElement instanceof Item){
            model.getPlayer().getInventory().setSelectedItem((Item) clickedElement);
        }

    }

    private double getBackgroundTileDim() {
        return roomPane.getMinWidth() / getBackgroundRowCount();
    }

    private double getBackgroundRowCount() {
        return (roomPane.getMinWidth() / backgroundScaling) / gameTileDim;
    }

    public void roomPaneClicked(MouseEvent mouseEvent) {

        if (mouseEvent.getButton() == MouseButton.SECONDARY){

            selectedGamePosition = positionClickedOnPane(getBackgroundTileDim(), getBackgroundTileDim(), mouseEvent.getX(), mouseEvent.getY());
            if (selectedGamePosition.getX() < 0 || selectedGamePosition.getY() < 0 || selectedGamePosition.getX() > getBackgroundRowCount() || selectedGamePosition.getY() > getBackgroundRowCount()) {
                return;
            }

            try {
                rightClickGameObject(model.getRoom().getGridGameObject(selectedGamePosition));
            } catch (ArrayIndexOutOfBoundsException e) { // Handle exceptions caused by non-matching RoomGrid sizes or invalid positions
                System.out.println(e.getMessage());
            }
        }
    }


    private void rightClickGameObject(GameObject object){

        if(object instanceof Field && selectedGamePosition != null){

            //TODO: Implement on click functionality

        } else {

        }



    }




}