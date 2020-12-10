package sdu.student;

import javafx.event.ActionEvent;
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
import worldofzuul.item.*;
import worldofzuul.util.Vector;
import worldofzuul.world.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static worldofzuul.util.Data.*;
import static worldofzuul.util.Drawing.drawGameObjects;
import static worldofzuul.util.Drawing.drawGrid;
import static worldofzuul.util.Math.positionClickedOnPane;
import static worldofzuul.util.Math.tryParse;

public class EditorController implements Initializable {
    private static final String configFileName = "gameConfig.json";
    private static final String spriteDirectory = "sprites";
    private int defaultGameTileDim = 48;
    private int defaultBackgroundScaling = 3;

    @FXML
    private Pane propertyEditorPane;
    @FXML
    private ListView<Room> roomSelector;
    @FXML
    private TextField backgroundImgTextField;
    @FXML
    private TextField backgroundScalingTextField;
    @FXML
    private TableView<Room.Exit> roomExitsTable;
    @FXML
    private Label currentRoomLabel;
    @FXML
    private TextField tileDimTextField;
    @FXML
    private TableColumn<Object, String> exitKeyRow;
    @FXML
    private TableColumn<Object, String> exitRoomRow;
    @FXML
    private ComboBox<String> gameObjectTypeBox;
    @FXML
    private Label currentPosLabel;
    @FXML
    private ToggleButton drawGridToggleButton;
    @FXML
    private Pane roomPane;


    private int gameTileDim = defaultGameTileDim;
    private int backgroundScaling = defaultBackgroundScaling;
    private Game model;
    private HashMap<String, Image> loadedImages;
    private Vector currentlyEditingPos;
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
            if(room != null && room.getRoomGrid() != null){
                Arrays.stream(room.getRoomGrid()).forEach(t -> {
                    Arrays.stream(t).forEach(gameObject -> {

                        gameObject.configureImages(loadedImages);

                    });
                });

            }
        }

        model.getPlayer().getInventory().addItem(new pHNeutralizers());



    }


    private void addListeners() {

        roomExitsTable.itemsProperty().bindBidirectional(model.getRoom().exitStringsProperty());
        backgroundScalingTextField.setText(String.valueOf(model.getRoom().getRoomBGScale()));
        tileDimTextField.setText(String.valueOf(model.getRoom().getRoomTileDim()));

        currentRoomLabel.setText(model.getRoom().toString());

        backgroundImgTextField.setText(model.getRoom().getBackgroundImage());
        backgroundImgTextField.textProperty().addListener(ev -> {
            model.getRoom().setBackgroundImage(backgroundImgTextField.getText());
        });
        backgroundScalingTextField.textProperty().addListener(ev -> {
            backgroundScaling = tryParse(backgroundScalingTextField.getText(), defaultBackgroundScaling);
            model.getRoom().setRoomBGScale(backgroundScaling);
        });
        tileDimTextField.textProperty().addListener(ev -> {
            gameTileDim = tryParse(tileDimTextField.getText(), defaultGameTileDim);
            model.getRoom().setRoomTileDim(gameTileDim);
        });

    }


    public void changeRoom(MouseEvent actionEvent) {
        Room clickedElement = roomSelector.getSelectionModel().getSelectedItem();
        if (clickedElement != null && clickedElement != model.getRoom()) {

            roomExitsTable.itemsProperty().unbindBidirectional(model.getRoom().exitStringsProperty());
            backgroundImgTextField.textProperty().removeListener(ev -> {
                model.getRoom().setBackgroundImage(backgroundImgTextField.getText());
            });
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

    public void changeType(ActionEvent actionEvent) {
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


    private void loadGame() {
        model = jsonToGame(readConfigFile(configFileName));
        if (model != null) {
            model.reconfigureRooms();
        } else {
            model = new Game();
            model.createRooms();
        }

    }


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

        drawGameObjects(model.getRoom(), loadedImages, roomPane, getBackgroundTileDim(), getClass(), currentlyEditingPos);

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


    private double getBackgroundTileDim() {
        return roomPane.getMinWidth() / getBackgroundRowCount();
    }

    private double getBackgroundRowCount() {
        return (roomPane.getMinWidth() / backgroundScaling) / gameTileDim;
    }


    private void selectGameObject(GameObject gameObject) {
        if (gameObject != currentGameObject) {
            currentGameObject = gameObject;
        } else {
            return;
        }


        propertyEditorPane.getChildren().clear();

        String nodeToLoad = "";
        FXMLLoader loader = new FXMLLoader();

        //Get fxml editor
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


    public void exportGame(ActionEvent actionEvent) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();

        content.putString(gameToJson(model));
        clipboard.setContent(content);

    }

    public void redrawGame(ActionEvent actionEvent) {



        drawRoom();
    }

    public void addExitRow(ActionEvent actionEvent) {
        model.getRoom().exitStringsProperty().add(new Room.Exit("Empty", "Empty"));
    }


    public void changeExitKey(TableColumn.CellEditEvent<Room.Exit, String> cell) {
        (cell.getTableView().getItems().get(cell.getTablePosition().getRow())).setExitKey(cell.getNewValue());
    }


    public void changeExitValue(TableColumn.CellEditEvent<Room.Exit, String> cell) {
        (cell.getTableView().getItems().get(cell.getTablePosition().getRow())).setExitValue(cell.getNewValue());
    }

    public void deleteExitRow(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE) {
            model.getRoom().exitsProperty().remove(roomExitsTable.getSelectionModel().getSelectedItem());
        }
    }

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



        } catch (ArrayIndexOutOfBoundsException e) { // Handle exceptions caused by non-matching RoomGrid sizes or invalid positions
            System.out.println(e.getMessage());
            //Create fitting RoomGrid
            model.getRoom().fillRoomGridWithBlocks((int) getBackgroundRowCount(), (int) getBackgroundRowCount());
            if (model.getRoom().getGridGameObject(currentlyEditingPos) != null) {
                paneClicked(event);
            }
        }
    }


    public void propertyPaneMouseMoved(MouseEvent mouseEvent) {
        drawRoom();
    }

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

    public void endPaneDragged(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
            roomPane.setOnMouseDragged(ev -> {
            });
        }
    }
}
