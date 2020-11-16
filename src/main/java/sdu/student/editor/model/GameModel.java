package sdu.student.editor.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import worldofzuul.Game;
import worldofzuul.world.Room;

import java.util.Arrays;
import java.util.List;

public class GameModel {
    private final ListProperty<RoomModel> rooms = new SimpleListProperty<>(
            FXCollections.observableArrayList());
    private Game game;
    private RoomModel currentRoom;

    public GameModel(Game game) {
        this.game = game;

        for (Room room : game.getRooms()) {
            rooms.add(new RoomModel(room));
        }

        setCurrentRoom(rooms.stream().findFirst().orElseThrow());

        rooms.addListener((obj, o, n) -> {
            for (RoomModel roomModel : n) {
                if (!o.contains(roomModel)) {
                    List<Room> roomList = Arrays.asList(game.getRooms());
                    roomList.add(roomModel.getRoom());
                    game.setRooms(roomList.toArray(new Room[game.getRooms().length + 1]));
                }
            }
        });

    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }


    public ObservableList<RoomModel> getRooms() {
        return rooms.get();
    }

    public void setRooms(ObservableList<RoomModel> rooms) {
        this.rooms.set(rooms);
    }

    public ListProperty<RoomModel> roomsProperty() {
        return rooms;
    }

    public RoomModel getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(RoomModel currentRoom) {
        game.setRoom(currentRoom.getRoom());
        this.currentRoom = currentRoom;
    }
}
