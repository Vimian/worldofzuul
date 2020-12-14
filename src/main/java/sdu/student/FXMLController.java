package sdu.student;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;
import worldofzuul.Game;
import worldofzuul.item.Item;
import worldofzuul.util.CustomPrintStream;
import worldofzuul.util.Vector;
import worldofzuul.world.Direction;
import worldofzuul.world.Environment;
import worldofzuul.world.GameObject;
import worldofzuul.world.Room;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static sdu.student.MenuController.loadGameScene;
import static worldofzuul.util.Data.*;
import static worldofzuul.util.Drawing.*;
import static worldofzuul.util.Math.*;

/**
 * The type Fxml controller.
 *
 * Controller for the primary Game scene.
 *
 */
public class FXMLController implements Initializable {
    /**
     * The constant name of the game configuration json-file.
     */
    private static final String configFileName = "gameConfig.json";
    /**
     * The constant for the spite directory.
     */
    private static final String spriteDirectory = "sprites";
    /**
     * The constant paneTransDelayCoefficient determining the speed of moving {@link FXMLController#roomPane}.
     */
    private static final double paneTransDelayCoefficient = 1.2;
    /**
     * The constant timers per second at which to update {@link FXMLController#model}.
     */
    private static final int updateDelay = 60;
    /**
     * The constant nightChangeOpacity which determines the darkness of {@link FXMLController#nightLayerPane}.
     */
    private static final double nightChangeOpacity = 0.6;
    /**
     * The constant delay at which to switch the opacity of {@link FXMLController#nightLayerPane}.
     */
    private static final int nightChangeFadeDelay = 6000;
    /**
     * The constant delay at which text notifications in {@link FXMLController#textDisplayBox} becomes deleted.
     */
    private static final int textDisplayDeletionDelay = 8000;
    /**
     * The constant delay at which text notifications in {@link FXMLController#textDisplayBox} becomes hidden.
     */
    private static final int textDisplayFadeDelay = 1500;
    /**
     * The constant maximal distance of which the player can interact with a GameObject.
     */
    private static final int playerInteractDistance = 1;
    /**
     * The Print stream.
     */
    private final CustomPrintStream printStream = new CustomPrintStream(System.out);
    /**
     * The Stage.
     */
    private final Stage stage;
    /**
     * The Pane containing display of the game and entities.
     */
    public StackPane gameContainerPane;
    /**
     * The Box displaying prints from {@link FXMLController#printStream}.
     */
    public VBox textDisplayBox;
    /**
     * The Root node.
     */
    public StackPane mainPane;
    /**
     * The Environment layer pane.
     */
    public Pane environmentLayerPane;
    /**
     * The Night layer pane.
     */
    public Pane nightLayerPane;
    /**
     * The Rain image pane.
     */
    public Pane rainImagePane;
    /**
     * The Inventory table view.
     */
    public TableView<Item> inventoryTableView;
    /**
     * The Currently selected item label.
     */
    public Label currentlySelectedItemLabel;
    /**
     * The Player balance label.
     */
    public Label playerBalanceLabel;
    /**
     * The Time label.
     */
    public Label timeLabel;
    /**
     * The Market button.
     */
    public Button marketButton;
    /**
     * The instance of Game.
     */
    public Game model;
    /**
     * The background tile dimension.
     */
    private int gameTileDim = 64;
    /**
     * The Background scaling.
     */
    private int backgroundScaling = 2;
    /**
     * The Room pane.
     */
    @FXML
    private Pane roomPane;
    /**
     * The Image view displaying the Player animations.
     */
    @FXML
    private ImageView imageView;
    /**
     * The {@link FXMLController#roomPane} translation.
     */
    private TranslateTransition paneTranslation;
    /**
     * All loaded images from {@link FXMLController#spriteDirectory}.
     */
    private HashMap<String, Image> loadedImages;
    /**
     * The Selected game position.
     */
    private Vector selectedGamePosition;
    /**
     * The Scheduled thread pool containing the {@link FXMLController#model} update thread.
     */
    private ScheduledExecutorService scheduledThreadPool;

    /**
     * Instantiates a new Fxml controller.
     *
     * @param stage the stage
     */
    public FXMLController(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loadedImages = getImages(spriteDirectory, getClass());

        loadGame();

        bindProperties();
        configureAnimations();
        enableGameUpdater();


        gameTileDim = model.getRoom().getRoomTileDim();
        backgroundScaling = model.getRoom().getRoomBGScale();

        System.setOut(printStream);
        printStream.printListProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> displayTextMessage(newValue.get(newValue.size() - 1))));
    }


    /**
     * Reload scene.
     */
    public void reloadScene() {
        scheduledThreadPool.shutdown();
        loadGameScene(stage, getClass(), this);
    }


    /**
     * Enable game updater.
     *
     * Creates thread to update {@link FXMLController#model} at the interval set by {@link FXMLController#updateDelay}.
     */
    private void enableGameUpdater() {

        scheduledThreadPool = Executors.newScheduledThreadPool(1, r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });

        long delay = ((long) 1e9) / updateDelay;
        scheduledThreadPool.scheduleAtFixedRate(() -> model.update(), 0, delay, TimeUnit.NANOSECONDS);
    }


    /**
     * Loads game from JSON-file specified by {@link FXMLController#configFileName} and replaces {@link FXMLController#model}.
     */
    private void loadGame() {

        var game = new Game();
        game.createRooms();

        model = jsonToGame(readConfigFile(configFileName));
        if (model != null) {
            model.reconfigureRooms();
        } else {
            System.out.println("Error loading game config");
            model = new Game();
            model.createRooms();
        }

        model.getRoom().setPrintingEnabled(true);
    }

    /**
     * Configure animations.
     *
     * Configures animations for GameObjects and Items in {@link FXMLController#model}.
     */
    private void configureAnimations() {

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


        for (Room room : model.getRooms()) {
            if (room != null && room.getRoomGrid() != null) {
                for (GameObject[] t : room.getRoomGrid()) {
                    for (GameObject gameObject : t) {
                        gameObject.configureImages(loadedImages);
                    }
                }

            }
        }

        model.getPlayer().getInventory().getItems().forEach(item -> item.configureImages(loadedImages));
        model.getMarket().getStock().forEach(item -> item.configureImages(loadedImages));


        drawRoom();
        setPaneTranslation(model.getPlayer().getPos());
        subscribeToPlayerMovement();

    }

    /**
     * Subscribe to {@link FXMLController#model} player movement.
     */
    private void subscribeToPlayerMovement() {
        model.getPlayer().getPos().vectorValueProperty().addListener((observable, oldValue, newValue) -> repositionPlayer(new Vector(oldValue), new Vector(newValue)));
    }

    /**
     * Reposition player.
     *
     * Moves {@link FXMLController#roomPane} in relation to the new player position, giving the illusion of movement.
     *
     * @param pos  the pos
     * @param pos2 the pos 2
     */
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

    /**
     * Reposition player.
     *
     * Plays walk animation of player {@link FXMLController#imageView} in param direction.
     *
     * @param direction the direction
     */
    private void repositionPlayer(Direction direction) {
        model.getPlayer().playAnimation(1, direction);

        double transX = getBackgroundTileDim();
        double transY = getBackgroundTileDim();

        switch (direction) {
            case SOUTH -> {
                transY *= -1;
                transX = 0;
            }
            case NORTH -> transX = 0;
            case WEST -> {
                transY = 0;
                transX *= -1;
            }
            case EAST -> transY = 0;
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

    /**
     * Sets {@link FXMLController#roomPane} translation.
     *
     * @param pos the pos
     */
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

    /**
     * Draw GameObjects or other images relevant to {@link FXMLController#roomPane}.
     */
    private void drawRoom() {
        roomPane.getChildren().clear();
        if (model.getRoom().getBackgroundImage() != null && loadedImages.containsKey(model.getRoom().getBackgroundImage())) {
            setBackground(model.getRoom());
        } else {
            setBackground(loadedImages.get("sprites/room/test.png"));
        }
        drawGrid(roomPane, getBackgroundRowCount());
        drawGameObjects(model.getRoom(), loadedImages, roomPane, getBackgroundTileDim(), getClass(), selectedGamePosition, false);
    }

    /**
     * Bind {@link FXMLController#model} properties to JavaFX controls and add listeners.
     */
    private void bindProperties() {

        inventoryTableView.itemsProperty().bindBidirectional(model.getPlayer().getInventory().itemsProperty());
        subscribeToEnvironmentChanges(model.getRoom().getEnvironment());
        currentlySelectedItemLabel.textProperty().bindBidirectional(model.getPlayer().getInventory().selectedItemNameProperty());

        playerBalanceLabel.textProperty().bindBidirectional(model.getPlayer().balanceProperty(), new NumberStringConverter());

        model.roomProperty().addListener((observable, oldValue, newValue) -> {
            oldValue.setPrintingEnabled(false);

            unsubscribeToEnvironmentChanges(oldValue.getEnvironment());
            subscribeToEnvironmentChanges(newValue.getEnvironment());

            gameTileDim = newValue.getRoomTileDim();
            backgroundScaling = newValue.getRoomBGScale();
            newValue.setPrintingEnabled(true);

        });


    }

    /**
     * Subscribe to room environment changes.
     *
     * Adds listeners to call {@link FXMLController#changeNightStage(boolean)} and {@link FXMLController#changeRainState(boolean)} upon update of param environment's property {@link Environment#nightStateProperty()} and {@link Environment#rainStateProperty()} respectively.
     *
     * @param environment the environment
     */
    private void subscribeToEnvironmentChanges(Environment environment) {
        environment.rainStateProperty().addListener((observable1, oldValue1, newValue1) -> changeRainState(newValue1));
        environment.nightStateProperty().addListener((observable1, oldValue1, newValue1) -> changeNightStage(newValue1));
    }

    /**
     * Unsubscribe to environment changes.
     *
     * Removes listeners added by {@link FXMLController#subscribeToEnvironmentChanges(Environment)}.
     *
     * @param environment the environment
     */
    private void unsubscribeToEnvironmentChanges(Environment environment) {
        environment.rainStateProperty().removeListener((observable1, oldValue1, newValue1) -> changeRainState(newValue1));
        environment.nightStateProperty().removeListener((observable1, oldValue1, newValue1) -> changeNightStage(newValue1));
    }

    /**
     * Change rain state.
     *
     * Displays or hides rain animation contained in {@link FXMLController#rainImagePane}.
     *
     * @param isRaining the is raining
     */
    private void changeRainState(boolean isRaining) {
        FadeTransition ft = new FadeTransition(Duration.millis(nightChangeFadeDelay), rainImagePane);
        if (isRaining) {
            ft.setFromValue(0.0);
            ft.setToValue(1);

        } else {
            ft.setFromValue(1);
            ft.setToValue(0);
        }
        ft.play();

    }

    /**
     * Change night stage.
     *
     * Displays or hides {@link FXMLController#nightLayerPane}.
     *
     * @param isNight the is night
     */
    private void changeNightStage(boolean isNight) {
        FadeTransition ft = new FadeTransition(Duration.millis(nightChangeFadeDelay), nightLayerPane);
        if (isNight) {
            ft.setFromValue(0.0);
            ft.setToValue(nightChangeOpacity);

        } else {
            ft.setFromValue(nightChangeOpacity);
            ft.setToValue(0);
        }
        ft.play();
    }

    /**
     * Sets background of {@link FXMLController#roomPane}.
     *
     * @param room the room
     */
    private void setBackground(Room room) {
        setBackground(loadedImages.get(room.getBackgroundImage()));
    }

    /**
     * Sets background of {@link FXMLController#roomPane}.
     *
     * @param backgroundImage the background image
     */
    private void setBackground(Image backgroundImage) {
        if (backgroundImage == null) {
            return;
        }

        BackgroundImage myBI = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(0, 0, false, false, false, true));

        roomPane.setMinSize(myBI.getImage().getWidth() * backgroundScaling, myBI.getImage().getHeight() * backgroundScaling);
        roomPane.setBackground(new Background(myBI));
    }

    /**
     * Calls {@link Game#move(Direction)} in {@link FXMLController#model} with the north direction.
     */
    public void moveNorth() {
        if (isPlayerNotMoving()) {
            model.move(Direction.NORTH);
        }
    }

    /**
     * Calls {@link Game#move(Direction)} in {@link FXMLController#model} with the south direction.
     */
    public void moveSouth() {
        if (isPlayerNotMoving()) {
            model.move(Direction.SOUTH);
        }
    }

    /**
     * Calls {@link Game#move(Direction)} in {@link FXMLController#model} with the east direction.
     */
    public void moveEast() {
        if (isPlayerNotMoving()) {
            model.move(Direction.EAST);
        }
    }

    /**
     * Calls {@link Game#move(Direction)} in {@link FXMLController#model} with the west direction.
     */
    public void moveWest() {
        if (isPlayerNotMoving()) {
            model.move(Direction.WEST);
        }
    }

    /**
     * Open market.
     *
     * Adds a new {@link SubScene} to {@link FXMLController#gameContainerPane}.
     */
    public void openMarket() {

        FXMLLoader loader = new FXMLLoader();

        loader.setControllerFactory(aClass -> new sdu.student.SubScene(model));
        loader.setLocation(getClass().getResource("subScene.fxml"));

        try {
            gameContainerPane.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Is player movement animation not active.
     *
     * @return the boolean
     */
    private boolean isPlayerNotMoving() {
        if (model.getPlayer().getAnimationTimeline() != null) {
            return !model.getPlayer().isAnimationActive();
        }
        return true;
    }

    /**
     * Gets background tile dim {@link FXMLController#roomPane} when dividing using {@link FXMLController#gameTileDim} and {@link FXMLController#backgroundScaling}.
     *
     * @return the background tile dim
     */
    private double getBackgroundTileDim() {
        return roomPane.getMinWidth() / getBackgroundRowCount();
    }

    /**
     * Gets background row count of {@link FXMLController#roomPane} when dividing using {@link FXMLController#gameTileDim} and {@link FXMLController#backgroundScaling}.
     *
     * @return the background row count
     */
    private double getBackgroundRowCount() {
        return (roomPane.getMinWidth() / backgroundScaling) / gameTileDim;
    }

    /**
     * Add text message to {@link FXMLController#textDisplayBox}.
     *
     * @param text the text
     */
    private void displayTextMessage(String text) {

        Label newLabel = new Label(text);
        newLabel.setId("textDisplayLabel");
        newLabel.setPickOnBounds(false);

        DropShadow effect = new DropShadow(0.67, Color.WHITE);
        effect.setInput(new Glow(0.65));
        newLabel.setEffect(effect);
        newLabel.setWrapText(true);


        textDisplayBox.getChildren().add(newLabel);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {

                    FadeTransition ft = new FadeTransition(Duration.millis(textDisplayFadeDelay), newLabel);
                    ft.setFromValue(1.0);
                    ft.setToValue(0.0);
                    ft.setOnFinished(event -> textDisplayBox.getChildren().remove(newLabel));

                    ft.play();
                });
                this.cancel();
            }
        }, FXMLController.textDisplayDeletionDelay);


    }

    /**
     * {@link FXMLController#roomPane} clicked.
     *
     * If {@link MouseEvent#getButton()} is {@link javafx.scene.input.MouseButton#PRIMARY} button is clicked interact with GameObject without an Item if target GameObject is within {@link FXMLController#playerInteractDistance}.
     *
     * If {@link MouseEvent#getButton()} is {@link javafx.scene.input.MouseButton#SECONDARY} button is clicked interact with GameObject with the players selected Item if target GameObject is within {@link FXMLController#playerInteractDistance}
     *
     * @param mouseEvent the mouse event
     */
    public void roomPaneClicked(MouseEvent mouseEvent) {

        selectedGamePosition = positionClickedOnPane(getBackgroundTileDim(), getBackgroundTileDim(), mouseEvent.getX(), mouseEvent.getY());
        if (selectedGamePosition.getX() < 0 || selectedGamePosition.getY() < 0 || selectedGamePosition.getX() > getBackgroundRowCount() || selectedGamePosition.getY() > getBackgroundRowCount()) {
            return;
        }

        if (vectorDifference(model.getPlayer().getPos(), selectedGamePosition) <= playerInteractDistance) {
            switch (mouseEvent.getButton()) {
                case PRIMARY -> {
                    model.interact(selectedGamePosition, false);
                    faceDirection(vectorDirection(model.getPlayer().getPos(), selectedGamePosition));
                }
                case SECONDARY -> {
                    model.interact(selectedGamePosition, true);
                    faceDirection(vectorDirection(model.getPlayer().getPos(), selectedGamePosition));
                }
            }
        }

    }

    /**
     * Face direction.
     *
     * Display image in {@link FXMLController#imageView} corresponding to correct direction using images defined in {@link FXMLController#model}'s player.
     *
     * @param vectorDirection the vector direction
     */
    private void faceDirection(Direction vectorDirection) {
        model.getPlayer().setDefaultImage(vectorDirection);
    }

    /**
     * Set {@link FXMLController#model}'s player item to be the item selected in {@link FXMLController#inventoryTableView}.
     */
    public void selectItem() {
        model.getPlayer().getInventory().setSelectedItem(inventoryTableView.getSelectionModel().getSelectedItem());
    }

}