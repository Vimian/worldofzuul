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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import worldofzuul.Game;
import worldofzuul.world.Direction;
import worldofzuul.world.Field;
import worldofzuul.world.Room;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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
    public StackPane mainPane;
    public VBox boxName;
    public StackPane stackPane;

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
    @FXML
    private ListView inventoryView;

    @FXML
    private Game game;

    public Button marketButton;

    @FXML
    private Label playerPositionProperty;

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


        playerPositionProperty.textProperty()
                .bindBidirectional(game.getPlayer().getPos().vectorValueProperty());


        long delay = ((long) 1e9) / updateDelay;
        scheduledThreadPool.scheduleAtFixedRate(() -> model.update(), 0, delay, TimeUnit.NANOSECONDS);

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
        model.interact();
    }

    public void openMarket(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();

        //Defines our controller
        loader.setControllerFactory(aClass -> new sdu.student.SubScene(model));
        //Defines the FXML file
        loader.setLocation(getClass().getResource("subScene.fxml"));

        try {
            stackPane.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        try {
            Node subRoot = FXMLLoader.load(getClass().getResource("subScene.fxml"));
            loader.setControllerFactory(aClass -> new FieldInfoBarController(field));
            stackPane.getChildren().add(subRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */



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
        if (clickedElement instanceof Item) {
            model.getPlayer().getInventory().setSelectedItem((Item) clickedElement);
        }

    }

    private double getBackgroundTileDim() {
        return roomPane.getMinWidth() / getBackgroundRowCount();
        game.interact(); //TODO: Why is this here?
    }
}