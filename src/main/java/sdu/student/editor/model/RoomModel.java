package sdu.student.editor.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import worldofzuul.world.Room;

import java.util.HashMap;

public class RoomModel {
    private final ListProperty<ExitModel> exits = new SimpleListProperty<>(
            FXCollections.observableArrayList());
    private Room room;

    public RoomModel(Room room) {
        this.room = room;
        int i = 0;
        for (String s : room.getRoomStringExits().keySet()) {
            exits.add(new ExitModel(s, (String) room.getRoomStringExits().values().toArray()[i]));
            i++;
        }

        exitsProperty().forEach(e -> {
            listenToExitPropertyChange(e);
        });

        exitsProperty().addListener((object, oldV, newV) -> {
            listenToNewObjects(oldV, newV);
        });

    }


    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public ObservableList<ExitModel> getExits() {
        return exits.get();
    }

    public void setExits(ObservableList<ExitModel> exits) {
        this.exits.set(exits);
    }

    public ListProperty<ExitModel> exitsProperty() {
        return exits;
    }

    private HashMap<String, String> exitPropertyToMap(ListProperty<ExitModel> list) {
        HashMap<String, String> map = new HashMap<>();
        for (ExitModel exitModel : list) {
            map.put(exitModel.getExitKey(), exitModel.getExitValue());
        }


        return map;
    }

    private void listenToExitPropertyChange(ExitModel model) {
        model.exitKeyProperty().addListener(ev1 -> {
            room.setRoomStringExits(exitPropertyToMap(exits));
        });
        model.exitValueProperty().addListener(ev1 -> {
            room.setRoomStringExits(exitPropertyToMap(exits));
        });
    }

    private void listenToNewObjects(ObservableList<ExitModel> v1, ObservableList<ExitModel> v2) {
        for (ExitModel exitModel : v2) {
            if (!v1.contains(exitModel)) {
                listenToExitPropertyChange(exitModel);
            }
        }
    }

    @Override
    public String toString() {
        return room.toString();
    }

}
