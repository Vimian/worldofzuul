package sdu.student;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import sdu.student.editor.model.GameModel;
import sdu.student.editor.model.InventoryModel;
import worldofzuul.Game;
import worldofzuul.item.GrowthStage;
import worldofzuul.item.Item;
import worldofzuul.util.Vector;
import worldofzuul.world.Direction;
import worldofzuul.world.Field;
import worldofzuul.world.Room;

import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import static worldofzuul.util.Data.*;
import static worldofzuul.util.Drawing.*;
import static worldofzuul.util.Math.vectorDifference;
import static worldofzuul.util.Math.vectorDirection;

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
    private GameModel model;
    private ScheduledExecutorService scheduledThreadPool;
    private Game game = new Game();

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
        scheduledThreadPool.scheduleAtFixedRate(() -> model.getGame().update(), 0, delay, TimeUnit.NANOSECONDS);
    }

    private void loadGame() {
        Game game = jsonToGame(readConfigFile(configFileName));
        if(game != null){
            model = new GameModel(game);
            model.getGame().reconfigureRooms();
        } else {
            System.out.println("Error loading game config");
            model = new GameModel(new Game());
            model.getGame().createRooms();
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

        model.getGame().getPlayer().addAnimation(animationKeys, spriteAnimations);
        model.getGame().getPlayer().setImageView(imageView);
        model.getGame().getPlayer().setAnimationCycleLengthMillis(400);

        model.getGame().getPlayer().display();

        //Example add sprites to fields
        Arrays.stream(model.getGame().getRoom().getRoomGrid()).forEach(t -> Arrays.stream(t).forEach(gameObject -> {
            if(gameObject instanceof Field){

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
        setPaneTranslation(model.getGame().getPlayer().getPos());
        subscribeToPlayerMovement();

    }

    private void subscribeToPlayerMovement() {
        //Upon player position change, move pane and activate player walk animation
        model.getGame().getPlayer().getPos().vectorValueProperty().addListener((observable, oldValue, newValue) -> {
            Vector pos = new Vector(oldValue);
            Vector pos2 = new Vector(newValue);
            repositionPlayer(pos, pos2);

        });
    }

    private void repositionPlayer(Vector pos, Vector pos2) {
        setPaneTranslation(pos);
        if (vectorDifference(pos, pos2) > 1) {
            drawRoom();
            model.getGame().getPlayer().stopAnimation();
            paneTranslation.stop();
            setPaneTranslation(pos2);
            return;
        }
        repositionPlayer(vectorDirection(pos, pos2));
    }

    private void repositionPlayer(Direction direction) {
        model.getGame().getPlayer().playAnimation(1, direction);

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
                (int) (model.getGame().getPlayer().getAnimationCycleLengthMillis() * paneTransDelayCoefficient));

        paneTranslation.setDelay(Duration.millis(paneTransDelayCoefficient * model.getGame().getPlayer().getAnimationCycleLengthMillis()));

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
        if (model.getGame().getRoom().getBackgroundImage() != null && loadedImages.containsKey(model.getGame().getRoom().getBackgroundImage())) {
            setBackground(model.getGame().getRoom());
        } else {
            setBackground(loadedImages.get("sprites/room/test.png"));
        }
        drawGrid(roomPane, getBackgroundRowCount());
        drawGameObjects(model.getGame().getRoom(), loadedImages, roomPane, getBackgroundTileDim());
    }

    private void bindProperties() {
        playerPositionProperty.textProperty();

        playerItems.itemsProperty().bindBidirectional(new InventoryModel(model.getGame().getPlayer().getInventory()).itemsProperty());


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
            model.getGame().move(Direction.NORTH);
        }
    }

    public void moveSouth(ActionEvent actionEvent) {
        if (!isPlayerMoving()) {
            model.getGame().move(Direction.SOUTH);
        }
    }

    public void moveEast(ActionEvent actionEvent) {
        if (!isPlayerMoving()) {
            model.getGame().move(Direction.EAST);
        }
    }

    public void moveWest(ActionEvent actionEvent) {
        if (!isPlayerMoving()) {
            model.getGame().move(Direction.WEST);
        }
    }

    public void interact(ActionEvent actionEvent) {
        model.getGame().interact();
    }


    private boolean isPlayerMoving() {

        if (model.getGame().getPlayer().getAnimationTimeline() != null) {
            return model.getGame().getPlayer().isAnimationActive();
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
            model.getPlayerModel().getInventoryModel().setSelectedItem((Item) clickedElement);
        }

    }

    private double getBackgroundTileDim() {
        return roomPane.getMinWidth() / getBackgroundRowCount();
    }

    private double getBackgroundRowCount() {
        return (roomPane.getMinWidth() / backgroundScaling) / gameTileDim;
    }

}