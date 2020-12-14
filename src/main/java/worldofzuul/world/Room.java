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

/**
 * The type Room.
 *
 * Represents a Room in the game.
 *
 */
public class Room {

    /**
     * The Exits.
     *
     * Exit keys and their attributed room.
     *
     */
    private final MapProperty<String, Room> exits = new SimpleMapProperty<>(
            FXCollections.observableHashMap()
    );
    /**
     * The list of exits.
     *
     * List of {@link Exit} used for json deserialization and JavaFX property binding.
     *
     */
    private final ListProperty<Exit> exitStrings = new SimpleListProperty<>(
            FXCollections.observableArrayList());
    /**
     * The Background image.
     */
    private final StringProperty backgroundImage = new SimpleStringProperty();

    /**
     * The Room grid.
     *
     * Contains all GameObjects or "tiles" in the room.
     *
     */
    private GameObject[][] roomGrid;
    /**
     * The Environment.
     */
    private Environment environment = new Environment();
    /**
     * The Description.
     */
    private String description;
    /**
     * The Room tile dimension.
     */
    private int roomTileDim = 16;
    /**
     * The Room background scale.
     */
    private int roomBGScale = 1;

    /**
     * Instantiates a new Room.
     */
    public Room() {

    }

    /**
     * Instantiates a new Room.
     *
     * @param description the description
     */
    public Room(String description) {
        this.description = description;
    }

    /**
     * Instantiates a new Room.
     *
     * @param description the description
     * @param roomGrid    the room grid
     */
    public Room(String description, GameObject[][] roomGrid) {
        this(description);
        this.roomGrid = roomGrid;
    }

    /**
     * Instantiates a new Room.
     *
     * @param description the description
     * @param roomGrid    the room grid
     * @param date        the date
     */
    public Room(String description, GameObject[][] roomGrid, Date date) {
        this(description);
        this.roomGrid = roomGrid;
        this.environment = new Environment(date);
    }

    /**
     * Sets exit.
     *
     * @param direction the direction
     * @param neighbor  the neighbor
     */
    public void setExit(String direction, Room neighbor) {
        exitStrings.add(new Exit(direction, neighbor.toString()));
        exits.put(direction, neighbor);
    }

    /**
     * Gets short description.
     *
     * @return the short description
     */
    @JsonIgnore
    public String getShortDescription() {
        return description;
    }

    /**
     * Gets long description.
     *
     * @return the long description
     */
    @JsonIgnore
    public String getLongDescription() {
        return "You are " + description + ".\n";
    }

    /**
     * Gets exit.
     *
     * @param direction the direction
     * @return the exit
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }

    /**
     * Get room grid game object [ ] [ ].
     *
     * @return the game object [ ] [ ]
     */
    public GameObject[][] getRoomGrid() {
        return roomGrid;
    }

    /**
     * Sets room grid.
     *
     * @param roomGrid the room grid
     */
    public void setRoomGrid(GameObject[][] roomGrid) {
        this.roomGrid = roomGrid;
    }

    /**
     * Gets grid game object.
     *
     * @param pos the pos
     * @return the grid game object
     */
    public GameObject getGridGameObject(Vector pos) {
        return getRoomGrid()[pos.getY()][pos.getX()];
    }

    /**
     * Sets grid game object.
     *
     * @param gameObject the game object
     * @param pos        the pos
     */
    public void setGridGameObject(GameObject gameObject, Vector pos) {
        roomGrid[pos.getY()][pos.getX()] = gameObject;
    }

    /**
     * Update.
     *
     * Updates {@link Room#environment} and all GameObjects in {@link Room#roomGrid}
     *
     * @return Ordered list of array of commands
     */
    public LinkedList<Command[]> update() {
        LinkedList<Command[]> commands = new LinkedList<>();

        environment.update();

        if (roomGrid != null) {
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


    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Fill room grid with blocks.
     *
     * @param height the height
     * @param width  the width
     */
    public void fillRoomGridWithBlocks(int height, int width) {
        this.roomGrid = new GameObject[height][width];
        for (int h = 0; h < this.roomGrid.length; h++) {
            for (int w = 0; w < this.roomGrid[h].length; w++) {
                this.roomGrid[h][w] = new Block();
            }
        }
    }

    /**
     * Gets environment.
     *
     * @return the environment
     */
    public Environment getEnvironment() {
        return environment;
    }

    /**
     * Sets environment.
     *
     * @param environment the environment
     */
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * Gets background image.
     *
     * @return the background image
     */
    public String getBackgroundImage() {
        return backgroundImage.get();
    }

    /**
     * Sets background image.
     *
     * @param backgroundImage the background image
     */
    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage.set(backgroundImage);
    }

    /**
     * Background image property string property.
     *
     * @return the string property
     */
    public StringProperty backgroundImageProperty() {
        return backgroundImage;
    }

    /**
     * Gets room tile dim.
     *
     * @return the room tile dim
     */
    public int getRoomTileDim() {
        return roomTileDim;
    }

    /**
     * Sets room tile dim.
     *
     * @param roomTileDim the room tile dim
     */
    public void setRoomTileDim(int roomTileDim) {
        this.roomTileDim = roomTileDim;
    }

    /**
     * Gets room bg scale.
     *
     * @return the room bg scale
     */
    public int getRoomBGScale() {
        return roomBGScale;
    }

    /**
     * Sets room bg scale.
     *
     * @param roomBGScale the room bg scale
     */
    public void setRoomBGScale(int roomBGScale) {
        this.roomBGScale = roomBGScale;
    }

    /**
     * Sets printing enabled.
     *
     * @param printingEnabled the printing enabled
     */
    @JsonIgnore
    public void setPrintingEnabled(boolean printingEnabled) {
        environment.setPrintingEnabled(printingEnabled);
    }

    /**
     * Gets exits.
     *
     * @return the exits
     */
    @JsonIgnore
    public ObservableMap<String, Room> getExits() {
        return exits.get();
    }

    /**
     * Sets exits.
     *
     * @param exits the exits
     */
    @JsonIgnore
    public void setExits(ObservableMap<String, Room> exits) {
        this.exits.set(exits);
    }

    /**
     * Exits property map property.
     *
     * @return the map property
     */
    @JsonIgnore
    public MapProperty<String, Room> exitsProperty() {
        return exits;
    }

    /**
     * Gets exit strings.
     *
     * @return the exit strings
     */
    @JsonGetter
    public ObservableList<Exit> getExitStrings() {
        return exitStrings.get();
    }

    /**
     * Sets exit strings.
     *
     * @param exits the exits
     */
    @JsonSetter
    public void setExitStrings(LinkedList<Exit> exits) {
        ListProperty<Exit> temp = new SimpleListProperty<>(
                FXCollections.observableArrayList());

        temp.addAll(exits);

        setExitStrings(temp);
    }

    /**
     * Sets exit strings.
     *
     * @param exitStrings the exit strings
     */
    @JsonIgnore
    public void setExitStrings(ObservableList<Exit> exitStrings) {
        this.exitStrings.set(exitStrings);
    }

    /**
     * Exit strings property list property.
     *
     * @return the list property
     */
    @JsonIgnore
    public ListProperty<Exit> exitStringsProperty() {
        return exitStrings;
    }

    @JsonIgnore
    @Override
    public String toString() {
        return description;
    }


    /**
     * The type Exit.
     *
     * Used for JavaFX editing of room exits and JSON (de)serialization.
     *
     */
    public static class Exit {

        /**
         * The Exit key.
         */
        private final StringProperty exitKey = new SimpleStringProperty();
        /**
         * The Exit value.
         */
        private final StringProperty exitValue = new SimpleStringProperty();

        /**
         * Instantiates a new Exit.
         */
        public Exit() {
        }

        /**
         * Instantiates a new Exit.
         *
         * @param exitKey   the exit key
         * @param exitValue the exit value
         */
        public Exit(String exitKey, String exitValue) {
            setExitKey(exitKey);
            setExitValue(exitValue);
        }


        /**
         * Gets exit key.
         *
         * @return the exit key
         */
        public String getExitKey() {
            return exitKey.get();
        }

        /**
         * Sets exit key.
         *
         * @param exitKey the exit key
         */
        public void setExitKey(String exitKey) {
            this.exitKey.set(exitKey);
        }

        /**
         * Exit key property string property.
         *
         * @return the string property
         */
        @JsonIgnore
        public StringProperty exitKeyProperty() {
            return exitKey;
        }

        /**
         * Gets exit value.
         *
         * @return the exit value
         */
        public String getExitValue() {
            return exitValue.get();
        }

        /**
         * Sets exit value.
         *
         * @param exitValue the exit value
         */
        public void setExitValue(String exitValue) {
            this.exitValue.set(exitValue);
        }

        /**
         * Exit value property string property.
         *
         * @return the string property
         */
        @JsonIgnore
        public StringProperty exitValueProperty() {
            return exitValue;
        }
    }
}

