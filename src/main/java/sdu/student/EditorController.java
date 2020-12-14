package sdu.student;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import sdu.student.editor.BlockEditor;
import sdu.student.editor.DoorEditor;
import sdu.student.editor.FieldEditor;
import sdu.student.editor.NPCEditor;
import worldofzuul.Game;
import worldofzuul.item.pHNeutralizers;
import worldofzuul.util.Vector;
import worldofzuul.world.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static worldofzuul.util.Data.*;
import static worldofzuul.util.Drawing.drawGameObjects;
import static worldofzuul.util.Drawing.drawGrid;
import static worldofzuul.util.Math.positionClickedOnPane;
import static worldofzuul.util.Math.tryParse;

/**
 * The type Editor controller.
 *
 * Controller for the GameEditor.
 *
 */
public class EditorController implements Initializable {
    /**
     * The constant name of the game configuration json-file.
     */
    private static final String configFileName = "gameConfig.json";


    /**
     * The constant for the spite directory.
     */
    private static final String spriteDirectory = "sprites";
    /**
     * The Default game tile dimensions.
     */
    private final int defaultGameTileDim = 48;
    /**
     * The Default background pane scaling.
     */
    private final int defaultBackgroundScaling = 3;

    /**
     * The Property editor pane.
     */
    @FXML
    private Pane propertyEditorPane;
    /**
     * The Room selector.
     */
    @FXML
    private ListView<Room> roomSelector;
    /**
     * The Background img text field.
     */
    @FXML
    private TextField backgroundImgTextField;
    /**
     * The Background scaling text field.
     */
    @FXML
    private TextField backgroundScalingTextField;
    /**
     * The Room exits table.
     */
    @FXML
    private TableView<Room.Exit> roomExitsTable;
    /**
     * The Current room label.
     */
    @FXML
    private Label currentRoomLabel;
    /**
     * The Tile dimension text field.
     */
    @FXML
    private TextField tileDimTextField;
    /**
     * The Exit key row.
     */
    @FXML
    private TableColumn<Object, String> exitKeyRow;
    /**
     * The Exit room row.
     */
    @FXML
    private TableColumn<Object, String> exitRoomRow;
    /**
     * The GameObject type box.
     */
    @FXML
    private ComboBox<String> gameObjectTypeBox;
    /**
     * The Current position label.
     */
    @FXML
    private Label currentPosLabel;
    /**
     * The Draw grid toggle button.
     */
    @FXML
    private ToggleButton drawGridToggleButton;
    /**
     * The pane displaying the game background and entities.
     */
    @FXML
    private Pane roomPane;


    /**
     * The Game tile dimension.
     */
    private int gameTileDim = defaultGameTileDim;
    /**
     * The Background scaling.
     */
    private int backgroundScaling = defaultBackgroundScaling;
    /**
     * The instance of Game being modified.
     */
    private Game model;
    /**
     * The loaded images.
     */
    private HashMap<String, Image> loadedImages;
    /**
     * The Currently editing position.
     */
    private Vector currentlyEditingPos;
    /**
     * The Current GameObject being selected.
     */
    private GameObject currentGameObject;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadGame();
        loadedImages = getImages(spriteDirectory, getClass());

        tileDimTextField.setText(String.valueOf(defaultGameTileDim));
        backgroundScalingTextField.setText(String.valueOf(defaultBackgroundScaling));


        exitRoomRow.setCellFactory(TextFieldTableCell.forTableColumn());
        exitKeyRow.setCellFactory(TextFieldTableCell.forTableColumn());

        roomSelector.itemsProperty()
                .bindBidirectional(model.roomsProperty());


        drawRoom();
        addListeners();


        for (Room room : model.getRooms()) {
            if (room != null && room.getRoomGrid() != null) {
                Arrays.stream(room.getRoomGrid()).forEach(t -> {
                    for (GameObject gameObject : t) {
                        gameObject.configureImages(loadedImages);
                    }
                });

            }
        }

        model.getPlayer().getInventory().addItem(new pHNeutralizers());


    }


    /**
     * Add listeners.
     *
     * Adds listeners to various JavaFX nodes and binds them to relevant properties.
     */
    private void addListeners() {

        roomExitsTable.itemsProperty().bindBidirectional(model.getRoom().exitStringsProperty());
        backgroundScalingTextField.setText(String.valueOf(model.getRoom().getRoomBGScale()));
        tileDimTextField.setText(String.valueOf(model.getRoom().getRoomTileDim()));

        currentRoomLabel.setText(model.getRoom().toString());

        backgroundImgTextField.setText(model.getRoom().getBackgroundImage());
        backgroundImgTextField.textProperty().addListener(ev -> model.getRoom().setBackgroundImage(backgroundImgTextField.getText()));
        backgroundScalingTextField.textProperty().addListener(ev -> {
            backgroundScaling = tryParse(backgroundScalingTextField.getText(), defaultBackgroundScaling);
            model.getRoom().setRoomBGScale(backgroundScaling);
        });
        tileDimTextField.textProperty().addListener(ev -> {
            gameTileDim = tryParse(tileDimTextField.getText(), defaultGameTileDim);
            model.getRoom().setRoomTileDim(gameTileDim);
        });

    }


    /**
     * Change room.
     *
     * Changes room dependent on the selected item of {@link EditorController#roomSelector}.
     */
    public void changeRoom() {
        Room clickedElement = roomSelector.getSelectionModel().getSelectedItem();
        if (clickedElement != null && clickedElement != model.getRoom()) {

            roomExitsTable.itemsProperty().unbindBidirectional(model.getRoom().exitStringsProperty());
            backgroundImgTextField.textProperty().removeListener(ev -> model.getRoom().setBackgroundImage(backgroundImgTextField.getText()));
            backgroundScalingTextField.textProperty().removeListener(ev -> {
                backgroundScaling = tryParse(backgroundScalingTextField.getText(), defaultBackgroundScaling);
                model.getRoom().setRoomBGScale(backgroundScaling);
            });
            tileDimTextField.textProperty().removeListener(ev -> {
                gameTileDim = tryParse(tileDimTextField.getText(), defaultGameTileDim);
                model.getRoom().setRoomTileDim(gameTileDim);
            });
            model.setRoom(clickedElement);
            addListeners();
            drawRoom();
        }
    }

    /**
     * Change type.
     *
     * Replaces the {@link EditorController#currentGameObject} with the selected item from {@link EditorController#gameObjectTypeBox}.
     */
    public void changeType() {
        if (model.getRoom().getGridGameObject(currentlyEditingPos) != currentGameObject) {
            return;
        }


        String clickedElement = gameObjectTypeBox.getSelectionModel().getSelectedItem();
        GameObject objectToAdd;

        switch (clickedElement) {
            case "Block":
                if (currentGameObject instanceof Block) {
                    return;
                }
                objectToAdd = new Block();

                break;
            case "Field":
                if (currentGameObject instanceof Field) {
                    return;
                }
                objectToAdd = new Field();

                break;
            case "Door":
                if (currentGameObject instanceof Door) {
                    return;
                }

                objectToAdd = new Door();
                break;
            case "NPC":
                if (currentGameObject instanceof Door) {
                    return;
                }

                objectToAdd = new NPC();
                break;
            default:
                return;
        }

        model.getRoom().setGridGameObject(objectToAdd, currentlyEditingPos);
        drawRoom();
        selectGameObject(objectToAdd);
    }


    /**
     * Loads game from JSON-file specified by {@link EditorController#configFileName} and replaces {@link EditorController#model}.
     */
    private void loadGame() {


        model = jsonToGame(readConfigFile(configFileName));



        if (model != null) {
            model.reconfigureRooms();
        } else {
            model = new Game();
            model.createRooms();
        }

    }


    /**
     * Draws images relevant to {@link EditorController#roomPane}.
     */
    private void drawRoom() {
        roomPane.getChildren().clear();

        if (model.getRoom().getBackgroundImage() != null && loadedImages.containsKey(model.getRoom().getBackgroundImage())) {
            setBackground(model.getRoom());
        } else {
            setBackground(loadedImages.get("sprites/room/test.png"));
        }


        if (drawGridToggleButton.isSelected()) {
            drawGrid(roomPane, getBackgroundRowCount());
        }

        drawGameObjects(model.getRoom(), loadedImages, roomPane, getBackgroundTileDim(), getClass(), currentlyEditingPos, true);

    }


    /**
     * Sets background of {@link EditorController#roomPane}.
     *
     * @param room the room
     */
    private void setBackground(Room room) {
        setBackground(loadedImages.get(room.getBackgroundImage()));
    }

    /**
     * Sets background of {@link EditorController#roomPane}.
     *
     * @param backgroundImage the background image.
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
     * Gets background tile dim {@link EditorController#roomPane} when dividing using {@link EditorController#gameTileDim} and {@link EditorController#backgroundScaling}.
     *
     * @return the background tile dim
     */
    private double getBackgroundTileDim() {
        return roomPane.getMinWidth() / getBackgroundRowCount();
    }

    /**
     * Gets background row count of {@link EditorController#roomPane} when dividing using {@link EditorController#gameTileDim} and {@link EditorController#backgroundScaling}.
     *
     * @return the background row count
     */
    private double getBackgroundRowCount() {
        return (roomPane.getMinWidth() / backgroundScaling) / gameTileDim;
    }


    /**
     * Select game object for editing.
     *
     * Selects a GameObject for editing and opens appropriate {@link EditorController#propertyEditorPane}
     *
     * @param gameObject the game object
     */
    private void selectGameObject(GameObject gameObject) {
        if (gameObject != currentGameObject) {
            currentGameObject = gameObject;
        } else {
            return;
        }


        propertyEditorPane.getChildren().clear();

        String nodeToLoad = "";
        FXMLLoader loader = new FXMLLoader();

        if (gameObject instanceof Block) {
            nodeToLoad = "editor/blockEditor.fxml";
            loader.setControllerFactory(aClass -> new BlockEditor((Block) gameObject));
            gameObjectTypeBox.getSelectionModel().select(0);
        } else if (gameObject instanceof Door) {
            nodeToLoad = "editor/doorEditor.fxml";
            loader.setControllerFactory(aClass -> new DoorEditor((Door) gameObject));
            gameObjectTypeBox.getSelectionModel().select(1);

        } else if (gameObject instanceof Field) {
            nodeToLoad = "editor/fieldEditor.fxml";
            loader.setControllerFactory(aClass -> new FieldEditor((Field) gameObject));
            gameObjectTypeBox.getSelectionModel().select(2);
        } else if (gameObject instanceof NPC) {
            nodeToLoad = "editor/npcEditor.fxml";
            loader.setControllerFactory(aClass -> new NPCEditor((NPC) gameObject));
            gameObjectTypeBox.getSelectionModel().select(3);
        }

        loader.setLocation(getClass().getResource(nodeToLoad));

        try {
            Node root = loader.load();
            propertyEditorPane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Export {@link EditorController#model}.
     *
     * Pastes serialized instance of {@link EditorController#model} to clipboard.
     */
    public void exportGame() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();

        content.putString(gameToJson(model));
        clipboard.setContent(content);

    }

    /**
     * Calls {@link EditorController#drawRoom()}
     */
    public void redrawGame() {


        drawRoom();
    }

    /**
     * Adds new exit entry to current room of {@link EditorController#model}
     */
    public void addExitRow() {
        model.getRoom().exitStringsProperty().add(new Room.Exit("Empty", "Empty"));
    }


    /**
     * Change exit key.
     *
     * @param cell the cell
     */
    public void changeExitKey(TableColumn.CellEditEvent<Room.Exit, String> cell) {
        (cell.getTableView().getItems().get(cell.getTablePosition().getRow())).setExitKey(cell.getNewValue());
    }


    /**
     * Change exit value.
     *
     * @param cell the cell
     */
    public void changeExitValue(TableColumn.CellEditEvent<Room.Exit, String> cell) {
        (cell.getTableView().getItems().get(cell.getTablePosition().getRow())).setExitValue(cell.getNewValue());
    }

    /**
     * Delete exit row.
     *
     * @param keyEvent the key event
     */
    public void deleteExitRow(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE) {
            model.getRoom().exitsProperty().remove(roomExitsTable.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * {@link EditorController#roomPane} clicked.
     *
     * When clicked calls {@link EditorController#selectGameObject(GameObject)} for GameObject at clicked position.
     *
     * @param event the event
     */
    public void paneClicked(MouseEvent event) {
        currentlyEditingPos = positionClickedOnPane(getBackgroundTileDim(), getBackgroundTileDim(), event.getX(), event.getY());
        if (currentlyEditingPos.getX() < 0 || currentlyEditingPos.getY() < 0 || currentlyEditingPos.getX() > getBackgroundRowCount() || currentlyEditingPos.getY() > getBackgroundRowCount()) {
            return;
        }
        currentPosLabel.textProperty().bindBidirectional(currentlyEditingPos.vectorValueProperty());

        try {
            GameObject gameObject = model.getRoom().getGridGameObject(currentlyEditingPos);
            selectGameObject(gameObject);
            drawRoom();


        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            model.getRoom().fillRoomGridWithBlocks((int) getBackgroundRowCount(), (int) getBackgroundRowCount());
            if (model.getRoom().getGridGameObject(currentlyEditingPos) != null) {
                paneClicked(event);
            }
        }
    }


    /**
     * {@link EditorController#propertyEditorPane} mouse moved.
     */
    public void propertyPaneMouseMoved() {
        drawRoom();
    }

    /**
     * Start {@link EditorController#roomPane} dragged.
     *
     * If secondary mouse button is activated set the colliding field of all GameObject, which mouse is dragged over, to true, if CTRL key is pressed as well, set them to false.
     *
     * @param mouseEvent the mouse event
     */
    public void startPaneDragged(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
            roomPane.setOnMouseDragged(ev -> {
                boolean collision = true;
                if (mouseEvent.isControlDown()) {
                    collision = false;
                }
                paneClicked(ev);
                currentGameObject.setColliding(collision);
                drawRoom();
            });
        }
    }

    /**
     * End pane dragged.
     *
     * @param mouseEvent the mouse event
     */
    public void endPaneDragged(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
            roomPane.setOnMouseDragged(ev -> {
            });
        }
    }
}
