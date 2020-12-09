package worldofzuul.world;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import worldofzuul.parsing.Command;
import worldofzuul.util.Vector;

import java.util.Date;
import java.util.LinkedList;
import java.util.Set;

public class Room {

    private final MapProperty<String, Room> exits = new SimpleMapProperty<>(
            FXCollections.observableHashMap()
    );
    private final ListProperty<Exit> exitStrings = new SimpleListProperty<>(
            FXCollections.observableArrayList());
    private final StringProperty backgroundImage = new SimpleStringProperty();

    private GameObject[][] roomGrid;
    private Environment environment = new Environment();
    private String description; //TODO: Consider converting to StringProperty
    private int roomTileDim = 24;
    private int roomBGScale = 1;

    public Room() {
        /*
        //Listen for room exit change
        exitStringsProperty().forEach(e -> {
            listenToExitPropertyChange(e);
        });

        exitStringsProperty().addListener((object, oldV, newV) -> {
            listenToNewObjects(oldV, newV);
        });
        */

    }

    // method for adding GameObjects to roomGrid, give positions as coordinate system.
    public void addToGrid(GameObject gameObject, int posX, int posY) {
        roomGrid[posY][posX] = gameObject;
    }
    public Room(String description)
    {
        this();
        this.description = description;
    }

    public Room(String description, GameObject[][] roomGrid) {
        this(description);
        this.roomGrid = roomGrid;
    }

    public Room(String description, GameObject[][] roomGrid, Date date) {
        this(description);
        this.roomGrid = roomGrid;
        this.environment = new Environment(date);
    }

    public void setExit(String direction, Room neighbor) {
        exitStrings.add(new Exit(direction, neighbor.toString()));
        exits.put(direction, neighbor);
    }

    @JsonIgnore
    public String getShortDescription() {
        return description;
    }

    @JsonIgnore
    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();
    }


    private String getExitString() {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public GameObject[][] getRoomGrid() {
        return roomGrid;
    }

    public void setRoomGrid(GameObject[][] roomGrid) {
        this.roomGrid = roomGrid;
    }

    public GameObject getGridGameObject(Vector pos) {
        return getRoomGrid()[pos.getY()][pos.getX()];
    }

    public void setGridGameObject(GameObject gameObject, Vector pos) {
        roomGrid[pos.getY()][pos.getX()] = gameObject;
    }

    public LinkedList<Command[]> update() {
        LinkedList<Command[]> commands = new LinkedList<>();

        environment.update();

        if(roomGrid != null){
            for (GameObject[] gameObjects : roomGrid) {
                for (GameObject gameObject : gameObjects) {
                    if (gameObject != null) {
                        commands.add(gameObject.update());
                        environment.update(gameObject);
                    }
                }
            }
        }

        return commands;
    }


    public String getDescription() {
        return description;
    }

    public void fillRoomGridWithBlocks(int height, int width) {
        this.roomGrid = new GameObject[height][width];
        for (int h = 0; h < this.roomGrid.length; h++) {
            for (int w = 0; w < this.roomGrid[h].length; w++) {
                this.roomGrid[h][w] = new Block();
            }
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getBackgroundImage() {
        return backgroundImage.get();
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage.set(backgroundImage);
    }

    public StringProperty backgroundImageProperty() {
        return backgroundImage;
    }

    public int getRoomTileDim() {
        return roomTileDim;
    }

    public void setRoomTileDim(int roomTileDim) {
        this.roomTileDim = roomTileDim;
    }

    public int getRoomBGScale() {
        return roomBGScale;
    }

    public void setRoomBGScale(int roomBGScale) {
        this.roomBGScale = roomBGScale;
    }

    @JsonIgnore
    public ObservableMap<String, Room> getExits() {
        return exits.get();
    }

    @JsonIgnore
    public void setExits(ObservableMap<String, Room> exits) {
        this.exits.set(exits);
    }

    @JsonIgnore
    public MapProperty<String, Room> exitsProperty() {
        return exits;
    }

    @JsonGetter
    public ObservableList<Exit> getExitStrings() {
        return exitStrings.get();
    }

    @JsonSetter
    public void setExitStrings(LinkedList<Exit> exits) {
        ListProperty<Exit> temp = new SimpleListProperty<>(
                FXCollections.observableArrayList());

        temp.addAll(exits);

        setExitStrings(temp);
    }

    @JsonIgnore
    public void setExitStrings(ObservableList<Exit> exitStrings) {
        this.exitStrings.set(exitStrings);
    }

    @JsonIgnore
    public ListProperty<Exit> exitStringsProperty() {
        return exitStrings;
    }

    @JsonIgnore
    @Override
    public String toString() {
        return description;
    }


    public static class Exit {

        private final StringProperty exitKey = new SimpleStringProperty();
        private final StringProperty exitValue = new SimpleStringProperty();

        public Exit() {
        }

        public Exit(String exitKey, String exitValue) {
            setExitKey(exitKey);
            setExitValue(exitValue);
        }


        public String getExitKey() {
            return exitKey.get();
        }

        public void setExitKey(String exitKey) {
            this.exitKey.set(exitKey);
        }

        @JsonIgnore
        public StringProperty exitKeyProperty() {
            return exitKey;
        }

        public String getExitValue() {
            return exitValue.get();
        }

        public void setExitValue(String exitValue) {
            this.exitValue.set(exitValue);
        }

        @JsonIgnore
        public StringProperty exitValueProperty() {
            return exitValue;
        }
    }
}

